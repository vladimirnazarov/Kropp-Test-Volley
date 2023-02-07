package com.vnazarov.test2.fragments.cities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vnazarov.test2.data.currentCity
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

                Picasso.get().load(this.cityEmblem).into(binding.cityImage)

                binding.cityItemName.text = this.cityName

                binding.cityFullItem.isClickable = true
                binding.cityFullItem.setOnClickListener {

                    currentCity = this
                    activity.replaceFragment(PlacesListFragment())
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return citiesList.size
    }
}