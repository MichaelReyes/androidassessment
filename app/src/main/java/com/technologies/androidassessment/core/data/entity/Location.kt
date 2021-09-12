package com.technologies.androidassessment.core.data.entity

import androidx.room.ColumnInfo

data class Location(
    @ColumnInfo(name = "above_sea_level")
    val aboveSeaLevel: Int,
    val latitude: Double,
    @ColumnInfo(name = "latitude_direction")
    val latitudeDirection: String,
    @ColumnInfo(name = "latitude_radius")
    val latitudeRadius: Double,
    val longitude: Double,
    @ColumnInfo(name = "longitude_direction")
    val longitudeDirection: String,
    @ColumnInfo(name = "longitude_radius")
    val longitudeRadius: Double
)