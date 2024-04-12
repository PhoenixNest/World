package io.module.map.tomtom.legacy.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.tomtom.sdk.featuretoggle.FeatureToggleController
import com.tomtom.sdk.featuretoggle.TomTomOrbisMapFeature
import com.tomtom.sdk.location.GeoLocation
import com.tomtom.sdk.location.GeoPoint
import com.tomtom.sdk.location.LocationProvider
import com.tomtom.sdk.location.OnLocationUpdateListener
import com.tomtom.sdk.location.android.AndroidLocationProvider
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.location.LocationMarkerOptions
import com.tomtom.sdk.search.Search
import com.tomtom.sdk.search.SearchCallback
import com.tomtom.sdk.search.SearchOptions
import com.tomtom.sdk.search.SearchResponse
import com.tomtom.sdk.search.common.error.SearchFailure
import com.tomtom.sdk.search.online.OnlineSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import io.module.map.utils.LogUtil
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

    /**
     * [Making search API calls](https://developer.tomtom.com/maps/android/guides/search/quickstart#making-search-api-calls)
     * */
    private lateinit var searchApi: Search

    companion object {
        private const val TAG = "TomTomMapConfig"
    }

    fun initTomTomMapComponent(
        context: Context,
        devKey: String
    ) {
        createLocationProvider(context)
        createMapOptions(devKey)
        createLocationMarkerOptions()
    }

    fun initOnlineSearchService(
        context: Context,
        devKey: String
    ) {
        createOnlineSearchApi(context, devKey)
    }

    fun registerLocationUpdateListener(onLocationUpdate: (latestLocationInfo: GeoLocation) -> Unit) {
        LogUtil.d(TAG, "[Location Update Listener] Register the location update listener.")
        mapLocationUpdateListener = OnLocationUpdateListener { onLocationUpdate.invoke(it) }
        mapLocationProvider.addOnLocationUpdateListener(mapLocationUpdateListener)
    }

    fun unregisterLocationUpdateListener() {
        LogUtil.d(TAG, "[Location Update Listener] Unregister the location update listener.")
        mapLocationProvider.removeOnLocationUpdateListener(mapLocationUpdateListener)
    }

    fun searchLocationInfo(
        geoPoint: GeoPoint,
        searchPrompt: String,
        searchLimit: Int,
        listener: TomTomMapSearchListener
    ) {
        val options = SearchOptions(
            query = searchPrompt,
            geoBias = geoPoint,
            limit = searchLimit
        )
        searchApi.search(
            options = options,
            callback = object : SearchCallback {
                override fun onSuccess(result: SearchResponse) {
                    listener.onSuccess(result)
                }

                override fun onFailure(failure: SearchFailure) {
                    listener.onFailure(failure)
                }
            }
        )
    }

    private fun createMapOptions(devKey: String) {
        mapOptions = MapOptions(mapKey = devKey)
    }

    private fun createLocationMarkerOptions() {
        LogUtil.d(TAG, "[Location Marker Options] Create new options config for map marker.")
        mapLocationMarkerOptions = LocationMarkerOptions(
            type = LocationMarkerOptions.Type.Pointer
        )
    }

    private fun createLocationProvider(context: Context) {
        LogUtil.d(TAG, "[Location Provider] Register location provider.")
        mapLocationProvider = AndroidLocationProvider(context)
    }

    private fun createOnlineSearchApi(context: Context, devKey: String) {
        LogUtil.d(TAG, "[Online Search] Creates search api.")
        searchApi = OnlineSearch.create(context, devKey)
    }

}