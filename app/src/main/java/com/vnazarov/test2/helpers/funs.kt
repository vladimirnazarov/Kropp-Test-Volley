package com.vnazarov.test2.helpers

import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.vnazarov.test2.MainActivity
import com.vnazarov.test2.R
import com.vnazarov.test2.data.cities
import com.vnazarov.test2.data.places
import com.vnazarov.test2.databinding.ActivityMainBinding
import com.vnazarov.test2.fragments.regions.RegionsListFragment
import com.vnazarov.test2.objects.City
import com.vnazarov.test2.objects.Place

fun AppCompatActivity.replaceFragment(fragment: Fragment, addStack: Boolean = true) {
    if (addStack){
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.data_container, fragment)
            .commit()
    } else {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.data_container, fragment)
            .commit()
    }
}

fun enablePopBack(activity: MainActivity, toolbar: Toolbar){
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener{
        activity.supportFragmentManager.popBackStack()
    }
}

fun disablePopBack(activity: MainActivity){
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
}

private fun initFunc(activity: MainActivity, mToolbar: Toolbar){
    activity.setSupportActionBar(mToolbar)

    activity.replaceFragment(RegionsListFragment(), false)
}

fun loadData(requestQueue: RequestQueue, mBinding: ActivityMainBinding, activity: MainActivity, mToolbar: Toolbar){
    if (cities.isEmpty() && places.isEmpty()) {

        val citiesList = arrayListOf<City>()
        val placesList = arrayListOf<Place>()

        val urlCities = "https://krokapp.by/api/get_cities/11/"
        val urlPlaces = "http://krokapp.by/api/get_points/11/"

        val requestCities = JsonArrayRequest(Request.Method.GET, urlCities, null, { response ->

            for (i in 0 until response.length()) {
                val jsonObject = response.getJSONObject(i)

                val city = City(
                    jsonObject.getInt("id_locale"),
                    jsonObject.getInt("id"),
                    jsonObject.getString("name"),
                    jsonObject.getInt("lang"),
                    jsonObject.getString("logo"),
                    jsonObject.getLong("last_edit_time"),
                    jsonObject.getBoolean("visible"),
                    jsonObject.getBoolean("city_is_regional"),
                    jsonObject.getString("region")
                )

                citiesList.add(city)
                println("city: $city")
            }

            cities = citiesList

        }, {
            Log.e("Cities response error", it.message.toString())
        })

        val requestPlaces = JsonArrayRequest(Request.Method.GET, urlPlaces, null, { response ->

            mBinding.activityProgressBar.visibility = View.GONE
            initFunc(activity, mToolbar)

            for (i in 0 until response.length()){
                val jsonObject = response.getJSONObject(i)

                val jsonListString = jsonObject.getJSONArray("images")
                val jsonListInt = jsonObject.getJSONArray("tags")

                val listString = arrayListOf<String>()
                val listInt = arrayListOf<Int>()

                for (j in 0 until jsonListString.length()){
                    listString.add(jsonListString[j] as String)
                }

                for (j in 0 until jsonListInt.length()){
                    listInt.add(jsonListInt[j] as Int)
                }

                val place = Place(
                    jsonObject.getInt("id"),
                    jsonObject.getInt("id_point"),
                    jsonObject.getString("name"),
                    jsonObject.getString("text"),
                    jsonObject.getString("sound"),
                    jsonObject.getInt("lang"),
                    jsonObject.getLong("last_edit_time"),
                    jsonObject.getString("creation_date"),
                    jsonObject.getDouble("lat"),
                    jsonObject.getDouble("lng"),
                    jsonObject.getString("photo"),
                    jsonObject.getInt("city_id"),
                    jsonObject.getBoolean("visible"),
                    listString,
                    listInt,
                    jsonObject.getBoolean("is_excursion")
                )

                placesList.add(place)
                println("place: $place")
            }

            places = placesList

        }, {
            Log.e("Places response error", it.message.toString())
        })

        requestCities.retryPolicy = object : DefaultRetryPolicy(10000, 1, 1f){}

        requestPlaces.retryPolicy = object : DefaultRetryPolicy(100000, 1, 1f){}

        requestQueue.add(requestCities)
        requestQueue.cache.clear()
        requestQueue.add(requestPlaces)

    }
}