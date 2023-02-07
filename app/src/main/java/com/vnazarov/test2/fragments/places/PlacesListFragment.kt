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

        (activity as MainActivity).title = currentCity
        enablePopBack(activity as MainActivity, (activity as MainActivity).mToolbar)
    }

    override fun onStop() {
        super.onStop()

        disablePopBack(activity as MainActivity)
    }

    private fun loadPlaces() {
        if (dataPlaces.isEmpty()) {

            Toast.makeText(context, "Something went wrong, try to relaunch the app", Toast.LENGTH_SHORT).show()

        } else loadRV()

    }

    private fun loadRV() {
        mRecyclerView = mBinding.listOfPlaces
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = PlacesListAdapter(dataPlaces, activity as AppCompatActivity)
        mBinding.listOfPlaces.adapter = adapter
    }
}