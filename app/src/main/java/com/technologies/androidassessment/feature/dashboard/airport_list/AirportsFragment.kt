package com.technologies.androidassessment.feature.dashboard.airport_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.technologies.androidassessment.R
import com.technologies.androidassessment.core.base.BaseActivity
import com.technologies.androidassessment.core.base.BaseFragment
import com.technologies.androidassessment.core.base.BaseViewModel
import com.technologies.androidassessment.core.extension.goToActivity
import com.technologies.androidassessment.core.extension.observe
import com.technologies.androidassessment.databinding.FragmentAirportsBinding
import com.technologies.androidassessment.feature.dashboard.DashboardActivity
import com.technologies.androidassessment.feature.dashboard.airport_details.AirportDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.list_airports.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AirportsFragment : BaseFragment<FragmentAirportsBinding>() {

    @Inject
    lateinit var adapter: AirportListAdapter

    private val viewModel: AirportsViewModel by viewModels()

    private var twoPane = false

    override val layoutRes: Int
        get() = R.layout.fragment_airports

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreated(savedInstance: Bundle?) {
        twoPane = baseView.findViewById<ViewGroup>(R.id.nav_host_fragment) != null

        initViews()
        initObservers()
    }

    override fun onResume() {
        super.onResume()

        (activity as BaseActivity<*>).setToolbar(show = true, showBackButton = false, title = getString(
                    R.string.label_flights))
    }

    private fun initViews() {
        airports_rv_data.adapter = adapter

        airports_srl_data?.setOnRefreshListener {
            airports_srl_data.isRefreshing = false
            viewModel.getAirports()
        }

        adapter.clickListener = {
            val bundle = bundleOf(
                Pair(AirportDetailsActivity.ARGS_AIRPORT, gson.toJson(it))
            )

            if (twoPane) {
                val navHostFragment =
                    childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

                navHostFragment.navController.navigate(
                    R.id.airportDetailsFragment,
                    bundle
                )
            } else {
                goToActivity(
                    AirportDetailsActivity::class.java,
                    false,
                    bundle
                )
            }
        }
    }

    private fun initObservers() {
        viewModel.apply {
            getAirports()
            observe(airports){
                it?.let { adapter.collection = it }
            }
        }

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }
}