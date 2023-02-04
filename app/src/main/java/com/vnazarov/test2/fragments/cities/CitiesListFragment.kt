package com.vnazarov.test2.fragments.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vnazarov.test2.MainActivity
import com.vnazarov.test2.R
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
            for (i in 1..10) {
                val city = City("City test$i", R.drawable.demo_city)
                citiesList.add(city)
            }

            cities = citiesList

            loadRV()
        } else loadRV()
    }

    private fun loadRV() {
        mRecyclerView = mBinding.listOfCities
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = CitiesListAdapter(cities, activity as AppCompatActivity)
        mBinding.listOfCities.adapter = adapter
    }
}