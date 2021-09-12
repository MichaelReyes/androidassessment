package com.technologies.androidassessment.feature.dashboard

import android.os.Bundle
import com.technologies.androidassessment.R
import com.technologies.androidassessment.core.base.BaseActivity
import com.technologies.androidassessment.databinding.ActivityDashboardBinding

class DashboardActivity : BaseActivity<ActivityDashboardBinding>() {
    override val layoutRes: Int
        get() = R.layout.activity_dashboard

    override fun onCreated(instance: Bundle?) {
        setSupportActionBar(binding.toolbar)
    }
}