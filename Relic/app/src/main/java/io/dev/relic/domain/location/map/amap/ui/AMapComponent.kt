package io.dev.relic.domain.location.map.amap.ui

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
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.amap.api.maps.AMapOptions
import com.amap.api.maps.MapView
import com.amap.api.maps.model.MyLocationStyle
import io.dev.relic.domain.location.map.amap.AMapConfig

/**
 * The Compose component of [Ali-Map](https://lbs.amap.com/api/android-sdk/summary/).
 *
 * @param modifier                  The container modifier of AMapView
 * @param aMapOptionsFactory        Builder of the AMapOptions
 * @param locationStyleFactory      Builder of the MyLocationStyle
 *
 * @see MapView
 * @see AMapOptions
 * @see MyLocationStyle
 * */
@Composable
fun AMapComponent(
    modifier: Modifier = Modifier,
    aMapOptionsFactory: () -> AMapOptions = { AMapConfig.OptionsConfig.defaultConfig() },
    locationStyleFactory: () -> MyLocationStyle = { AMapConfig.MapStyle.defaultStyle() }
) {
    if (LocalInspectionMode.current) {
        return
    }

    val context: Context = LocalContext.current

    val aMapOptions: AMapOptions = aMapOptionsFactory.invoke()
    val mLocationStyle: MyLocationStyle = locationStyleFactory.invoke()
    val mapView: MapView = remember { MapView(context, aMapOptions) }

    AndroidView(
        factory = {
            mapView.apply {
                map.apply {
                    isMyLocationEnabled = true
                    myLocationStyle = mLocationStyle
                }
            }
        },
        modifier = modifier.fillMaxSize()
    )

    AMapLifecycleBinder(mapView = mapView)
}

@Composable
private fun AMapLifecycleBinder(mapView: MapView) {
    val context: Context = LocalContext.current
    val lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle
    val previousAMapState: MutableState<Lifecycle.Event> = remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
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
            Lifecycle.Event.ON_CREATE -> {
                if (previousAMapState.value != Lifecycle.Event.ON_STOP) {
                    this.onCreate(Bundle())
                }
            }

            Lifecycle.Event.ON_RESUME -> {
                this.onResume()
            }

            Lifecycle.Event.ON_PAUSE -> {
                this.onPause()
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
            this@componentCallback.onLowMemory()
        }
    }
}