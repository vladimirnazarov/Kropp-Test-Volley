package com.vnazarov.test2.fragments.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vnazarov.test2.MainActivity
import com.vnazarov.test2.data.currentRegion
import com.vnazarov.test2.data.dataCities
import com.vnazarov.test2.data.currentRegionName
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

        (activity as MainActivity).title = currentRegionName
        enablePopBack(activity as MainActivity, (activity as MainActivity).mToolbar)
    }

    override fun onStop() {
        super.onStop()

        disablePopBack(activity as MainActivity)
    }

    private fun loadCities() {
        if (dataCities.isEmpty()) {

            Toast.makeText(context, "Something went wrong, try to relaunch the app", Toast.LENGTH_SHORT).show()

        } else {

            val dataCityPerRegion = arrayListOf<City>()

            for (i in 0 until dataCities.size){
                if (dataCities[i].cityRegion == currentRegion) dataCityPerRegion.add(dataCities[i])
                if (!dataCities[i].isCityVisible) dataCityPerRegion.remove(dataCities[i])
            }

            loadRV(dataCityPerRegion)
        }
    }

    private fun loadRV(citiesPerRegion: List<City>) {
        mRecyclerView = mBinding.listOfCities
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = CitiesListAdapter(citiesPerRegion, activity as AppCompatActivity)
        mBinding.listOfCities.adapter = adapter
    }
}