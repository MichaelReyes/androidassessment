package com.technologies.androidassessment.core.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TBL_AIRPORT = "tbl_airport"

@Entity(tableName = TBL_AIRPORT)
data class Airport(
    @PrimaryKey
    @ColumnInfo(name = "airport_code")
    val airportCode: String,
    @ColumnInfo(name = "airport_name")
    val airportName: String,
    @Embedded
    val city: City,
    @Embedded
    val country: Country,
    @ColumnInfo(name = "domestic_airport")
    val domesticAirport: Boolean,
    @ColumnInfo(name = "e_ticketable_airport")
    val eticketableAirport: Boolean,
    @ColumnInfo(name = "international_airport")
    val internationalAirport: Boolean,
    @Embedded
    val location: Location,
    @ColumnInfo(name = "online_indicator")
    val onlineIndicator: Boolean,
    @Embedded
    val region: Region,
    @ColumnInfo(name = "regional_airport")
    val regionalAirport: Boolean
)