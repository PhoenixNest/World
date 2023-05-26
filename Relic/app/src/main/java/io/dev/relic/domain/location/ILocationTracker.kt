package io.dev.relic.domain.location

import android.location.Location

/**
 * @see io.dev.relic.core.device.location.LocationTrackerImpl
 * */
interface ILocationTracker {

    suspend fun getCurrentLocation(): Location?

}