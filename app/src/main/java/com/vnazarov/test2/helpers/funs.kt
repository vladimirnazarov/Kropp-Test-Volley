package com.vnazarov.test2.helpers

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.vnazarov.test2.MainActivity
import com.vnazarov.test2.R
import com.vnazarov.test2.data.*
import com.vnazarov.test2.databinding.ActivityMainBinding
import com.vnazarov.test2.fragments.regions.RegionsListFragment
import com.vnazarov.test2.objects.City
import com.vnazarov.test2.objects.Language
import com.vnazarov.test2.objects.Place
import com.vnazarov.test2.objects.Region


fun AppCompatActivity.replaceFragment(fragment: Fragment, addStack: Boolean = true) {
    if (addStack) {
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

fun enablePopBack(activity: MainActivity, toolbar: Toolbar) {
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener {
        activity.supportFragmentManager.popBackStack()
    }
}

fun disablePopBack(activity: MainActivity) {
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
}

/**
 * Activity setup fragment
 */
private fun initFunc(activity: MainActivity, mToolbar: Toolbar) {
    activity.setSupportActionBar(mToolbar)

    println("Language: $currentLanguage")
    activity.replaceFragment(RegionsListFragment(), false)
}

fun loadData(
    requestQueue: RequestQueue,
    mBinding: ActivityMainBinding,
    activity: MainActivity,
    mToolbar: Toolbar
) {

    initializeRegions()

    if (dataCities.isEmpty() && dataPlaces.isEmpty()) {

        val citiesList = arrayListOf<City>()
        val languagesList = arrayListOf<Language>()
        val placesList = arrayListOf<Place>()

        /**
         * URL's of JSON files
         */
        val urlCities = "https://krokapp.by/api/get_cities/11/"
        val urlLanguages = "https://krokapp.by/api/get_languages"
        val urlPlaces = "https://krokapp.by/api/get_points/11/"

        /**
         * 1st data pack
         */
        val requestCities = JsonArrayRequest(Request.Method.GET, urlCities, null, { response ->

            for (i in 0 until response.length()) {
                val jsonObject = response.getJSONObject(i)

                val city = City(
                    jsonObject.getInt("id_locale"),
                    jsonObject.getInt("id"),
                    jsonObject.getString("name"),
                    jsonObject.getInt("lang"),
                    jsonObject.getString("logo"),
                    jsonObject.getLong("last_edit_time"),
                    jsonObject.getBoolean("visible"),
                    jsonObject.getBoolean("city_is_regional"),
                    jsonObject.getString("region")
                )

                citiesList.add(city)
            }

            dataCities = citiesList
            requestQueue.cache.clear()

        }, {
            Log.e("Cities response error", it.message.toString())
            Toast.makeText(activity, "Cities: Something went wrong, relaunch the app", Toast.LENGTH_SHORT).show()
        })

        /**
         * 2nd data pack
         */
        val requestLanguages =
            JsonArrayRequest(Request.Method.GET, urlLanguages, null, { response ->

                for (i in 0 until response.length()) {
                    val jsonObject = response.getJSONObject(i)

                    val language = Language(
                        jsonObject.getInt("id"),
                        jsonObject.getString("key"),
                        jsonObject.getString("name")
                    )

                    languagesList.add(language)
                }

                dataLanguages = languagesList
                requestQueue.cache.clear()

            }, {
                Log.e("Languages response error", it.message.toString())
                Toast.makeText(activity, "Languages: Something went wrong, relaunch the app", Toast.LENGTH_SHORT).show()
            })

        /**
         * 3rd data pack
         */
        val requestPlaces = JsonArrayRequest(Request.Method.GET, urlPlaces, null, { response ->

            mBinding.activityProgressBar.visibility = View.GONE
            mBinding.activityAttention1.visibility = View.GONE
            mBinding.activityAttention2.visibility = View.GONE

            for (i in 0 until response.length()) {
                val jsonObject = response.getJSONObject(i)

                val jsonListString = jsonObject.getJSONArray("images")
                val jsonListInt = jsonObject.getJSONArray("tags")

                val listString = arrayListOf<String>()
                val listInt = arrayListOf<Int>()

                for (j in 0 until jsonListString.length()) {
                    listString.add(jsonListString[j] as String)
                }

                for (j in 0 until jsonListInt.length()) {
                    listInt.add(jsonListInt[j] as Int)
                }

                val place = Place(
                    jsonObject.getInt("id"),
                    jsonObject.getInt("id_point"),
                    jsonObject.getString("name"),
                    jsonObject.getString("text"),
                    jsonObject.getString("sound"),
                    jsonObject.getInt("lang"),
                    jsonObject.getLong("last_edit_time"),
                    jsonObject.getString("creation_date"),
                    jsonObject.getDouble("lat"),
                    jsonObject.getDouble("lng"),
                    jsonObject.getString("photo"),
                    jsonObject.getInt("city_id"),
                    jsonObject.getBoolean("visible"),
                    listString,
                    listInt,
                    jsonObject.getBoolean("is_excursion")
                )

                placesList.add(place)
            }

            dataPlaces = placesList
            requestQueue.cache.clear()
            initializeDialog(activity, mToolbar)

        }, {
            Log.e("Places response error", it.message.toString())
            Toast.makeText(activity, "Places: Something went wrong, relaunch the app", Toast.LENGTH_SHORT).show()
        })

        /**
         * Requests settings
         */
        requestCities.retryPolicy = object : DefaultRetryPolicy(10000, 1, 1f) {}
        requestLanguages.retryPolicy = object : DefaultRetryPolicy(10000, 1, 1f) {}
        requestPlaces.retryPolicy = object : DefaultRetryPolicy(100000, 1, 1f) {}

        requestQueue.add(requestCities)
        requestQueue.add(requestLanguages)
        requestQueue.add(requestPlaces)

    }
}

/**
 * a - language, b - city
 */
private fun initializeRegions() {
    val region11 = Region("Брэсцкая вобласць", R.drawable.r_emblem_brest, 1, "Brest region")
    val region21 = Region("Brest region", R.drawable.r_emblem_brest, 2, "Brest region")
    val region31 = Region("Брестская область", R.drawable.r_emblem_brest, 3, "Brest region")
    val region41 = Region("Brestská oblast", R.drawable.r_emblem_brest, 4, "Brest region")
    val region51 = Region("布雷斯特地区", R.drawable.r_emblem_brest, 5, "Brest region")

    val region12 = Region("Віцебская вобласць", R.drawable.r_emblem_vitebsk, 1, "Vitebsk region")
    val region22 = Region("Vitebsk region", R.drawable.r_emblem_vitebsk, 2, "Vitebsk region")
    val region32 = Region("Витебская область", R.drawable.r_emblem_vitebsk, 3, "Vitebsk region")
    val region42 = Region("Vitebská oblast", R.drawable.r_emblem_vitebsk, 4, "Vitebsk region")
    val region52 = Region("维捷布斯克地区", R.drawable.r_emblem_vitebsk, 5, "Vitebsk region")

    val region13 = Region("Гомельская вобласть", R.drawable.r_emblem_homel, 1, "Gomel region")
    val region23 = Region("Gomel region", R.drawable.r_emblem_homel, 2, "Gomel region")
    val region33 = Region("Гомельская область", R.drawable.r_emblem_homel, 3, "Gomel region")
    val region43 = Region("Gomelská oblast", R.drawable.r_emblem_homel, 4, "Gomel region")
    val region53 = Region("戈梅利地区", R.drawable.r_emblem_homel, 5, "Gomel region")

    val region14 = Region("Гродзенская вобласць", R.drawable.r_emblem_hrodna, 1, "Grodno region")
    val region24 = Region("Grodno region", R.drawable.r_emblem_hrodna, 2, "Grodno region")
    val region34 = Region("Гродненская область", R.drawable.r_emblem_hrodna, 3, "Grodno region")
    val region44 = Region("Region Grodno", R.drawable.r_emblem_hrodna, 4, "Grodno region")
    val region54 = Region("格罗德诺地区", R.drawable.r_emblem_hrodna, 5, "Grodno region")

    val region15 = Region("Магілёўская вобласць", R.drawable.r_emblem_mahileu, 1, "Mogilev region")
    val region25 = Region("Mogilev region", R.drawable.r_emblem_mahileu, 2, "Mogilev region")
    val region35 = Region("Могилёвская область", R.drawable.r_emblem_mahileu, 3, "Mogilev region")
    val region45 = Region("Mogilevská oblast", R.drawable.r_emblem_mahileu, 4, "Mogilev region")
    val region55 = Region("莫吉廖夫地区", R.drawable.r_emblem_mahileu, 5, "Mogilev region")

    val region16 = Region("Мінская вобласць", R.drawable.r_emblem_minsk, 1, "Minsk region")
    val region26 = Region("Minsk region", R.drawable.r_emblem_minsk, 2, "Minsk region")
    val region36 = Region("Минская область", R.drawable.r_emblem_minsk, 3, "Minsk region")
    val region46 = Region("Minská oblast", R.drawable.r_emblem_minsk, 4, "Minsk region")
    val region56 = Region("明斯克地区", R.drawable.r_emblem_minsk, 5, "Minsk region")

    val regionsList = arrayListOf<Region>(
        region11, region12, region13, region14, region15, region16,
        region21, region22, region23, region24, region25, region26,
        region31, region32, region33, region34, region35, region36,
        region41, region42, region43, region44, region45, region46,
        region51, region52, region53, region54, region55, region56
    )

    dataRegions = regionsList
}

private fun initializeDialog(activity: MainActivity, mToolbar: Toolbar) {
    val dialog = Dialog(activity)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.language_dialog)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)

    val layoutParams = WindowManager.LayoutParams()
    layoutParams.copyFrom(dialog.window!!.attributes)
    val dialogWindowWidth = 1000
    layoutParams.width = dialogWindowWidth
    dialog.window?.attributes = layoutParams

    val rLang1 = dialog.findViewById<RadioButton>(R.id.lang_1_radio_button)
    val rLang2 = dialog.findViewById<RadioButton>(R.id.lang_2_radio_button)
    val rLang3 = dialog.findViewById<RadioButton>(R.id.lang_3_radio_button)
    val rLang4 = dialog.findViewById<RadioButton>(R.id.lang_4_radio_button)
    val rLang5 = dialog.findViewById<RadioButton>(R.id.lang_5_radio_button)

    rLang1.setOnClickListener {
        currentLanguage = 1
        dialog.hide()

        initLangData(1)

        initFunc(activity, mToolbar)
    }
    rLang2.setOnClickListener {
        currentLanguage = 2
        dialog.hide()

        initLangData(2)

        initFunc(activity, mToolbar)
    }
    rLang3.setOnClickListener {
        currentLanguage = 3
        dialog.hide()

        initLangData(3)

        initFunc(activity, mToolbar)
    }
    rLang4.setOnClickListener {
        currentLanguage = 4
        dialog.hide()

        initLangData(4)

        initFunc(activity, mToolbar)
    }
    rLang5.setOnClickListener {
        currentLanguage = 5
        dialog.hide()

        initLangData(5)

        initFunc(activity, mToolbar)
    }

    dialog.show()
}

private fun initializeLanguageRegions(lang: Int){
    val regionsLang = arrayListOf<Region>()
    for (i in 0 until dataRegions.size){
        if (dataRegions[i].language == lang) regionsLang.add(dataRegions[i])
    }

    dataRegions = regionsLang
}

private fun initializeLanguageCities(lang: Int){
    val citiesLang = arrayListOf<City>()
    for (i in 0 until dataCities.size){
        if (dataCities[i].language == lang) citiesLang.add(dataCities[i])
    }

    dataCities = citiesLang
}

private fun initializeLanguagePlaces(lang: Int){
    val placesLang = arrayListOf<Place>()
    for (i in 0 until dataPlaces.size){
        if (dataPlaces[i].language == lang) placesLang.add(dataPlaces[i])
    }

    dataPlaces = placesLang
}

private fun initLangData(lang: Int){
    initializeLanguageRegions(lang)
    initializeLanguageCities(lang)
    initializeLanguagePlaces(lang)
}