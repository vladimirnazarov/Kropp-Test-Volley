package com.vnazarov.test2.fragments.places

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.vnazarov.test2.data.place
import com.vnazarov.test2.databinding.PlaceItemBinding
import com.vnazarov.test2.fragments.currentPlace.CurrentPlaceFragment
import com.vnazarov.test2.helpers.replaceFragment
import com.vnazarov.test2.objects.Place

class PlacesListAdapter(var placesList: List<Place>, private val activity: AppCompatActivity): RecyclerView.Adapter<PlacesListAdapter.PlacesListHolder>() {

    inner class PlacesListHolder(var binding: PlaceItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesListHolder {
        val binding = PlaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PlacesListHolder(binding)
    }

    override fun onBindViewHolder(holder: PlacesListHolder, position: Int) {
        with(holder){
            with(placesList[position]){
                binding.placeItemName.text = this.name
                binding.placeFullItem.isClickable = true
                binding.placeFullItem.setOnClickListener {
                    place = this.name
                    activity.replaceFragment(CurrentPlaceFragment())
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return placesList.size
    }


}