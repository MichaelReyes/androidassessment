package com.technologies.androidassessment.feature.dashboard.airport_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.technologies.androidassessment.R
import com.technologies.androidassessment.core.base.BaseFragment
import com.technologies.androidassessment.core.base.BaseViewModel
import com.technologies.androidassessment.databinding.FragmentAirportsBinding

class AirportsFragment : BaseFragment<FragmentAirportsBinding>() {

    override val layoutRes: Int
        get() = R.layout.fragment_airports

    override fun onCreated(savedInstance: Bundle?) {

    }

    override fun getViewModel(): BaseViewModel? = null
}