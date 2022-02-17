package com.technologies.androidassessment.core.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.technologies.androidassessment.core.data.entity.Airport
import com.technologies.androidassessment.core.data.entity.TBL_AIRPORT
import io.reactivex.Single

@Dao
interface AirportDao : BaseDao<Airport> {

    @Query("SELECT * FROM $TBL_AIRPORT")
    fun getAirports(): List<Airport>

    @Query("SELECT * FROM $TBL_AIRPORT")
    fun getAirportsRx(): Single<List<Airport>>

}