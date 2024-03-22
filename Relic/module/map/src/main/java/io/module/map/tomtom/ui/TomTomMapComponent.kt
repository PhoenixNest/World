package io.module.map.tomtom.ui

import android.view.ViewGroup
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
    locationMarkerOptions: LocationMarkerOptions = TomTomMapConfig.locationMarkerOptions
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
        ).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    AndroidView(
        factory = { mapView },
        modifier = modifier.fillMaxSize()
    )

    TomTomMapLifecycleBinder(mapView)
    TomTomMapLocationProviderBinder(mapView)
    TomTomMapLocationMarkerBinder(
        mapView = mapView,
        markerOptions = locationMarkerOptions
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
private fun TomTomMapLocationProviderBinder(mapView: MapView) {
    LaunchedEffect(Unit) {
        mapView.getMapAsync {
            TomTomMapConfig.registerLocationProvider(it)
        }
    }
}

@Composable
private fun TomTomMapLocationMarkerBinder(
    mapView: MapView,
    markerOptions: LocationMarkerOptions
) {
    LaunchedEffect(Unit) {
        mapView.getMapAsync {
            it.enableLocationMarker(markerOptions)
        }
    }
}