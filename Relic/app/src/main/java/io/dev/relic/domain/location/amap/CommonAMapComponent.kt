package io.dev.relic.domain.location.amap

import android.annotation.SuppressLint
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.amap.api.maps.AMapOptions
import com.amap.api.maps.MapView
import io.dev.relic.global.utils.LogUtil

private const val TAG = "CommonAMapComponent"

@Composable
fun CommonAMapComponent(
    context: Context,
    lifecycle: Lifecycle,
    mainSavedInstanceState: Bundle?
) {

    val aMapView: MapView = remember {
        val aMapOptions: AMapOptions = AMapConfig.OptionsConfig.defaultConfig()
        MapView(context, aMapOptions)
    }

    val previousAMapState: MutableState<Lifecycle.Event> = remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    AndroidView(
        factory = { aMapView },
        modifier = Modifier.fillMaxSize(),
        onRelease = { mapView: MapView ->
            mapView.removeAllViews()
            mapView.onDestroy()
        }
    )

    bindAMapLifecycle(
        context = context,
        lifecycle = lifecycle,
        mainSavedInstanceState = mainSavedInstanceState,
        previousAMapState = previousAMapState,
        aMapView = aMapView,
        onResume = {},
        onPause = {}
    )
}


@SuppressLint("ComposableNaming")
@Composable
private fun bindAMapLifecycle(
    context: Context,
    lifecycle: Lifecycle,
    mainSavedInstanceState: Bundle?,
    previousAMapState: MutableState<Lifecycle.Event>,
    aMapView: MapView,
    onResume: () -> Unit,
    onPause: () -> Unit
) {
    val savedInstancedState: Bundle = mainSavedInstanceState ?: Bundle()
    LogUtil.debug(TAG, "[Combine lifecycle] savedInstancedState: $savedInstancedState")

    DisposableEffect(
        key1 = context,
        key2 = lifecycle,
        key3 = aMapView
    ) {
        val aMapComponentCallback: ComponentCallbacks = aMapView.componentCallback()
        val aMapLifecycleObserver: LifecycleObserver = aMapView.lifecycleObserver(
            previousAMapState = previousAMapState,
            saveStateBundle = savedInstancedState,
            onResume = onResume,
            onPause = onPause
        )

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
    previousAMapState: MutableState<Lifecycle.Event>,
    saveStateBundle: Bundle,
    onResume: () -> Unit,
    onPause: () -> Unit
): LifecycleObserver {
    return LifecycleEventObserver { _: LifecycleOwner, event: Lifecycle.Event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                LogUtil.debug(TAG, "[LifeCycleObserver] ON_CREATE")
                if (previousAMapState.value != Lifecycle.Event.ON_STOP) {
                    this.onCreate(saveStateBundle)
                }
            }

            Lifecycle.Event.ON_RESUME -> {
                LogUtil.debug(TAG, "[LifeCycleObserver] ON_RESUME")
                this.onResume()
                onResume.invoke()
            }

            Lifecycle.Event.ON_PAUSE -> {
                LogUtil.warning(TAG, "[LifeCycleObserver] ON_PAUSE")
                this.onPause()
                onPause.invoke()
            }

            else -> {}
        }
        previousAMapState.value = event
    }
}

private fun MapView.componentCallback(): ComponentCallbacks {
    return object : ComponentCallbacks {

        /**
         * Called by the system when the device configuration changes while your
         * component is running.  Note that, unlike activities, other components
         * are never restarted when a configuration changes: they must always deal
         * with the results of the change, such as by re-retrieving resources.
         *
         *
         * At the time that this function has been called, your Resources
         * object will have been updated to return resource values matching the
         * new configuration.
         *
         *
         * For more information, read [Handling Runtime Changes]({@docRoot}guide/topics/resources/runtime-changes.html).
         *
         * @param newConfig The new device configuration.
         */
        override fun onConfigurationChanged(newConfig: Configuration) {
            //
        }

        /**
         * This is called when the overall system is running low on memory, and
         * actively running processes should trim their memory usage.  While
         * the exact point at which this will be called is not defined, generally
         * it will happen when all background process have been killed.
         * That is, before reaching the point of killing processes hosting
         * service and foreground UI that we would like to avoid killing.
         *
         *
         * You should implement this method to release
         * any caches or other unnecessary resources you may be holding on to.
         * The system will perform a garbage collection for you after returning from this method.
         *
         * Preferably, you should implement [ComponentCallbacks2.onTrimMemory] from
         * [ComponentCallbacks2] to incrementally unload your resources based on various
         * levels of memory demands.  That API is available for API level 14 and higher, so you should
         * only use this [.onLowMemory] method as a fallback for older versions, which can be
         * treated the same as [ComponentCallbacks2.onTrimMemory] with the [ ][ComponentCallbacks2.TRIM_MEMORY_COMPLETE] level.
         */
        override fun onLowMemory() {
            this@componentCallback.onLowMemory()
        }
    }
}