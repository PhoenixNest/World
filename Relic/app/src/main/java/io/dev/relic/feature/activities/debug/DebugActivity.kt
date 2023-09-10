package io.dev.relic.feature.activities.debug

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import io.dev.relic.databinding.ActivityDebugBinding
import io.dev.relic.domain.map.amap.AMapPrivacyCenter
import io.dev.relic.feature.activities.AbsBaseActivity
import io.dev.relic.global.RelicApplication

class DebugActivity : AbsBaseActivity() {

    private val binding: ActivityDebugBinding by lazy {
        ActivityDebugBinding.inflate(layoutInflater)
    }

    companion object {
        private const val TAG = "DebugActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ DebugActivity::class.java
                ).apply {
                    action = "[Activity] Debug"
                }
            )
        }
    }

    /* ======================== override ======================== */

    override fun initialization(savedInstanceState: Bundle?) {
        verifyAMapPrivacyAgreement()
    }

    override fun initUi(savedInstanceState: Bundle?) {
        setContentView(binding.root)
        setupDebugView(savedInstanceState)
    }

    /* ======================== Lifecycle ======================== */

    override fun onStart() {
        super.onStart()
        mapOnStart()
    }

    override fun onResume() {
        super.onResume()
        mapOnResume()
    }

    override fun onPause() {
        super.onPause()
        mapOnPause()
    }

    override fun onStop() {
        super.onStop()
        mapOnStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapOnSavedInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapOnDestroy()
    }

    /* ======================== Logical ======================== */

    private fun verifyAMapPrivacyAgreement() {
        AMapPrivacyCenter.verifyAMapPrivacyAgreement(RelicApplication.getApplicationContext())
    }

    private fun navigateToMLKitActivity() {
        MLKitActivity.start(this)
    }

    private fun navigateToMediaPipeActivity() {
        MediaPipeActivity.start(this)
    }

    /* ======================== Ui ======================== */

    private fun setupDebugView(savedInstanceState: Bundle?) {
        setupTopDebugPanel()
        setupDebugAMapView(savedInstanceState)
        setupDebugTomTomAMapView(savedInstanceState)
    }

    private fun setupTopDebugPanel() {
        binding.apply {
            cardViewMlKit.setOnClickListener { navigateToMLKitActivity() }
            cardViewMediaPipe.setOnClickListener { navigateToMediaPipeActivity() }
        }
    }

    private fun setupDebugAMapView(savedInstanceState: Bundle?) {
        binding.aMapView.apply {
            // 此方法必须重写
            onCreate(savedInstanceState)
        }.also { mapView: MapView ->
            val aMap: AMap = mapView.map
            aMap.apply {
                // 显示实时交通状况
                isTrafficEnabled = true

                // 地图模式可选类型：
                // - MAP_TYPE_NORMAL
                // - MAP_TYPE_SATELLITE
                // - MAP_TYPE_NIGHT
                mapType = AMap.MAP_TYPE_NORMAL
            }
        }
    }

    private fun setupDebugTomTomAMapView(savedInstanceState: Bundle?) {
        binding.tomtomMapView.apply {
            onCreate(savedInstanceState)
        }.getMapAsync {
            //
        }
    }

    private fun mapOnStart() {
        binding.apply {
            tomtomMapView.onStart()
        }
    }

    private fun mapOnResume() {
        binding.apply {
            aMapView.onResume()
            tomtomMapView.onResume()
        }
    }

    private fun mapOnPause() {
        binding.apply {
            aMapView.onPause()
            tomtomMapView.onPause()
        }
    }

    private fun mapOnStop() {
        binding.apply {
            tomtomMapView.onStop()
        }
    }

    private fun mapOnSavedInstanceState(outState: Bundle) {
        binding.apply {
            tomtomMapView.onSaveInstanceState(outState)
        }
    }

    private fun mapOnDestroy() {
        binding.apply {
            aMapView.onDestroy()
            tomtomMapView.onDestroy()
        }
    }
}