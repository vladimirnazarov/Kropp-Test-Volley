package com.vnazarov.test2.objects

data class City(

    val locationId: Int = 0,

    val id: Int = 0,

    val cityName: String = "",

    val language: Int = 0,

    val cityEmblem: String = "",

    val lastEditTime: Long = 0,

    val isCityVisible: Boolean = false,

    val isCityRegional: Boolean = false,

    val cityRegion: String = ""
)
