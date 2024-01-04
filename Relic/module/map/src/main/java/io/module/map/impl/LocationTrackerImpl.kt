package io.module.map.impl

import android.Manifest
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.qualifiers.ApplicationContext
import io.common.RelicPermissionDetector.Native.checkPermission
import io.common.util.LogUtil
import io.module.map.ILocationTracker
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class LocationTrackerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val locationClient: FusedLocationProviderClient
) : ILocationTracker {

    companion object {
        private const val TAG = "LocationTracker"
    }

    override suspend fun getCurrentLocation(): Location? {

        val hasAccessFindLocationPermission = checkPermission(
            context = context,
            requestPermission = Manifest.permission.ACCESS_FINE_LOCATION
        ).also {
            LogUtil.d(TAG, "[Permission Status] [Find Location] isGranted: $it")
        }

        val hasAccessCoarseLocationPermission = checkPermission(
            context = context,
            requestPermission = Manifest.permission.ACCESS_COARSE_LOCATION
        ).also {
            LogUtil.d(TAG, "[Permission Status] [Access Coarse Location] isGranted: $it")
        }

        val locationManager = context.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

        val isGpsEnabled: Boolean = (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            .also {
                LogUtil.d(TAG, "[GPS Status] isEnabled: $it")
            }

        if (!hasAccessFindLocationPermission
            || !hasAccessCoarseLocationPermission
            || !isGpsEnabled
        ) {
            return null
        }

        return suspendCancellableCoroutine { continuation: CancellableContinuation<Location?> ->
            locationClient.lastLocation.apply {
                // Cached the latest device’s location.
                if (isComplete) {
                    if (isSuccessful) {
                        continuation.resume(
                            value = result,
                            onCancellation = null
                        )
                    } else {
                        continuation.resume(
                            value = null,
                            onCancellation = null
                        )
                    }
                    return@suspendCancellableCoroutine
                }

                addOnSuccessListener { location ->
                    LogUtil.d(TAG, "[Access Success] Data: $location")
                    continuation.resume(
                        value = location,
                        onCancellation = null
                    )
                }

                addOnFailureListener {
                    LogUtil.e(TAG, "[Access Failed] Message: ${it.message}")
                    continuation.resume(
                        value = null,
                        onCancellation = null
                    )
                }

                addOnCanceledListener {
                    LogUtil.d(TAG, "[Access Canceled]")
                    continuation.cancel()
                }
            }
        }
    }
}