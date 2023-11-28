package io.module.map

import android.location.Location

/**
 * @see io.module.map.impl.LocationTrackerImpl
 * */
interface ILocationTracker {

    suspend fun getCurrentLocation(): Location?

}