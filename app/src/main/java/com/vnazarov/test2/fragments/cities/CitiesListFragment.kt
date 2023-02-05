package com.vnazarov.test2.fragments.cities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.vnazarov.test2.MainActivity
import com.vnazarov.test2.data.cities
import com.vnazarov.test2.data.region
import com.vnazarov.test2.databinding.FragmentCitiesListBinding
import com.vnazarov.test2.helpers.disablePopBack
import com.vnazarov.test2.helpers.enablePopBack
import com.vnazarov.test2.objects.City

class CitiesListFragment : Fragment() {

    private lateinit var mBinding: FragmentCitiesListBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var adapter: CitiesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCitiesListBinding.inflate(layoutInflater, container, false)

        loadCities()

        return mBinding.root
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).title = region
        enablePopBack(activity as MainActivity, (activity as MainActivity).mToolbar)
    }

    override fun onStop() {
        super.onStop()

        disablePopBack(activity as MainActivity)
    }

    private fun loadCities() {
        if (cities.isEmpty()) {

            val citiesList = arrayListOf<City>()

            val url = "https://krokapp.by/api/get_cities/11/"
            val request = JsonArrayRequest(Request.Method.GET, url, null, { response ->
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
                }

                cities = citiesList
                println(citiesList)

                loadRV()

            }, {
                Log.e("Response error", it.message.toString())
            })

            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(request)

        } else loadRV()
    }

    private fun loadRV() {
        mRecyclerView = mBinding.listOfCities
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = CitiesListAdapter(cities, activity as AppCompatActivity)
        mBinding.listOfCities.adapter = adapter
    }
}