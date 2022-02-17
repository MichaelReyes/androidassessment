package com.technologies.androidassessment.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.technologies.androidassessment.core.data.network.Resource
import com.technologies.androidassessment.core.data.network.Status
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException

/**
 * To act as a super class for all other ViewModel.
 * Handing common LiveDatas used in ViewModel classes such as
 * Loading, error, internet connection, message, etc.
 */
abstract class BaseViewModel : ViewModel() {

    protected val gson = Gson()

    protected val disposable = CompositeDisposable()

    private val _hasInternetConnection = MutableLiveData<Boolean>()
    val hasInternetConnection: LiveData<Boolean> = _hasInternetConnection
    fun setHasInternetConnection(value: Boolean) {
        _hasInternetConnection.postValue(value)
    }

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading
    fun setLoading(value: Boolean) {
        _loading.postValue(value)
    }

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    fun setError(value: String) {
        _error.postValue(value)
    }

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    fun setMessage(value: String) {
        _message.postValue(value)
    }

    protected fun handleException(exception: Throwable): String? {
        return if (exception is HttpException) {
            exception.response()?.errorBody()?.string()
        } else
            exception.localizedMessage
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    /**
     *  For cleaner and shorter implementation of handling API response
     *  To only pass onError and onLoading if you have specific handling
     *  To be able to observe onSuccess only
     */
    suspend fun <T> Resource<T>.handleResponse(
        onError: (suspend (Resource<T>) -> Unit)? = null,
        onLoading: (suspend (Resource<T>) -> Unit)? = null,
        onSuccess: suspend (Resource<T>) -> Unit,
    ) {
        when (status) {
            Status.SUCCESS -> {
                setLoading(false)
                onSuccess.invoke(this)
            }
            Status.ERROR -> {
                setLoading(false)
                setError(message ?: "")
                onError?.invoke(this)
            }
            Status.LOADING -> {
                setLoading(true)
                onLoading?.invoke(this)
            }
        }
    }
}