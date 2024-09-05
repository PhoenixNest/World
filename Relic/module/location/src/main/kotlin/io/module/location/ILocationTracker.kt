package io.module.location

import android.location.Location
import io.module.location.impl.LocationTrackerImpl

/**
 * @see LocationTrackerImpl
 * */
interface ILocationTracker {

    suspend fun getCurrentLocation(): Location?

}