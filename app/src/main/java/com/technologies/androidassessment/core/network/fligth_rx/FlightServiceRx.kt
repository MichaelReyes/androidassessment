package com.technologies.androidassessment.core.network.fligth_rx

import com.technologies.androidassessment.core.data.entity.Airport
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlightServiceRx @Inject constructor(retrofit: Retrofit) : FlightApiRx {
    private val api by lazy { retrofit.create(FlightApiRx::class.java) }

    override fun getAirportsRx(): Single<List<Airport>> = api.getAirportsRx()

}