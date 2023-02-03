package com.vnazarov.test2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.vnazarov.test2.databinding.ActivityMainBinding
import com.vnazarov.test2.fragments.regions.RegionsListFragment
import com.vnazarov.test2.helpers.replaceFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()

        initFields()
        initFunc()
    }

    private fun initFields(){
        mToolbar = mBinding.mainToolbar
    }

    private fun initFunc(){
        setSupportActionBar(mToolbar)

        replaceFragment(RegionsListFragment(), false)
    }
}