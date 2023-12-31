package io.module.map.tomtom.ui

import android.content.ComponentCallbacks
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.LifecycleObserver
import com.tomtom.sdk.location.GeoLocation
import com.tomtom.sdk.location.LocationProvider
import com.tomtom.sdk.location.android.AndroidLocationProvider
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.ui.MapView
import io.module.map.tomtom.TomTomMapConfig
import io.module.map.tomtom.TomTomMapConfig.LocationMarkerConfig.defaultLocationMarkerOptions
import io.module.map.tomtom.TomTomMapConfig.MapLocationProviderConfig.defaultLocationProvider
import io.module.map.tomtom.kit.componentCallback
import io.module.map.tomtom.kit.lifecycleObserver

/**
 * The Compose component of [TomTomMap](https://developer.tomtom.com/android/maps/documentation/overview/introduction)
 *
 * @param modifier                 The container modifier of TomTomMapView
 * @param mapOptionsFactory        Builder of the MapOptions
 *
 * @see MapView
 * @see MapOptions
 * */
@Composable
fun TomTomMapComponent(
    onLocationUpdate: (location: GeoLocation) -> Unit,
    modifier: Modifier = Modifier,
    mapOptionsFactory: () -> MapOptions = { MapOptions(TomTomMapConfig.mapDevKey) },
    contentDescription: String? = null,
    locationProvider: LocationProvider? = null,
    contentPadding: PaddingValues = PaddingValues(),
    content: (@Composable () -> Unit)? = null
) {

    if (LocalInspectionMode.current) {
        Box(modifier = modifier)
        return
    }

    val context: Context = LocalContext.current

    val mapOptions: MapOptions = mapOptionsFactory.invoke()
    val mapView: MapView = remember { MapView(context, mapOptions) }

    AndroidView(
        factory = { mapView },
        modifier = modifier.fillMaxSize()
    )

    TomTomMapLifecycleBinder(mapView)
    TomTomMapLocationProviderBinder(
        mapView = mapView,
        onLocationUpdate = onLocationUpdate
    )
    TomTomMapLocationMarkerBinder(mapView)
}

/* ======================== Util ======================== */

@Composable
private fun TomTomMapLifecycleBinder(mapView: MapView) {
    val context: Context = LocalContext.current
    val lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle
    val previousMapState: MutableState<Lifecycle.Event> = remember {
        mutableStateOf(ON_CREATE)
    }

    DisposableEffect(
        key1 = context,
        key2 = lifecycle,
        key3 = mapView
    ) {
        val mapComponentCallback: ComponentCallbacks = mapView.componentCallback()
        val mapLifecycleObserver: LifecycleObserver = mapView.lifecycleObserver(previousMapState)

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
private fun TomTomMapLocationProviderBinder(
    mapView: MapView,
    onLocationUpdate: (location: GeoLocation) -> Unit
) {
    val context: Context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        val locationProvider: AndroidLocationProvider = defaultLocationProvider(
            context = context,
            onLocationUpdate = onLocationUpdate
        )

        mapView.getMapAsync { tomTomMap: TomTomMap ->
            tomTomMap.setLocationProvider(locationProvider)
        }
    }
}

@Composable
private fun TomTomMapLocationMarkerBinder(mapView: MapView) {
    LaunchedEffect(key1 = Unit) {
        mapView.getMapAsync { tomTomMap: TomTomMap ->
            tomTomMap.enableLocationMarker(defaultLocationMarkerOptions())
        }
    }
}