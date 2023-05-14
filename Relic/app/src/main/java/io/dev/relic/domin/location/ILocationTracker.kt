package io.dev.relic.domin.location

import android.location.Location

interface ILocationTracker {

    suspend fun getCurrentLocation(): Location?

}