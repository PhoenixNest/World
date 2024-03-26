package io.module.map.tomtom.ui

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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import com.tomtom.sdk.map.display.ui.MapView
import io.module.map.tomtom.TomTomMapManager.defaultLocationMarkerOptions
import io.module.map.tomtom.TomTomMapManager.defaultLocationProvider
import io.module.map.tomtom.TomTomMapManager.defaultMapOptions
import io.module.map.tomtom.TomTomMapManager.locationMarkerOptions
import io.module.map.tomtom.TomTomMapManager.locationProvider
import io.module.map.tomtom.TomTomMapManager.mapOptions
import io.module.map.tomtom.kit.componentCallback
import io.module.map.tomtom.kit.disposingComposition
import io.module.map.tomtom.kit.lifecycleObserver
import io.module.map.tomtom.kit.newComposition
import io.module.map.tomtom.utils.MapLogUtil

private const val TAG = "TomTomMapComponent"

/**
 * The Compose component of [TomTomMap](https://developer.tomtom.com/android/maps/documentation/overview/introduction)
 *
 * @param modifier
 * */
@Composable
fun TomTomMapComponent(modifier: Modifier = Modifier) {

    if (LocalInspectionMode.current) {
        MapLogUtil.e(TAG, "[Render] Compose inspection Mode, skip rending")
        Box(modifier = modifier)
        return
    }

    val context = LocalContext.current
    val mapView = remember {
        MapView(
            context = context,
            mapOptions = mapOptions ?: defaultMapOptions
        )
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
                    val provider = locationProvider ?: defaultLocationProvider
                    val options = locationMarkerOptions ?: defaultLocationMarkerOptions
                    it.setLocationProvider(provider)
                    it.enableLocationMarker(options)
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

        MapLogUtil.d(TAG, "[Lifecycle] Binds the lifecycle with mapView")
        lifecycle.addObserver(mapLifecycleObserver)
        context.registerComponentCallbacks(mapComponentCallback)

        onDispose {
            MapLogUtil.w(TAG, "[Lifecycle] Compose onDispose, free memory")
            lifecycle.removeObserver(mapLifecycleObserver)
            context.unregisterComponentCallbacks(mapComponentCallback)
        }
    }

    DisposableEffect(mapView) {
        onDispose {
            // Avoid OOM
            MapLogUtil.w(TAG, "[Lifecycle] mapView onDispose, free memory")
            mapView.onDestroy()
            mapView.removeAllViews()
        }
    }
}