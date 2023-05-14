package io.dev.relic.core.module.location

import android.Manifest
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.qualifiers.ApplicationContext
import io.dev.relic.core.module.permission.RelicPermissionDetector
import io.dev.relic.domin.location.ILocationTracker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class RelicLocationTracker @Inject constructor(
    @ApplicationContext private val context: Context,
    private val locationClient: FusedLocationProviderClient
) : ILocationTracker {

    override suspend fun getCurrentLocation(): Location? {

        val hasAccessFindLocationPermission: Boolean = RelicPermissionDetector.checkPermission(
            context = context,
            requestPermission = Manifest.permission.ACCESS_FINE_LOCATION
        )

        val hasAccessCoarseLocationPermission: Boolean = RelicPermissionDetector.checkPermission(
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

        return suspendCancellableCoroutine { continuation ->
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
                addOnSuccessListener { location ->
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