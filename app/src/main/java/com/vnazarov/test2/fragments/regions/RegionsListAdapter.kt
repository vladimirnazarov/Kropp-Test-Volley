package com.vnazarov.test2.fragments.regions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vnazarov.test2.data.currentRegion
import com.vnazarov.test2.data.currentRegionName
import com.vnazarov.test2.databinding.RegionItemBinding
import com.vnazarov.test2.fragments.cities.CitiesListFragment
import com.vnazarov.test2.helpers.replaceFragment
import com.vnazarov.test2.objects.Region

class RegionsListAdapter(var regionList: List<Region>, private val activity: AppCompatActivity): RecyclerView.Adapter<RegionsListAdapter.RegionListHolder>() {

    inner class RegionListHolder(val binding: RegionItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionListHolder {
        val binding = RegionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RegionListHolder(binding)
    }

    override fun onBindViewHolder(holder: RegionListHolder, position: Int) {
        with(holder){
            with(regionList[position]){
                binding.regionItemName.text = this.name
                binding.regionImage.load(this.imageId)

                binding.regionFullItem.isClickable = true
                binding.regionFullItem.setOnClickListener {
                    currentRegion = this.region
                    currentRegionName = this.name
                    activity.replaceFragment(CitiesListFragment())
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return regionList.size
    }
}