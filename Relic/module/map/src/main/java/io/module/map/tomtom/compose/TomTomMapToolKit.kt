package io.module.map.tomtom.compose

import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.MutableState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.ui.MapView
import io.module.map.utils.LogUtil
import kotlinx.coroutines.awaitCancellation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object TomTomMapToolKit {

    private const val TAG = "TomTomMapToolKit"

    suspend inline fun disposingComposition(factory: () -> Composition) {
        val composition = factory()
        try {
            awaitCancellation()
        } finally {
            composition.dispose()
        }
    }

    suspend inline fun MapView.awaitMap(): TomTomMap {
        return suspendCoroutine { continuation ->
            getMapAsync {
                continuation.resume(it)
            }
        }
    }

    suspend inline fun MapView.newComposition(
        parent: CompositionContext,
        noinline content: @Composable () -> Unit
    ): Composition {
        val map = awaitMap()
        return Composition(
            applier = TomTomMapApplier(
                map = map,
                mapView = this
            ),
            parent = parent
        ).apply {
            setContent(content)
        }
    }

    fun MapView.lifecycleObserver(
        previousAMapState: MutableState<Lifecycle.Event>,
        onCreate: () -> Unit = {},
        onStart: () -> Unit = {},
        onResume: () -> Unit = {},
        onPause: () -> Unit = {},
        onStop: () -> Unit = {}
    ): LifecycleObserver {
        return LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    LogUtil.d(TAG, "[Map Lifecycle] onCreate")
                    if (previousAMapState.value != Lifecycle.Event.ON_STOP) {
                        this.onCreate(Bundle())
                        onCreate.invoke()
                    }
                }

                Lifecycle.Event.ON_START -> {
                    LogUtil.d(TAG, "[Map Lifecycle] onStart")
                    this.onStart()
                    onStart.invoke()
                }

                Lifecycle.Event.ON_RESUME -> {
                    LogUtil.d(TAG, "[Map Lifecycle] onResume")
                    this.onResume()
                    onResume.invoke()
                }

                Lifecycle.Event.ON_PAUSE -> {
                    LogUtil.w(TAG, "[Map Lifecycle] onPause")
                    this.onPause()
                    onPause.invoke()
                }

                Lifecycle.Event.ON_STOP -> {
                    LogUtil.w(TAG, "[Map Lifecycle] onStop")
                    this.onStop()
                    onStop.invoke()
                }

                Lifecycle.Event.ON_DESTROY -> {
                    LogUtil.e(TAG, "[Map Lifecycle] onDestroy")
                    // Handled in onDispose.
                }

                else -> throw IllegalStateException()
            }
            previousAMapState.value = event
        }
    }

    fun MapView.componentCallback(): ComponentCallbacks {
        return object : ComponentCallbacks {
            override fun onConfigurationChanged(newConfig: Configuration) {
                //
            }

            override fun onLowMemory() {
                this@componentCallback.onPause()
            }
        }
    }
}