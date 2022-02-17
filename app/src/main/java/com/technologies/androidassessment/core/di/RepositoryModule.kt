package com.technologies.androidassessment.core.di

import com.technologies.androidassessment.core.data.dao.AirportDao
import com.technologies.androidassessment.core.network.flight.FlightRepository
import com.technologies.androidassessment.core.network.flight.FlightService
import com.technologies.androidassessment.core.network.fligth_rx.FlightRepositoryRx
import com.technologies.androidassessment.core.network.fligth_rx.FlightServiceRx
import com.technologies.androidassessment.core.utils.NetworkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideFlightRepository(
        service: FlightService,
        airportDao: AirportDao,
        networkHandler: NetworkHandler
    ): FlightRepository {
        return FlightRepository.FlightRepositoryImpl(service, airportDao, networkHandler)
    }

    @Provides
    @ViewModelScoped
    fun provideFlightRepositoryRx(
        service: FlightServiceRx,
        airportDao: AirportDao,
        networkHandler: NetworkHandler
    ): FlightRepositoryRx {
        return FlightRepositoryRx.FlightRepositoryRxImpl(service, airportDao, networkHandler)
    }
}