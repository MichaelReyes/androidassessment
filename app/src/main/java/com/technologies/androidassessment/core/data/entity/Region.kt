package com.technologies.androidassessment.core.data.entity

import androidx.room.ColumnInfo

data class Region(
    @ColumnInfo(name = "region_code")
    val regionCode: String,
    @ColumnInfo(name = "region_name")
    val regionName: String
)