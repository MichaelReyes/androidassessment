package com.technologies.androidassessment.core.data.entity

import androidx.room.ColumnInfo

data class Country(
    @ColumnInfo(name = "country_code")
    val countryCode: String,
    @ColumnInfo(name = "country_name")
    val countryName: String?
)