package com.vnazarov.test2.fragments.places

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
import com.vnazarov.test2.data.currentCity
import com.vnazarov.test2.data.dataPlaces
import com.vnazarov.test2.databinding.FragmentPlacesListBinding
import com.vnazarov.test2.helpers.disablePopBack
import com.vnazarov.test2.helpers.enablePopBack
import com.vnazarov.test2.objects.Place

class PlacesListFragment : Fragment() {

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

        (activity as MainActivity).title = currentCity.cityName
        enablePopBack(activity as MainActivity, (activity as MainActivity).mToolbar)
    }

    override fun onStop() {
        super.onStop()

        disablePopBack(activity as MainActivity)
    }

    private fun loadPlaces() {
        if (dataPlaces.isEmpty()) {

            Toast.makeText(context, "Something went wrong, try to relaunch the app", Toast.LENGTH_SHORT).show()

        } else {

            val dataPlacePerCity = arrayListOf<Place>()

            for (i in 0 until dataPlaces.size){
                if (dataPlaces[i].cityId == currentCity.id) dataPlacePerCity.add(dataPlaces[i])
                if (!dataPlaces[i].isVisible) dataPlacePerCity.remove(dataPlaces[i])
            }

            loadRV(dataPlacePerCity)
        }

    }

    private fun loadRV(placesPerCity: List<Place>) {
        mRecyclerView = mBinding.listOfPlaces
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = PlacesListAdapter(placesPerCity, activity as AppCompatActivity)
        mBinding.listOfPlaces.adapter = adapter
    }
}