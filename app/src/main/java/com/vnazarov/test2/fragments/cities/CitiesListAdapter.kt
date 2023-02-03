package com.vnazarov.test2.fragments.cities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vnazarov.test2.databinding.CityItemBinding
import com.vnazarov.test2.objects.City

class CitiesListAdapter(var citiesList: List<City>): RecyclerView.Adapter<CitiesListAdapter.CitiesListHolder>() {

    inner class CitiesListHolder(val binding: CityItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesListHolder {
        val binding = CityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CitiesListHolder(binding)
    }

    override fun onBindViewHolder(holder: CitiesListHolder, position: Int) {
        with(holder){
            with(citiesList[position]){
                binding.cityItemName.text = this.name
            }
        }
    }

    override fun getItemCount(): Int {
        return citiesList.size
    }


}