package com.vnazarov.test2.fragments.places

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.vnazarov.test2.MainActivity
import com.vnazarov.test2.R
import com.vnazarov.test2.data.city
import com.vnazarov.test2.data.places
import com.vnazarov.test2.databinding.FragmentPlacesListBinding
import com.vnazarov.test2.fragments.cities.CitiesListFragment
import com.vnazarov.test2.helpers.disablePopBack
import com.vnazarov.test2.helpers.enablePopBack
import com.vnazarov.test2.objects.Place

class PlacesListFragment: Fragment() {

    private lateinit var mBinding: FragmentPlacesListBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var adapter: PlacesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentPlacesListBinding.inflate(layoutInflater, container, false)

        loadPlaces()

        return mBinding.root
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).title = city
        enablePopBack(activity as MainActivity, (activity as MainActivity).mToolbar)
    }

    override fun onStop() {
        super.onStop()

        disablePopBack(activity as MainActivity)
    }

    private fun loadPlaces(){
        if (places.isEmpty()){
            val placesList = arrayListOf<Place>()

            val url = "http://krokapp.by/api/get_points/11/"
            val request = JsonArrayRequest(Request.Method.GET, url, null, { response ->

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
                }

                places = placesList

                loadRV()

            }, {
                Log.e("Places response error", it.message.toString())
            })

            request.retryPolicy = object : DefaultRetryPolicy(100000, 1, 1f){}
            (activity as MainActivity).requestQueue.cache.clear()
            (activity as MainActivity).requestQueue.add(request)

        } else loadRV()
    }

    private fun loadRV(){
        mRecyclerView = mBinding.listOfPlaces
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = PlacesListAdapter(places, activity as AppCompatActivity)
        mBinding.listOfPlaces.adapter = adapter
    }
}