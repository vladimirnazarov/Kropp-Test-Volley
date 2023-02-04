package com.vnazarov.test2.fragments.cities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.vnazarov.test2.data.city
import com.vnazarov.test2.databinding.CityItemBinding
import com.vnazarov.test2.fragments.places.PlacesListFragment
import com.vnazarov.test2.helpers.replaceFragment
import com.vnazarov.test2.objects.City

class CitiesListAdapter(var citiesList: List<City>, val activity: AppCompatActivity): RecyclerView.Adapter<CitiesListAdapter.CitiesListHolder>() {

    inner class CitiesListHolder(val binding: CityItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesListHolder {
        val binding = CityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CitiesListHolder(binding)
    }

    override fun onBindViewHolder(holder: CitiesListHolder, position: Int) {
        with(holder){
            with(citiesList[position]){
                binding.cityItemName.text = this.name
                binding.cityImage.load(this.imageId){
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }

                binding.cityFullItem.isClickable = true
                binding.cityFullItem.setOnClickListener {
                    city = this.name
                    activity.replaceFragment(PlacesListFragment())
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return citiesList.size
    }


}