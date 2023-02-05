package com.vnazarov.test2.objects

data class City(

    var locationId: Int,

    var id: Int,

    var cityName: String,

    var lang: Int,

    var cityEmblem: String,

    var lastEditTime: Long,

    var isCityVisible: Boolean,

    var isCityRegional: Boolean,

    var cityRegion: String
)
