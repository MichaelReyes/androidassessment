package com.technologies.androidassessment.feature.dashboard.airport_details

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
class AirportDetailsViewModel @Inject constructor(
    private val flightRepository: FlightRepository
) : BaseViewModel() {

    private val _airport = MutableLiveData<Airport>()
    val airport: LiveData<Airport> = _airport

    private val _showBackButton = MutableLiveData(false)
    val showBackButton: LiveData<Boolean> = _showBackButton

    fun setAirport(value: Airport) {
        _airport.value = value
    }

    fun setShowBackButton(value: Boolean) {
        _showBackButton.value = value
    }
}