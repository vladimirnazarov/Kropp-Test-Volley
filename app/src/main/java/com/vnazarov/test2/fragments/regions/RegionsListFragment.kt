package com.vnazarov.test2.fragments.regions

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
import com.vnazarov.test2.data.regions
import com.vnazarov.test2.databinding.FragmentRegionsListBinding
import com.vnazarov.test2.objects.Region

class RegionsListFragment: Fragment() {

    private lateinit var mBinding: FragmentRegionsListBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var adapter: RegionsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentRegionsListBinding.inflate(layoutInflater, container, false)

        loadRegions()

        return mBinding.root
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).title = "Regions"
    }

    private fun loadRegions(){
        if (regions.isEmpty()) {
            val regionList = arrayListOf<Region>()
            for (i in 1..10) {
                val region = Region(name = "Region test$i", R.drawable.demo_region)
                regionList.add(region)
            }

            regions = regionList

            loadRV()
        } else loadRV()
    }

    private fun loadRV(){
        mRecyclerView = mBinding.listOfRegions
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = RegionsListAdapter(regions, activity as AppCompatActivity)
        mBinding.listOfRegions.adapter = adapter
    }
}