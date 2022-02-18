package com.technologies.androidassessment.core.network.flight

import com.technologies.androidassessment.core.data.dao.AirportDao
import com.technologies.androidassessment.core.data.entity.Airport
import com.technologies.androidassessment.core.data.network.Resource
import com.technologies.androidassessment.core.utils.NetworkHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

interface FlightRepository {

    fun getAirports(): Flow<Resource<List<Airport>>>

    class FlightRepositoryImpl
    @Inject constructor(
        private val service: FlightService,
        private val airportDao: AirportDao,
        private val networkHandler: NetworkHandler
    ) : FlightRepository {
        override fun getAirports(): Flow<Resource<List<Airport>>> {
            return flow {
                emit(Resource.loading(data = null))
                try {
                    if (networkHandler.isConnected) {
                        service.getAirports().let {
                            airportDao.insert(it)
                            emit(Resource.success(data = it))
                        }
                    } else {
                        emit(
                            Resource.success(
                                data = airportDao.getAirports()
                            )
                        )
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()
                    emit(
                        Resource.error(
                            data = null, message = exception.message ?: "Error Occurred!"
                        )
                    )
                }

            }.flowOn(Dispatchers.IO).take(2)
        }

    }
}