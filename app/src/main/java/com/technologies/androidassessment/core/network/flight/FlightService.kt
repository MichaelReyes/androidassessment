package com.technologies.androidassessment.core.network.flight

import com.technologies.androidassessment.core.data.entity.Airport
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlightService @Inject constructor(retrofit: Retrofit) : FlightApi {
    private val api by lazy { retrofit.create(FlightApi::class.java) }

    override suspend fun getAirports(): List<Airport> =
        api.getAirports()

}