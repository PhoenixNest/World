package io.dev.relic.domain.location.amap

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
fun RelicAMapComponent(
    modifier: Modifier = Modifier,
    aMapOptionsFactory: () -> AMapOptions = { AMapOptions() },
    locationStyleFactory: () -> MyLocationStyle = { MyLocationStyle() }
) {
    if (LocalInspectionMode.current) {
        return
    }

    val context: Context = LocalContext.current

    val aMapOptions: AMapOptions = aMapOptionsFactory.invoke()
    val mLocationStyle: MyLocationStyle = locationStyleFactory.invoke()
    val aMapView: MapView = remember { MapView(context, aMapOptions) }

    AndroidView(
        factory = {
            aMapView.apply {
                map.apply {
                    isMyLocationEnabled = true
                    myLocationStyle = mLocationStyle
                }
            }
        },
        modifier = modifier.fillMaxSize(),
        onRelease = { mapView: MapView ->
            // Avoid OOM
            mapView.removeAllViews()
            mapView.onDestroy()
        }
    )

    AMapLifecycleBinder(aMapView = aMapView)
}

@Composable
private fun AMapLifecycleBinder(aMapView: MapView) {
    val context: Context = LocalContext.current
    val lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle
    val previousAMapState: MutableState<Lifecycle.Event> = remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    DisposableEffect(
        key1 = context,
        key2 = lifecycle,
        key3 = aMapView
    ) {
        val aMapComponentCallback: ComponentCallbacks = aMapView.componentCallback()
        val aMapLifecycleObserver: LifecycleObserver = aMapView.lifecycleObserver(previousAMapState)

        context.registerComponentCallbacks(aMapComponentCallback)
        lifecycle.addObserver(aMapLifecycleObserver)

        onDispose {
            lifecycle.removeObserver(aMapLifecycleObserver)
            context.unregisterComponentCallbacks(aMapComponentCallback)
        }
    }

    DisposableEffect(key1 = aMapView) {
        onDispose {
            // Avoid OOM
            aMapView.onDestroy()
            aMapView.removeAllViews()
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