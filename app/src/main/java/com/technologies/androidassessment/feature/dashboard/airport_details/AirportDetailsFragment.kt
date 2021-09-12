package com.technologies.androidassessment.feature.dashboard.airport_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.technologies.androidassessment.R
import com.technologies.androidassessment.core.base.BaseActivity
import com.technologies.androidassessment.core.base.BaseFragment
import com.technologies.androidassessment.core.base.BaseViewModel
import com.technologies.androidassessment.core.data.entity.Location
import com.technologies.androidassessment.core.extension.fromJson
import com.technologies.androidassessment.core.extension.observe
import com.technologies.androidassessment.databinding.FragmentAirportDetailsBindingImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AirportDetailsFragment : BaseFragment<FragmentAirportDetailsBindingImpl>(), AirportDetailsHandler {

    private val viewModel: AirportDetailsViewModel by viewModels()

    override val layoutRes: Int
        get() = R.layout.fragment_airport_details

    override fun onBack() {
        activity?.finish()
    }

    override fun onViewLocation(location: Location) {
        val uri = Uri.parse("google.navigation:q=${location.latitude},${location.longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(mapIntent)
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreated(savedInstance: Bundle?) {
        initBinding()
        initObserver()
        checkExtras()
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.handler = this
    }

    private fun initObserver() {
        viewModel.apply {
            setShowBackButton(activity is AirportDetailsActivity)
            observe(airport){
                it?.let {
                    if(activity is AirportDetailsActivity) {
                        (activity as BaseActivity<*>).setToolbar(
                            show = true, showBackButton = false, title = it.airportName
                        )
                    }
                }
            }
        }
    }

    private fun checkExtras() {
        arguments?.let {
            if (it.containsKey(AirportDetailsActivity.ARGS_AIRPORT)) {
                viewModel.setAirport(
                    gson.fromJson(
                        it.getString(
                            AirportDetailsActivity.ARGS_AIRPORT,
                            ""
                        )
                    )
                )
            }
        }
    }
}