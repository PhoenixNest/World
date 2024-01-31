package io.domain.use_case.lcoation

import io.domain.use_case.lcoation.action.GetCurrentLocation

internal const val TAG = "LocationUnitUseCase"

data class LocationUseCase(
    val getCurrentLocation: GetCurrentLocation
)