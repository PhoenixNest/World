package io.dev.relic.domain.map.tomtom

import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.lifecycle.Lifecycle.Event.ON_PAUSE
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.ui.MapView

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
        modifier = modifier.fillMaxSize(),
        onRelease = {
            // Avoid OOM
            it.onDestroy()
            it.removeAllViews()
        }
    )

    TomTomMapLifecycleBinder(mapView)
}

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

    DisposableEffect(key1 = mapView) {
        onDispose {
            // Avoid OOM
            mapView.onDestroy()
            mapView.removeAllViews()
        }
    }
}

/* ======================== Util ======================== */

private fun MapView.lifecycleObserver(
    previousAMapState: MutableState<Lifecycle.Event>
): LifecycleObserver {
    return LifecycleEventObserver { _: LifecycleOwner, event: Lifecycle.Event ->
        when (event) {
            ON_CREATE -> {
                if (previousAMapState.value != ON_STOP) {
                    this.onCreate(Bundle())
                }
            }

            ON_START -> {
                this.onStart()
            }

            ON_RESUME -> {
                this.onResume()
            }

            ON_PAUSE -> {
                this.onPause()
            }

            ON_STOP -> {
                this.onStop()
            }

            else -> {}
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