package com.technologies.androidassessment.feature.dashboard.airport_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.technologies.androidassessment.core.base.BaseViewModel
import com.technologies.androidassessment.core.data.entity.Airport
import com.technologies.androidassessment.core.network.flight.FlightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class AirportsViewModel @Inject constructor(
    private val flightRepository: FlightRepository
) : BaseViewModel() {

    private val _airports = MutableLiveData<List<Airport>>()
    val airports: LiveData<List<Airport>> = _airports

    fun getAirports() {
        flightRepository.getAirports()
            .onEach { resource ->
                resource.handleResponse {
                    it.data?.let { _airports.value = it }
                }
            }.launchIn(viewModelScope)
    }
}