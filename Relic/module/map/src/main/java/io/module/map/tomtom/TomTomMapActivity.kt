package io.module.map.tomtom

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.tomtom.sdk.common.measures.UnitSystem
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.camera.CameraOptions
import com.tomtom.sdk.map.display.ui.MapFragment
import com.tomtom.sdk.map.display.ui.currentlocation.CurrentLocationButton
import io.module.map.R
import io.module.map.databinding.ActivityTomtomMapBinding
import io.module.map.tomtom.TomTomMapCustomizer.DEFAULT_TRACKING_MODE
import io.module.map.tomtom.TomTomMapCustomizer.DEFAULT_VIEW_TILE
import io.module.map.tomtom.TomTomMapCustomizer.DEFAULT_ZOOM_VALUE
import io.module.map.tomtom.permission.MapPermissionCenter
import io.module.map.tomtom.permission.MapPermissionListener
import io.module.map.tomtom.utils.MapLogUtil

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
        mapFragment.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapFragment.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

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
            context = this,
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
                        enableUserLocation()
                    }

                    override fun onPermissionDenied() {
                        //
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
            zoomControlsView.isVisible = true
            scaleView.units = UnitSystem.Metric
            scaleView.isVisible = true
            currentLocationButton.visibilityPolicy = CurrentLocationButton.VisibilityPolicy.Visible
        }
    }

    private fun customMapStyle() {
        tomtomMap.apply {
            showHillShading()
            cameraTrackingMode = DEFAULT_TRACKING_MODE
        }
    }
}