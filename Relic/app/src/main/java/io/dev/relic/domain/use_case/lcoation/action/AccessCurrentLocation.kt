package io.dev.relic.domain.use_case.lcoation.action

import android.location.Location
import io.dev.relic.domain.location.ILocationTracker
import io.dev.relic.domain.use_case.lcoation.TAG
import io.dev.relic.global.utils.LogUtil
import javax.inject.Inject

class AccessCurrentLocation @Inject constructor(
    private val locationTracker: ILocationTracker
) {

    interface ILocationListener {

        fun onAccessing()

        fun onAccessSucceed(location: Location)

        fun onAccessFailed(errorMessage: String)

    }

    suspend operator fun invoke(listener: ILocationListener) {
        LogUtil.verbose(TAG, "[LocationTracker] Attempts to get the current device location.")
        listener.onAccessing()

        // Try to access the current location of the device.
        locationTracker.getCurrentLocation()?.run {
            LogUtil.debug(TAG, "[LocationTracker] Get the location information succeeded.")
            listener.onAccessSucceed(this)

        } ?: run {
            val errorMessage = "Couldn't retrieve the location of the current device."
            LogUtil.error(TAG, "[LocationTracker] $errorMessage")
            listener.onAccessFailed(errorMessage = errorMessage)
        }
    }

}