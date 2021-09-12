package com.technologies.androidassessment.core.network.flight

import com.technologies.androidassessment.core.data.entity.Airport
import retrofit2.http.GET

interface FlightApi {

    @GET("flight/refData/airport")
    suspend fun getAirports(): List<Airport>
}