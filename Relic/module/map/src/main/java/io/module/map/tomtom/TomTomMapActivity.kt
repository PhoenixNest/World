package io.module.map.tomtom

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tomtom.sdk.location.OnLocationUpdateListener
import com.tomtom.sdk.location.android.AndroidLocationProvider
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.camera.CameraOptions
import com.tomtom.sdk.map.display.common.screen.Padding
import com.tomtom.sdk.map.display.location.LocationMarkerOptions
import com.tomtom.sdk.map.display.map.OnlineCachePolicy
import com.tomtom.sdk.map.display.style.StyleDescriptor
import com.tomtom.sdk.map.display.style.StyleMode
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

    private lateinit var mapOptions: MapOptions
    private lateinit var mapLocationProvider: AndroidLocationProvider
    private lateinit var mapLocationMarkerOptions: LocationMarkerOptions
    private lateinit var mapLocationUpdateListener: OnLocationUpdateListener

    companion object {
        private const val TAG = "TomTomMapActivity"

        private const val KEY_MAP_FRAGMENT = "TomTomMapFragment"
        private const val DEFAULT_ZOOM_VALUE = 0.8

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
        unregisterLocationUpdateLocation()
        mapFragment.onDestroyView()
        mapFragment.onDestroy()
    }

    /* ======================== Logical ======================== */

    private fun initialization() {
        createMapParams()
        setupMapFragment()
    }

    private fun createMapParams() {
        createMapOptions()
        createLocationProvider()
        createMapLocationMarkerOptions()
    }

    private fun createMapOptions() {
        mapOptions = MapOptions(
            mapKey = getString(R.string.tomtom_dev_key),
            cameraOptions = CameraOptions(),
            padding = Padding(),
            mapStyle = StyleDescriptor(Uri.EMPTY),
            styleMode = StyleMode.DARK,
            onlineCachePolicy = OnlineCachePolicy.Default,
            renderToTexture = true
        )
    }

    private fun createLocationProvider() {
        mapLocationProvider = AndroidLocationProvider(context = this)
    }

    private fun createMapLocationMarkerOptions() {
        mapLocationMarkerOptions = LocationMarkerOptions(type = LocationMarkerOptions.Type.Chevron)
    }

    private fun createMapLocationUpdateListener(map: TomTomMap) {
        mapLocationUpdateListener = OnLocationUpdateListener {
            map.moveCamera(CameraOptions(position = it.position, zoom = DEFAULT_ZOOM_VALUE))
        }
    }

    /* ======================== Ui ======================== */

    private fun setupMapFragment() {
        mapFragment = MapFragment.newInstance(mapOptions)

        supportFragmentManager.beginTransaction()
            .replace(R.id.map_container, mapFragment, KEY_MAP_FRAGMENT)
            .commit()

        mapFragment.getMapAsync {
            MapLogUtil.d(TAG, "[TomTomMap] Get map async successful.")
            setupMapView(it)
        }
    }

    private fun setupMapView(map: TomTomMap) {
        tomtomMap = map
        enableUserLocation()
    }

    private fun enableUserLocation() {
        val isAccessFineLocation = MapPermissionCenter.checkPermission(
            context = this@TomTomMapActivity,
            requestPermission = ACCESS_FINE_LOCATION
        )

        val isAccessCoarseLocation = MapPermissionCenter.checkPermission(
            context = this@TomTomMapActivity,
            requestPermission = ACCESS_COARSE_LOCATION
        )

        if (isAccessFineLocation && isAccessCoarseLocation) {
            mapLocationProvider.enable()
            showUserLocation()
        } else {
            val permissionList = listOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
            requestRuntimePermission(permissionList)
        }
    }

    private fun showUserLocation() {
        tomtomMap.setLocationProvider(mapLocationProvider)
        tomtomMap.enableLocationMarker(mapLocationMarkerOptions)
        registerLocationUpdateListener()
    }

    private fun registerLocationUpdateListener() {
        createMapLocationUpdateListener(tomtomMap)
        mapLocationProvider.addOnLocationUpdateListener(mapLocationUpdateListener)
    }

    private fun unregisterLocationUpdateLocation() {
        mapLocationProvider.removeOnLocationUpdateListener(mapLocationUpdateListener)
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