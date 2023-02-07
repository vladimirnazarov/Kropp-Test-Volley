package com.vnazarov.test2.fragments.currentPlace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.vnazarov.test2.MainActivity
import com.vnazarov.test2.data.currentPlace
import com.vnazarov.test2.data.currentPlaceImage
import com.vnazarov.test2.databinding.FragmentCurrentPlaceBinding
import com.vnazarov.test2.helpers.disablePopBack
import com.vnazarov.test2.helpers.enablePopBack

class CurrentPlaceFragment: Fragment() {

    private lateinit var mBinding: FragmentCurrentPlaceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCurrentPlaceBinding.inflate(layoutInflater, container, false)

        return mBinding.root
    }

    override fun onResume() {
        super.onResume()

        mBinding.currentPlaceTest.text = currentPlace
        mBinding.currentPlaceImage.load(currentPlaceImage)
        (activity as MainActivity).title = currentPlace
        enablePopBack(activity as MainActivity, (activity as MainActivity).mToolbar)
    }

    override fun onStop() {
        super.onStop()

        disablePopBack(activity as MainActivity)
    }
}