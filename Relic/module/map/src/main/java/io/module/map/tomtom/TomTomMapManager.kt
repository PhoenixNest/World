package io.module.map.tomtom

import android.net.Uri
import com.tomtom.sdk.location.GeoLocation
import com.tomtom.sdk.location.LocationProvider
import com.tomtom.sdk.location.OnLocationUpdateListener
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.camera.CameraOptions
import com.tomtom.sdk.map.display.common.screen.Padding
import com.tomtom.sdk.map.display.location.LocationMarkerOptions
import com.tomtom.sdk.map.display.map.OnlineCachePolicy
import com.tomtom.sdk.map.display.style.StyleDescriptor
import com.tomtom.sdk.map.display.style.StyleMode
import io.common.RelicResCenter
import io.common.util.LogUtil
import io.module.map.R

object TomTomMapManager {

    private const val TAG = "TomTomMapConfig"

    private val mapDevKey = RelicResCenter.getString(R.string.tomtom_dev_key)

    /**
     * [TomTomMap • MapOptions](https://developer.tomtom.com/assets/downloads/tomtom-sdks/android/api-reference/0.33.1/maps/display-common/com.tomtom.sdk.map.display/-map-options/index.html)
     * */
    var mapOptions: MapOptions? = null

    /**
     * [TomTomMap • Location Marker](https://developer.tomtom.com/maps/android/guides/map-display/markers)
     * */
    var locationMarkerOptions: LocationMarkerOptions? = null

    /**
     * [TomTomMap • Location Provider](https://developer.tomtom.com/android/maps/documentation/guides/location/built-in-location-provider)
     * */
    var locationProvider: LocationProvider? = null

    /**
     * [TomTomMap • Location Provider • OnLocationUpdateListener](https://developer.tomtom.com/android/maps/documentation/guides/location/built-in-location-provider)
     * */
    private var onLocationUpdateListener: OnLocationUpdateListener? = null

    fun initTomTomMapComponent() {
        initMapOptions()
        initLocationMarkerOptions()
        initLocationProvider()
    }

    val defaultMapOptions = MapOptions(
        mapKey = mapDevKey,
        cameraOptions = CameraOptions(),
        padding = Padding(),
        mapStyle = StyleDescriptor(Uri.EMPTY),
        styleMode = StyleMode.DARK,
        onlineCachePolicy = OnlineCachePolicy.Default,
        renderToTexture = true
    )

    val defaultLocationMarkerOptions = LocationMarkerOptions(
        type = LocationMarkerOptions.Type.Pointer
    )

    private fun initMapOptions(
        newOptions: MapOptions? = null
    ): MapOptions? {
        if (mapOptions != null) {
            LogUtil.w(TAG, "[Map options] Already created.")
            return mapOptions
        }

        LogUtil.d(TAG, "[Map options] Create new options config for map.")
        mapOptions = newOptions ?: defaultMapOptions
        return mapOptions
    }

    private fun initLocationMarkerOptions(
        newOptions: LocationMarkerOptions? = null
    ): LocationMarkerOptions? {
        if (locationMarkerOptions != null) {
            LogUtil.w(TAG, "[Location Marker Options] Already created.")
            return locationMarkerOptions
        }

        LogUtil.d(TAG, "[Location Marker Options] Create new options config for map marker.")
        locationMarkerOptions = newOptions ?: defaultLocationMarkerOptions

        return locationMarkerOptions
    }

    private fun initLocationProvider() {
        if (locationProvider != null) {
            LogUtil.w(TAG, "[Location Provider] Already registered.")
            return
        }

        LogUtil.d(TAG, "[Location Provider] Register the location provider.")
        locationProvider = object : LocationProvider {

            override val lastKnownLocation: GeoLocation? = null

            override fun addOnLocationUpdateListener(listener: OnLocationUpdateListener) {
                LogUtil.d(TAG, "[Location Provider] addOnLocationUpdateListener")
            }

            override fun close() {
                LogUtil.e(TAG, "[Location Provider] close")
            }

            override fun disable() {
                LogUtil.w(TAG, "[Location Provider] disable")
                locationProvider?.let {
                    unregisterOnLocationUpdateListener(it)
                }
            }

            override fun enable() {
                LogUtil.d(TAG, "[Location Provider] enable")
                locationProvider?.let {
                    registerOnLocationUpdateListener(it)
                }
            }

            override fun removeOnLocationUpdateListener(listener: OnLocationUpdateListener) {
                LogUtil.w(TAG, "[Location Provider] removeOnLocationUpdateListener")
            }
        }
    }

    private fun registerOnLocationUpdateListener(provider: LocationProvider) {
        LogUtil.d(TAG, "[Location Update Listener] Register the location provider.")
        onLocationUpdateListener?.let {
            provider.addOnLocationUpdateListener(it)
        }
    }

    private fun unregisterOnLocationUpdateListener(provider: LocationProvider) {
        LogUtil.d(TAG, "[Location Update Listener] Register the location provider.")
        onLocationUpdateListener?.let {
            provider.removeOnLocationUpdateListener(it)
        }
    }

}