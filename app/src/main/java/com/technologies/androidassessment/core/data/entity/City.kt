package com.technologies.androidassessment.core.data.entity

import androidx.room.ColumnInfo

data class City(
    @ColumnInfo(name = "city_code")
    val cityCode: String,
    @ColumnInfo(name = "city_name")
    val cityName: String,
    @ColumnInfo(name = "time_zone_name")
    val timeZoneName: String
)