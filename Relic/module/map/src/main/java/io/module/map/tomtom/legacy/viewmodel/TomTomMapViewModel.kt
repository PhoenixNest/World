package io.module.map.tomtom.legacy.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.tomtom.sdk.location.GeoLocation
import com.tomtom.sdk.location.LocationProvider
import com.tomtom.sdk.location.OnLocationUpdateListener
import com.tomtom.sdk.location.android.AndroidLocationProvider
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.location.LocationMarkerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import io.module.map.utils.MapLogUtil
import javax.inject.Inject

@HiltViewModel
class TomTomMapViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    /**
     * [TomTomMap • Location Provider](https://developer.tomtom.com/android/maps/documentation/guides/location/built-in-location-provider)
     * */
    lateinit var mapLocationProvider: LocationProvider

    /**
     * [TomTomMap • MapOptions](https://developer.tomtom.com/assets/downloads/tomtom-sdks/android/api-reference/0.33.1/maps/display-common/com.tomtom.sdk.map.display/-map-options/index.html)
     * */
    lateinit var mapOptions: MapOptions

    /**
     * [TomTomMap • Location Marker](https://developer.tomtom.com/maps/android/guides/map-display/markers)
     * */
    lateinit var mapLocationMarkerOptions: LocationMarkerOptions

    /**
     * [TomTomMap • Location Provider • OnLocationUpdateListener](https://developer.tomtom.com/android/maps/documentation/guides/location/built-in-location-provider)
     * */
    private lateinit var mapLocationUpdateListener: OnLocationUpdateListener

    companion object {
        private const val TAG = "TomTomMapConfig"
    }

    override fun onCleared() {
        super.onCleared()

        // Avoid OOM
        unregisterLocationUpdateListener()
    }

    fun initTomTomMapComponent(
        context: Context,
        mapDevKey: String
    ) {
        createLocationProvider(context)
        createMapOptions(mapDevKey)
        createLocationMarkerOptions()
    }

    private fun createMapOptions(mapDevKey: String) {
        mapOptions = MapOptions(mapKey = mapDevKey)
    }

    private fun createLocationMarkerOptions() {
        MapLogUtil.d(TAG, "[Location Marker Options] Create new options config for map marker.")
        mapLocationMarkerOptions = LocationMarkerOptions(
            type = LocationMarkerOptions.Type.Pointer
        )
    }

    private fun createLocationProvider(context: Context) {
        MapLogUtil.d(TAG, "[Location Provider] Register the location provider.")
        mapLocationProvider = AndroidLocationProvider(context)
    }

    fun registerLocationUpdateListener(onLocationUpdate: (latestLocationInfo: GeoLocation) -> Unit) {
        MapLogUtil.d(TAG, "[Location Update Listener] Register the location update listener.")
        mapLocationUpdateListener = OnLocationUpdateListener { onLocationUpdate.invoke(it) }
        mapLocationProvider.addOnLocationUpdateListener(mapLocationUpdateListener)
    }

    fun unregisterLocationUpdateListener() {
        MapLogUtil.d(TAG, "[Location Update Listener] Unregister the location update listener.")
        mapLocationProvider.removeOnLocationUpdateListener(mapLocationUpdateListener)
    }

}