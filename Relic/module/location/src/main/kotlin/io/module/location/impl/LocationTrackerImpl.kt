package io.module.location.impl

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.qualifiers.ApplicationContext
import io.module.location.ILocationTracker
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
            Log.d(TAG, "[Permission Status] [Find Location] isGranted: $it")
        }

        val hasAccessCoarseLocationPermission = checkPermission(
            context = context,
            requestPermission = Manifest.permission.ACCESS_COARSE_LOCATION
        ).also {
            Log.d(TAG, "[Permission Status] [Access Coarse Location] isGranted: $it")
        }

        val locationManager = context.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

        val isEnableNetworkProvider =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        val isEnableGPSProvider =
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isGpsEnabled: Boolean = (isEnableNetworkProvider || isEnableGPSProvider)
            .also {
                Log.d(TAG, "[GPS Status] isEnabled: $it")
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
                    Log.d(TAG, "[Access Success] Data: $location")
                    continuation.resume(
                        value = location,
                        onCancellation = null
                    )
                }

                addOnFailureListener {
                    Log.e(TAG, "[Access Failed] Message: ${it.message}")
                    continuation.resume(
                        value = null,
                        onCancellation = null
                    )
                }

                addOnCanceledListener {
                    Log.d(TAG, "[Access Canceled]")
                    continuation.cancel()
                }
            }
        }
    }

    /**
     * Check is the request permission is already granted.
     *
     * @param context
     * @param requestPermission
     * */
    private fun checkPermission(
        context: Context,
        requestPermission: String
    ): Boolean {
        return ContextCompat.checkSelfPermission(
            /* context = */ context,
            /* permission = */ requestPermission
        ) == PackageManager.PERMISSION_GRANTED
    }
}