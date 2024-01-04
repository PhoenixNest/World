package io.domain.use_case.lcoation.action

import io.domain.use_case.lcoation.TAG
import io.common.util.LogUtil
import io.module.map.ILocationListener
import io.module.map.ILocationTracker
import javax.inject.Inject

class AccessCurrentLocation @Inject constructor(
    private val locationTracker: ILocationTracker
) {
    suspend operator fun invoke(listener: ILocationListener) {
        LogUtil.v(TAG, "[LocationTracker] Attempts to get the current device location.")
        listener.onAccessing()

        // Try to access the current location of the device.
        locationTracker.getCurrentLocation()?.run {
            LogUtil.d(TAG, "[LocationTracker] Get the location information succeeded.")
            listener.onAccessSucceed(this)
        } ?: run {
            val errorMessage = "Couldn't retrieve the location of the current device."
            LogUtil.e(TAG, "[LocationTracker] $errorMessage")
            listener.onAccessFailed(errorMessage = errorMessage)
        }
    }
}