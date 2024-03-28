package io.module.map.tomtom

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.camera.CameraOptions
import com.tomtom.sdk.map.display.ui.MapFragment
import io.module.map.R
import io.module.map.databinding.ActivityTomtomMapBinding
import io.module.map.tomtom.permission.MapPermissionCenter
import io.module.map.tomtom.permission.MapPermissionListener
import io.module.map.tomtom.utils.MapLogUtil

class TomTomMapActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTomtomMapBinding.inflate(layoutInflater)
    }

    private lateinit var mapFragment: MapFragment
    private lateinit var tomtomMap: TomTomMap

    /**
     * [TomTomMap • Location Provider](https://developer.tomtom.com/android/maps/documentation/guides/location/built-in-location-provider)
     * */
    private val mapLocationProvider by lazy {
        TomTomMapManager.mapLocationProvider
    }

    /**
     * [TomTomMap • MapOptions](https://developer.tomtom.com/assets/downloads/tomtom-sdks/android/api-reference/0.33.1/maps/display-common/com.tomtom.sdk.map.display/-map-options/index.html)
     * */
    private val mapOptions by lazy {
        TomTomMapManager.mapOptions
    }

    /**
     * [TomTomMap • Location Marker](https://developer.tomtom.com/maps/android/guides/map-display/markers)
     * */
    private val mapLocationMarkerOptions by lazy {
        TomTomMapManager.mapLocationMarkerOptions
    }

    companion object {
        private const val TAG = "TomTomMapActivity"

        private const val KEY_MAP_FRAGMENT = "TomTomMapFragment"
        private const val DEFAULT_ZOOM_VALUE = 8.0

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ TomTomMapActivity::class.java
                ).apply {
                    action = "ACTION_VIEW"
                }
            )
        }
    }

    /* ======================== Lifecycle ======================== */

    override fun onCreate(savedInstanceState: Bundle?) {
        activeImmersiveMode()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialization()
    }

    override fun onResume() {
        super.onResume()
        mapFragment.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapFragment.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Avoid OOM
        TomTomMapManager.unregisterLocationUpdateListener()
        mapFragment.onDestroyView()
        mapFragment.onDestroy()
    }

    /* ======================== Logical ======================== */

    private fun initialization() {
        initMapComponent()
        setupMapFragment()
    }

    private fun initMapComponent() {
        val mapDevKey = getString(R.string.tomtom_dev_key)
        TomTomMapManager.initTomTomMapComponent(
            context = this,
            mapDevKey = mapDevKey
        )
    }


    /* ======================== Ui ======================== */

    private fun activeImmersiveMode() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun toggleLoadingView(isShow: Boolean) {
        binding.linearLayoutLoading.visibility = if (isShow) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun setupMapFragment() {
        toggleLoadingView(true)
        mapFragment = MapFragment.newInstance(mapOptions)

        supportFragmentManager.beginTransaction()
            .replace(R.id.map_container, mapFragment, KEY_MAP_FRAGMENT)
            .commit()

        mapFragment.getMapAsync {
            MapLogUtil.d(TAG, "[TomTomMap] Get map async successful.")
            toggleLoadingView(false)
            setupMapView(it)
        }
    }

    private fun setupMapView(map: TomTomMap) {
        tomtomMap = map
        enableUserLocation()
    }

    private fun enableUserLocation() {
        MapLogUtil.d(TAG, "[Map View] Enable user location.")

        val isAccessFineLocation = MapPermissionCenter.checkPermission(
            context = this@TomTomMapActivity,
            requestPermission = Manifest.permission.ACCESS_FINE_LOCATION
        )

        val isAccessCoarseLocation = MapPermissionCenter.checkPermission(
            context = this@TomTomMapActivity,
            requestPermission = Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (isAccessFineLocation && isAccessCoarseLocation) {
            showUserLocation()
        } else {
            val permissionList = listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            requestRuntimePermission(permissionList)
        }
    }

    private fun showUserLocation() {
        // Enable user location.
        mapLocationProvider.enable()
        // Binds the location provider.
        tomtomMap.setLocationProvider(mapLocationProvider)
        // Enable the location marker.
        tomtomMap.enableLocationMarker(mapLocationMarkerOptions)
        // Register the location update listener to access the latest user location.
        TomTomMapManager.registerLocationUpdateListener {
            MapLogUtil.d(TAG, "[User Location] Latest location: ${it.position}")
            tomtomMap.animateCamera(CameraOptions(it.position, DEFAULT_ZOOM_VALUE))
            TomTomMapManager.unregisterLocationUpdateListener()
        }
    }

    private fun requestRuntimePermission(permissionList: List<String>) {
        permissionList.forEach { permission ->
            MapPermissionCenter.requestRuntimePermission(
                activity = this@TomTomMapActivity,
                requestPermission = permission,
                permissionListener = object : MapPermissionListener {
                    override fun onPermissionGranted() {
                        enableUserLocation()
                    }

                    override fun onPermissionDenied() {
                        //
                    }
                }
            )
        }
    }
}