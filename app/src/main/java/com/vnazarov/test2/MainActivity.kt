package com.vnazarov.test2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.vnazarov.test2.data.currentPlace
import com.vnazarov.test2.databinding.ActivityMainBinding
import com.vnazarov.test2.fragments.currentPlace.CurrentPlaceFragment
import com.vnazarov.test2.helpers.loadData
import com.vnazarov.test2.helpers.replaceFragment
import com.vnazarov.test2.objects.Place

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mToolbar: Toolbar
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        requestQueue = Volley.newRequestQueue(this@MainActivity)
        mToolbar = mBinding.mainToolbar

        loadData(requestQueue, mBinding, this, mToolbar)
    }
}