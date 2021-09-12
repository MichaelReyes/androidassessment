package com.technologies.androidassessment.feature.dashboard.airport_details

import com.technologies.androidassessment.core.data.entity.Location

interface AirportDetailsHandler {

    fun onViewLocation(location: Location)

    fun onBack()

}