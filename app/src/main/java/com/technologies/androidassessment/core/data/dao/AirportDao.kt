
package com.technologies.androidassessment.core.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.technologies.androidassessment.core.data.entity.Airport
import com.technologies.androidassessment.core.data.entity.TBL_AIRPORT

@Dao
interface AirportDao: BaseDao<Airport> {

    @Query("SELECT * FROM $TBL_AIRPORT")
    fun getProducts(): List<Airport>

}