package com.vnazarov.test2.data

import com.vnazarov.test2.objects.City
import com.vnazarov.test2.objects.Language
import com.vnazarov.test2.objects.Place
import com.vnazarov.test2.objects.Region
import java.util.concurrent.TimeUnit

var currentRegionName = ""
var currentRegion = ""
var currentCity = City()
var currentPlace = Place()
var currentLanguage = 0

var dataRegions = arrayListOf<Region>()
var dataCities = arrayListOf<City>()
var dataPlaces = arrayListOf<Place>()
var dataLanguages = arrayListOf<Language>()


var maxMinutes = 0L
var maxSeconds = 0L
var currentMinutes = 0
var currentSeconds = 0