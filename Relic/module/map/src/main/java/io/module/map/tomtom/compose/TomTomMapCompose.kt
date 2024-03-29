package io.module.map.tomtom.compose

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
import com.tomtom.sdk.location.android.AndroidLocationProvider
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.location.LocationMarkerOptions
import com.tomtom.sdk.map.display.ui.MapView
import io.module.map.R
import io.module.map.tomtom.utils.MapLogUtil

private const val TAG = "TomTomMapComponent"

/**
 * The Compose component of [TomTomMap](https://developer.tomtom.com/android/maps/documentation/overview/introduction)
 *
 * @param modifier
 * @param mapLocationProvider
 * @param mapLocationMarkerOptions
 * */
@Composable
fun TomTomMapComponent(
    modifier: Modifier = Modifier,
    mapLocationProvider: AndroidLocationProvider,
    mapLocationMarkerOptions: LocationMarkerOptions
) {

    if (LocalInspectionMode.current) {
        MapLogUtil.e(TAG, "[Render] Compose inspection Mode, skip rending")
        Box(modifier = modifier)
        return
    }

    val context = LocalContext.current

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
                    it.setLocationProvider(mapLocationProvider)
                    it.enableLocationMarker(mapLocationMarkerOptions)
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