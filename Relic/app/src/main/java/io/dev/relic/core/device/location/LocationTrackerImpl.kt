package io.dev.relic.core.device.location

import android.Manifest
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.qualifiers.ApplicationContext
import io.dev.relic.core.device.permission.RelicPermissionDetector.Native.checkPermission
import io.dev.relic.domain.location.ILocationTracker
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class LocationTrackerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val locationClient: FusedLocationProviderClient
) : ILocationTracker {

    override suspend fun getCurrentLocation(): Location? {

        val hasAccessFindLocationPermission: Boolean = checkPermission(
            context = context,
            requestPermission = Manifest.permission.ACCESS_FINE_LOCATION
        )

        val hasAccessCoarseLocationPermission: Boolean = checkPermission(
            context = context,
            requestPermission = Manifest.permission.ACCESS_COARSE_LOCATION
        )

        val locationManager: LocationManager = context.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

        val isGpsEnabled: Boolean = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!hasAccessFindLocationPermission
            || !hasAccessCoarseLocationPermission
            || !isGpsEnabled
        ) {
            return null
        }

        return suspendCancellableCoroutine { continuation: CancellableContinuation<Location?> ->
            locationClient.lastLocation.apply {
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
                addOnSuccessListener { location: Location ->
                    continuation.resume(
                        value = location,
                        onCancellation = null
                    )
                }
                addOnFailureListener {
                    continuation.resume(
                        value = null,
                        onCancellation = null
                    )
                }
                addOnCanceledListener {
                    continuation.cancel()
                }
            }
        }
    }
}