package com.technologies.androidassessment.feature.dashboard.airport_details

import android.os.Bundle
import com.technologies.androidassessment.R
import com.technologies.androidassessment.core.base.BaseActivity
import com.technologies.androidassessment.databinding.ActivityAirportDetailsBinding

class AirportDetailsActivity : BaseActivity<ActivityAirportDetailsBinding>() {
    override val layoutRes
        get() = R.layout.activity_airport_details

    override fun onCreated(instance: Bundle?) {
        setSupportActionBar(binding.toolbar)
    }

    companion object {
        const val ACTIVITY_CODE = 1234
        const val ARGS_AIRPORT = "_args_airport"
    }
}