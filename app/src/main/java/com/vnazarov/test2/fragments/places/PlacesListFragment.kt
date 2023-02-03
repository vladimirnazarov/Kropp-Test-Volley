package com.vnazarov.test2.fragments.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vnazarov.test2.MainActivity
import com.vnazarov.test2.data.city
import com.vnazarov.test2.data.places
import com.vnazarov.test2.databinding.FragmentPlacesListBinding
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
            for (i in 1..10){
                val place = Place(name = "Place test$i")
                placesList.add(place)
            }

            places = placesList

            loadRV()
        } else loadRV()
    }

    private fun loadRV(){
        mRecyclerView = mBinding.listOfPlaces
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = PlacesListAdapter(places, activity as AppCompatActivity)
        mBinding.listOfPlaces.adapter = adapter
    }
}