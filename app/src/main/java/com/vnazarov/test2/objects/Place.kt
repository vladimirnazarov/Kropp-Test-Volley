package com.vnazarov.test2.objects

data class Place(

    val id: Int,

    val pointId: Int,

    val name: String,

    val text: String,

    val sound: String,

    val language: Int,

    val lastEditTime: Long,

    val creationDate: String,

    val latitude: Double,

    val longitude: Double,

    val photo: String,

    val cityId: Int,

    val isVisible: Boolean,

    val images: List<String>,

    val tags: List<Int>,

    val isExcursion: Boolean
)