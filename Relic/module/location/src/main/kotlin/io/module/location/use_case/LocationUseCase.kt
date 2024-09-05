package io.module.location.use_case

import io.module.location.use_case.action.GetCurrentLocation

internal const val TAG = "LocationUnitUseCase"

data class LocationUseCase(
    val getCurrentLocation: GetCurrentLocation
)