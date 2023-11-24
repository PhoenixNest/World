package io.module.map.tomtom.ui

import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.tomtom.sdk.location.android.AndroidLocationProvider
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.ui.MapView
import io.module.map.tomtom.TomTomMapConfig

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
    modifier: Modifier = Modifier,
    mapOptionsFactory: () -> MapOptions = { MapOptions(TomTomMapConfig.mapDevKey) }
) {

    if (LocalInspectionMode.current) {
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
    // TomTomMapLocationProviderBinder(mapView)
}

/* ======================== Util ======================== */

@Composable
private fun TomTomMapLifecycleBinder(mapView: MapView) {
    val context: Context = LocalContext.current
    val lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle
    val previousAMapState: MutableState<Lifecycle.Event> = remember {
        mutableStateOf(ON_CREATE)
    }

    DisposableEffect(
        key1 = context,
        key2 = lifecycle,
        key3 = mapView
    ) {
        val mapComponentCallback: ComponentCallbacks = mapView.componentCallback()
        val mapLifecycleObserver: LifecycleObserver = mapView.lifecycleObserver(previousAMapState)

        context.registerComponentCallbacks(mapComponentCallback)
        lifecycle.addObserver(mapLifecycleObserver)

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
    val context: Context = LocalContext.current

    SideEffect {
        val locationProvider: AndroidLocationProvider = TomTomMapConfig.MapLocationProviderConfig.defaultLocationProvider(context)
        mapView.getMapAsync { tomTomMap: TomTomMap ->
            tomTomMap.setLocationProvider(locationProvider)
        }
    }
}

private fun MapView.lifecycleObserver(
    previousAMapState: MutableState<Lifecycle.Event>
): LifecycleObserver {
    return LifecycleEventObserver { _: LifecycleOwner, event: Lifecycle.Event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                if (previousAMapState.value != ON_STOP) {
                    this.onCreate(Bundle())
                }
            }

            Lifecycle.Event.ON_START -> this.onStart()
            Lifecycle.Event.ON_RESUME -> this.onResume()
            Lifecycle.Event.ON_PAUSE -> this.onPause()
            Lifecycle.Event.ON_STOP -> this.onStop()
            Lifecycle.Event.ON_DESTROY -> {
                // Handled in onDispose.
            }

            else -> throw IllegalStateException()
        }
        previousAMapState.value = event
    }
}

private fun MapView.componentCallback(): ComponentCallbacks {
    return object : ComponentCallbacks {
        override fun onConfigurationChanged(newConfig: Configuration) {
            //
        }

        override fun onLowMemory() {
            this@componentCallback.onPause()
        }
    }
}