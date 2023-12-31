package io.module.map.tomtom

import android.content.Context
import android.net.Uri
import com.tomtom.quantity.Distance
import com.tomtom.sdk.location.GeoLocation
import com.tomtom.sdk.location.OnLocationUpdateListener
import com.tomtom.sdk.location.android.AndroidLocationProvider
import com.tomtom.sdk.location.android.AndroidLocationProviderConfig
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.camera.CameraOptions
import com.tomtom.sdk.map.display.common.screen.Padding
import com.tomtom.sdk.map.display.location.LocationMarkerOptions
import com.tomtom.sdk.map.display.map.OnlineCachePolicy
import com.tomtom.sdk.map.display.style.StyleDescriptor
import com.tomtom.sdk.map.display.style.StyleMode
import io.common.RelicResCenter
import io.module.map.R
import kotlin.time.Duration.Companion.milliseconds

object TomTomMapConfig {

    val mapDevKey: String = RelicResCenter.getString(R.string.tomtom_dev_key)

    object MapOptionsConfig {

        /**
         * [TomTomMap • MapOptions](https://developer.tomtom.com/assets/downloads/tomtom-sdks/android/api-reference/0.33.1/maps/display-common/com.tomtom.sdk.map.display/-map-options/index.html)
         * */
        fun defaultConfig(): MapOptions {
            return MapOptions(
                mapKey = mapDevKey,
                cameraOptions = CameraOptions(),
                padding = Padding(),
                mapStyle = StyleDescriptor(Uri.EMPTY),
                styleMode = StyleMode.DARK,
                onlineCachePolicy = OnlineCachePolicy.Default,
                renderToTexture = true
            )
        }
    }

    object MapLocationProviderConfig {

        /**
         * [TomTomMap • Location Provider](https://developer.tomtom.com/android/maps/documentation/guides/location/built-in-location-provider)
         * */
        fun defaultLocationProvider(
            context: Context,
            config: AndroidLocationProviderConfig = defaultLocationProviderConfig(),
            onLocationUpdate: (location: GeoLocation) -> Unit
        ): AndroidLocationProvider {
            return AndroidLocationProvider(
                context = context,
                config = config
            ).also { provider: AndroidLocationProvider ->
                registerLocationUpdateListener(
                    provider = provider,
                    onLocationUpdate = onLocationUpdate
                )
            }.apply {
                enable()
            }
        }

        private fun registerLocationUpdateListener(
            provider: AndroidLocationProvider,
            onLocationUpdate: (location: GeoLocation) -> Unit
        ): OnLocationUpdateListener {
            val locationUpdateListener = OnLocationUpdateListener { location: GeoLocation ->
                onLocationUpdate.invoke(location)
            }

            provider.addOnLocationUpdateListener(locationUpdateListener)

            return locationUpdateListener
        }

        private fun unregisterLocationUpdateListener(
            provider: AndroidLocationProvider,
            listener: OnLocationUpdateListener
        ) {
            provider.removeOnLocationUpdateListener(listener)
        }

        private fun defaultLocationProviderConfig(): AndroidLocationProviderConfig {
            return AndroidLocationProviderConfig(
                minTimeInterval = 250L.milliseconds,
                minDistance = Distance.meters(20.0)
            )
        }
    }

    object LocationMarkerConfig {

        fun defaultLocationMarkerOptions(): LocationMarkerOptions {
            return LocationMarkerOptions(
                type = LocationMarkerOptions.Type.Pointer
            )
        }
    }
}