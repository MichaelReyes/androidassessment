package com.technologies.androidassessment.feature.dashboard.airport_details

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.technologies.androidassessment.R
import com.technologies.androidassessment.core.base.BaseActivity
import com.technologies.androidassessment.databinding.ActivityAirportDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AirportDetailsActivity : BaseActivity<ActivityAirportDetailsBinding>() {
    override val layoutRes
        get() = R.layout.activity_airport_details

    override fun onCreated(instance: Bundle?) {
        setSupportActionBar(binding.toolbar)
        findNavController(R.id.nav_host_fragment).navigate(
            R.id.airportDetailsFragment,
            intent.extras
        )
    }

    companion object {
        const val ACTIVITY_CODE = 1234
        const val ARGS_AIRPORT = "_args_airport"
    }
}