package io.module.location

import android.location.Location

/**
 * @see io.module.map.impl.LocationTrackerImpl
 * */
interface ILocationTracker {

    suspend fun getCurrentLocation(): Location?

}