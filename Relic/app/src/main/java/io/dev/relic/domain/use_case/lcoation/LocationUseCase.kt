package io.dev.relic.domain.use_case.lcoation

import io.dev.relic.domain.use_case.lcoation.action.AccessCurrentLocation

internal const val TAG = "LocationUnitUseCase"

data class LocationUseCase(
    val accessCurrentLocation: AccessCurrentLocation
)