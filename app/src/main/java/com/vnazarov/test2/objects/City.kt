package com.vnazarov.test2.objects

data class City(

    val locationId: Int,

    val id: Int,

    val cityName: String,

    val language: Int,

    val cityEmblem: String,

    val lastEditTime: Long,

    val isCityVisible: Boolean,

    val isCityRegional: Boolean,

    val cityRegion: String
)
