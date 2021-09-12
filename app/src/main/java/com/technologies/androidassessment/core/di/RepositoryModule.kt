package com.technologies.androidassessment.core.di

import com.technologies.androidassessment.core.data.dao.AirportDao
import com.technologies.androidassessment.core.network.flight.FlightRepository
import com.technologies.androidassessment.core.network.flight.FlightService
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

}