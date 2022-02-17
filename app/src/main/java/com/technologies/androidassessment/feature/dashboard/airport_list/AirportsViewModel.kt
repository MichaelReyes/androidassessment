package com.technologies.androidassessment.feature.dashboard.airport_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.technologies.androidassessment.BuildConfig
import com.technologies.androidassessment.core.base.BaseViewModel
import com.technologies.androidassessment.core.data.entity.Airport
import com.technologies.androidassessment.core.network.flight.FlightRepository
import com.technologies.androidassessment.core.network.fligth_rx.FlightRepositoryRx
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class AirportsViewModel @Inject constructor(
    private val flightRepository: FlightRepository,
    private val flightRepositoryRx: FlightRepositoryRx,
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

    fun getAirportsRx() {
        //Purpose of delay is just to show the loader for a few seconds
        flightRepositoryRx.getAirports()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { setLoading(true) }
            .delay(3000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
            .doAfterTerminate { setLoading(false) }
            .subscribeBy(
                onSuccess = {
                    _airports.value = it
                },
                onError = {
                    if (BuildConfig.DEBUG) {
                        it.printStackTrace()
                    }
                    handleException(it)?.let { setError(it) }
                }
            ).addTo(disposable)
    }
}