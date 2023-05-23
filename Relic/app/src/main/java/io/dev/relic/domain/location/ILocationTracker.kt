package io.dev.relic.domain.location

import android.location.Location

interface ILocationTracker {

    suspend fun getCurrentLocation(): Location?

}