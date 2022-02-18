package com.technologies.androidassessment.core.network.fligth_rx

import com.technologies.androidassessment.core.data.dao.AirportDao
import com.technologies.androidassessment.core.data.entity.Airport
import com.technologies.androidassessment.core.utils.NetworkHandler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface FlightRepositoryRx {

    fun getAirports(): Single<List<Airport>>

    class FlightRepositoryRxImpl
    @Inject constructor(
        private val service: FlightServiceRx,
        private val airportDao: AirportDao,
        private val networkHandler: NetworkHandler
    ) : FlightRepositoryRx {
        override fun getAirports(): Single<List<Airport>> {
            return if (networkHandler.isConnected) {
                service.getAirportsRx().flatMap {
                    airportDao.insertRx(it)
                        .andThen(Single.just(it))
                }
            } else {
                airportDao.getAirportsRx()
            }.subscribeOn(Schedulers.io())
        }
    }
}