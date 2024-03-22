package io.dev.relic.feature.pages.explore

import com.tomtom.sdk.location.LocationProvider
import com.tomtom.sdk.location.OnLocationUpdateListener
import com.tomtom.sdk.map.display.MapOptions

data class ExploreTomTomMapState(
    val mapOptions: MapOptions?,
    val locationProvider: LocationProvider?,
    val locationUpdateListener: OnLocationUpdateListener?
)