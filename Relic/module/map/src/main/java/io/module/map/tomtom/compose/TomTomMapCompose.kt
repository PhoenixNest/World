package io.module.map.tomtom.compose

import android.Manifest
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import com.tomtom.sdk.location.LocationProvider
import com.tomtom.sdk.location.OnLocationUpdateListener
import com.tomtom.sdk.location.android.AndroidLocationProvider
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.location.LocationMarkerOptions
import com.tomtom.sdk.map.display.ui.MapView
import io.module.map.R
import io.module.map.permission.MapPermissionCenter
import io.module.map.tomtom.TomTomMapCustomizer.DEFAULT_LOCATION_MARKER_TYPE
import io.module.map.tomtom.compose.TomTomMapToolKit.componentCallback
import io.module.map.tomtom.compose.TomTomMapToolKit.disposingComposition
import io.module.map.tomtom.compose.TomTomMapToolKit.lifecycleObserver
import io.module.map.tomtom.compose.TomTomMapToolKit.newComposition
import io.module.map.utils.LogUtil

private const val TAG = "TomTomMapComponent"

/**
 * The Compose component of [TomTomMap](https://developer.tomtom.com/android/maps/documentation/overview/introduction)
 *
 * @param context
 * @param modifier
 * */
@Composable
fun TomTomMapComponent(
    context: Context,
    modifier: Modifier = Modifier
) {

    if (LocalInspectionMode.current) {
        LogUtil.e(TAG, "[Render] Compose inspection Mode, skip rending")
        Box(modifier = modifier)
        return
    }

    val devKey = stringResource(id = R.string.tomtom_dev_key)
    val mapView = remember {
        MapView(context, MapOptions(devKey))
    }

    AndroidView(
        factory = { mapView },
        modifier = modifier.fillMaxSize()
    )

    TomTomMapLifecycleBinder(mapView)

    val parentComposition = rememberCompositionContext()
    LaunchedEffect(Unit) {
        disposingComposition {
            mapView.newComposition(parentComposition) {
                mapView.getMapAsync {
                    // Enable location marker to indicate the current user location.
                    val locationMarkerOptions = LocationMarkerOptions(DEFAULT_LOCATION_MARKER_TYPE)
                    it.enableLocationMarker(locationMarkerOptions)

                    // Binds the location provider to TomTomMap to fetch the latest user location.
                    val provider = TomTomMapLocationProviderFactory(context)
                    it.setLocationProvider(provider)

                    // Binds the update listener to gets the callback when fetch the latest location info.
                    val locationUpdateListener = OnLocationUpdateListener {
                        // Use the latest location info
                    }
                    provider.addOnLocationUpdateListener(locationUpdateListener)
                }
            }
        }
    }
}

/* ======================== Util ======================== */

@Composable
private fun TomTomMapLifecycleBinder(mapView: MapView) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val previousMapState = remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    DisposableEffect(
        keys = arrayOf(
            context,
            lifecycle,
            mapView
        )
    ) {
        val mapComponentCallback = mapView.componentCallback()
        val mapLifecycleObserver = mapView.lifecycleObserver(previousMapState)

        LogUtil.d(TAG, "[Lifecycle] Binds the lifecycle with mapView")
        lifecycle.addObserver(mapLifecycleObserver)
        context.registerComponentCallbacks(mapComponentCallback)

        onDispose {
            LogUtil.w(TAG, "[Lifecycle] Compose onDispose, free memory")
            lifecycle.removeObserver(mapLifecycleObserver)
            context.unregisterComponentCallbacks(mapComponentCallback)
        }
    }

    DisposableEffect(mapView) {
        onDispose {
            // Avoid OOM
            LogUtil.w(TAG, "[Lifecycle] mapView onDispose, free memory")
            mapView.onDestroy()
            mapView.removeAllViews()
        }
    }
}

@Suppress("FunctionName")
private fun TomTomMapLocationProviderFactory(context: Context): LocationProvider {
    val locationProvider = AndroidLocationProvider(context)

    val isAccessCoarseLocation = MapPermissionCenter.checkPermission(
        context = context,
        requestPermission = Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val isAccessFineLocation = MapPermissionCenter.checkPermission(
        context = context,
        requestPermission = Manifest.permission.ACCESS_FINE_LOCATION
    )
    if (isAccessCoarseLocation && isAccessFineLocation) {
        locationProvider.enable()
    }

    return locationProvider
}