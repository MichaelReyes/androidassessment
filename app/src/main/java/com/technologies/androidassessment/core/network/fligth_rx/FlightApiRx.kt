package com.technologies.androidassessment.core.network.fligth_rx

import com.technologies.androidassessment.core.data.entity.Airport
import io.reactivex.Single
import retrofit2.http.GET

interface FlightApiRx {

    @GET("flight/refData/airport")
    fun getAirportsRx(): Single<List<Airport>>
}