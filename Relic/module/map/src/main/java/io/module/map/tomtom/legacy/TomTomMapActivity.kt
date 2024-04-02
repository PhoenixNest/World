package io.module.map.tomtom.legacy

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.camera.CameraOptions
import com.tomtom.sdk.map.display.ui.MapFragment
import io.module.map.R
import io.module.map.databinding.ActivityTomtomMapBinding
import io.module.map.permission.MapPermissionCenter
import io.module.map.permission.MapPermissionListener
import io.module.map.tomtom.TomTomMapCustomizer.DEFAULT_IS_ENABLE_ZOOM_CONTROL_VIEW
import io.module.map.tomtom.TomTomMapCustomizer.DEFAULT_IS_SHOW_SCALE_VIEW
import io.module.map.tomtom.TomTomMapCustomizer.DEFAULT_LOCATION_BUTTON_POLICY
import io.module.map.tomtom.TomTomMapCustomizer.DEFAULT_SCALE_VIEW_UNIT
import io.module.map.tomtom.TomTomMapCustomizer.DEFAULT_TRACKING_MODE
import io.module.map.tomtom.TomTomMapCustomizer.DEFAULT_VIEW_TILE
import io.module.map.tomtom.TomTomMapCustomizer.DEFAULT_ZOOM_VALUE
import io.module.map.utils.MapLogUtil

class TomTomMapActivity : AppCompatActivity() {

    /**
     * View binding
     * */
    private val binding by lazy {
        ActivityTomtomMapBinding.inflate(layoutInflater)
    }

    /**
     * ViewModel
     * */
    private val mapViewModel by lazy {
        ViewModelProvider(this)[TomTomMapViewModel::class.java]
    }

    private lateinit var mapFragment: MapFragment
    private lateinit var tomtomMap: TomTomMap

    companion object {
        private const val TAG = "TomTomMapActivity"
        private const val KEY_MAP_FRAGMENT = "TomTomMapFragment"

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
        MapLogUtil.d(TAG, "[Map Lifecycle] onResume")
        mapFragment.onResume()
    }

    override fun onPause() {
        super.onPause()
        MapLogUtil.d(TAG, "[Map Lifecycle] onPause")
        mapFragment.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        MapLogUtil.d(TAG, "[Map Lifecycle] onDestroy")

        // Avoid OOM
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
        mapViewModel.initTomTomMapComponent(
            context = this@TomTomMapActivity,
            mapDevKey = mapDevKey
        )
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
            MapLogUtil.d(TAG, "[Map Permission] Permission granted")
            showUserLocation()
        } else {
            MapLogUtil.w(TAG, "[Map Permission] Request runtime permission")
            val permissionList = listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            requestRuntimePermission(permissionList)
        }
    }

    private fun showUserLocation() {
        MapLogUtil.d(TAG, "[User Location] Get the latest location info of user")
        // Enable user location.
        mapViewModel.mapLocationProvider.enable()
        // Binds the location provider.
        tomtomMap.setLocationProvider(mapViewModel.mapLocationProvider)
        // Enable the location marker.
        tomtomMap.enableLocationMarker(mapViewModel.mapLocationMarkerOptions)
        // Register the location update listener to access the latest user location.
        mapViewModel.registerLocationUpdateListener {
            val geoPoint = it.position
            val latitude = geoPoint.latitude
            val longitude = geoPoint.longitude
            MapLogUtil.d(TAG, "[User Location] Latest location: ($latitude, $longitude)")
            mapViewModel.unregisterLocationUpdateListener()
            // Smooth animation to the new position.
            tomtomMap.animateCamera(
                CameraOptions(
                    position = geoPoint,
                    zoom = DEFAULT_ZOOM_VALUE,
                    tilt = DEFAULT_VIEW_TILE
                )
            )
        }
    }

    private fun requestRuntimePermission(permissionList: List<String>) {
        permissionList.forEach { permission ->
            MapPermissionCenter.requestRuntimePermission(
                activity = this@TomTomMapActivity,
                requestPermission = permission,
                permissionListener = object : MapPermissionListener {
                    override fun onPermissionGranted() {
                        MapLogUtil.d(TAG, "[Map Permission] Permission Granted")
                        enableUserLocation()
                    }

                    override fun onPermissionDenied() {
                        MapLogUtil.e(TAG, "[Map Permission] Permission Denied")
                    }
                }
            )
        }
    }

    /* ======================== Ui ======================== */

    private fun activeImmersiveMode() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun toggleLoadingView(isShow: Boolean) {
        binding.linearLayoutLoading.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun setupMapFragment() {
        toggleLoadingView(true)
        mapFragment = MapFragment.newInstance(mapViewModel.mapOptions)

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

        // Customize the ui style of map
        customMapStyle()
        customMapFragment()
    }

    private fun customMapFragment() {
        mapFragment.apply {
            zoomControlsView.isVisible = DEFAULT_IS_ENABLE_ZOOM_CONTROL_VIEW
            scaleView.units = DEFAULT_SCALE_VIEW_UNIT
            scaleView.isVisible = DEFAULT_IS_SHOW_SCALE_VIEW
            currentLocationButton.visibilityPolicy = DEFAULT_LOCATION_BUTTON_POLICY
        }
    }

    private fun customMapStyle() {
        tomtomMap.apply {
            showHillShading()
            cameraTrackingMode = DEFAULT_TRACKING_MODE
        }
    }
}