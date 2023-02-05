package com.vnazarov.test2.helpers

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.vnazarov.test2.MainActivity
import com.vnazarov.test2.R

fun AppCompatActivity.replaceFragment(fragment: Fragment, addStack: Boolean = true) {
    if (addStack){
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.data_container, fragment)
            .commit()
    } else {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.data_container, fragment)
            .commit()
    }
}

fun enablePopBack(activity: MainActivity, toolbar: Toolbar){
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener{
        activity.supportFragmentManager.popBackStack()
    }
}

fun disablePopBack(activity: MainActivity){
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
}