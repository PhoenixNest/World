package io.dev.relic.feature.activities.debug

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import io.dev.relic.databinding.ActivityDebugBinding
import io.dev.relic.domain.location.amap.AMapPrivacyCenter
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
        setupDebugAMapView(savedInstanceState)
    }

    /* ======================== Lifecycle ======================== */

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    /* ======================== Logical ======================== */

    private fun verifyAMapPrivacyAgreement() {
        AMapPrivacyCenter.verifyAMapPrivacyAgreement(RelicApplication.getApplicationContext())
    }

    /* ======================== Ui ======================== */
    private fun setupDebugAMapView(savedInstanceState: Bundle?) {
        binding.mapView.apply {
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
}