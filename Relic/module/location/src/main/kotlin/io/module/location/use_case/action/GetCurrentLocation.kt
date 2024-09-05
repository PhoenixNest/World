package io.module.location.use_case.action

import android.util.Log
import io.module.location.ILocationListener
import io.module.location.ILocationTracker
import io.module.location.use_case.TAG
import javax.inject.Inject

class GetCurrentLocation @Inject constructor(
    private val locationTracker: ILocationTracker
) {
    suspend operator fun invoke(listener: ILocationListener) {
        Log.v(TAG, "[LocationTracker] Attempts to get the current device location.")
        listener.onAccessing()

        // Try to access the current location of the device.
        locationTracker.getCurrentLocation()?.run {
            Log.d(TAG, "[LocationTracker] Get the location information succeeded.")
            listener.onAccessSucceed(this)
        } ?: run {
            val errorMessage = "Couldn't retrieve the location of the current device."
            Log.e(TAG, "[LocationTracker] $errorMessage")
            listener.onAccessFailed(errorMessage = errorMessage)
        }
    }
}