package io.domain.use_case.lcoation

import io.domain.use_case.lcoation.action.AccessCurrentLocation

internal const val TAG = "LocationUnitUseCase"

data class LocationUseCase(
    val accessCurrentLocation: AccessCurrentLocation
)