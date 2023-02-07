package com.vnazarov.test2.objects

data class Place(

    val id: Int = 0,

    val pointId: Int = 0,

    val name: String = "",

    val text: String = "",

    val sound: String = "",

    val language: Int = 0,

    val lastEditTime: Long = 0,

    val creationDate: String = "",

    val latitude: Double = 0.0,

    val longitude: Double = 0.0,

    val logo: String = "",

    val photo: String = "",

    val cityId: Int = 0,

    val isVisible: Boolean = false,

    val images: List<String> = arrayListOf(),

    val tags: List<Int> = arrayListOf(),

    val isExcursion: Boolean = false
)