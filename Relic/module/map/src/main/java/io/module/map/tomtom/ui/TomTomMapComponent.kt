package io.module.map.tomtom.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import com.tomtom.sdk.location.LocationProvider
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.location.LocationMarkerOptions
import com.tomtom.sdk.map.display.ui.MapView
import io.common.util.LogUtil
import io.module.map.tomtom.TomTomMapConfig
import io.module.map.tomtom.kit.componentCallback
import io.module.map.tomtom.kit.lifecycleObserver

private const val TAG = "TomTomMapComponent"

/**
 * The Compose component of [TomTomMap](https://developer.tomtom.com/android/maps/documentation/overview/introduction)
 *
 * @param mapOptions
 * @param modifier
 * */
@Composable
fun TomTomMapComponent(
    modifier: Modifier = Modifier,
    mapOptions: MapOptions = TomTomMapConfig.mapOptions,
    markerOptions: LocationMarkerOptions = TomTomMapConfig.locationMarkerOptions,
    locationProvider: LocationProvider? = TomTomMapConfig.getLocationProvider()
) {

    if (LocalInspectionMode.current) {
        LogUtil.e(TAG, "[Compose] Inspection Mode, skip map rending")
        Box(modifier = modifier)
        return
    }

    val context = LocalContext.current
    val mapView = remember {
        MapView(
            context = context,
            mapOptions = mapOptions
        )
    }

    AndroidView(
        factory = { mapView },
        modifier = modifier.fillMaxSize()
    )

    TomTomMapLifecycleBinder(mapView)
    TomTomMapLocationBinder(
        mapView = mapView,
        markerOptions = markerOptions,
        locationProvider = locationProvider
    )
}

/* ======================== Util ======================== */

@Composable
private fun TomTomMapLifecycleBinder(mapView: MapView) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val previousMapState = remember {
        mutableStateOf(ON_CREATE)
    }

    DisposableEffect(
        key1 = context,
        key2 = lifecycle,
        key3 = mapView
    ) {
        val mapComponentCallback = mapView.componentCallback()
        val mapLifecycleObserver = mapView.lifecycleObserver(previousMapState)

        LogUtil.d(TAG, "[Lifecycle] Binds the lifecycle with mapView")
        lifecycle.addObserver(mapLifecycleObserver)
        context.registerComponentCallbacks(mapComponentCallback)

        onDispose {
            lifecycle.removeObserver(mapLifecycleObserver)
            context.unregisterComponentCallbacks(mapComponentCallback)
        }
    }

    DisposableEffect(mapView) {
        onDispose {
            // Avoid OOM
            mapView.onDestroy()
            mapView.removeAllViews()
        }
    }
}

@Composable
private fun TomTomMapLocationBinder(
    mapView: MapView,
    markerOptions: LocationMarkerOptions,
    locationProvider: LocationProvider?
) {
    LaunchedEffect(Unit) {
        mapView.getMapAsync { tomTomMap ->
            tomTomMap.setLocationProvider(locationProvider)
            tomTomMap.enableLocationMarker(markerOptions)
        }
    }
}