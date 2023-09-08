package io.dev.relic.feature.activities.debug

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import com.tomtom.sdk.map.display.ui.MapFragment
import io.dev.relic.R
import io.dev.relic.databinding.ActivityDebugBinding
import io.dev.relic.domain.map.amap.AMapPrivacyCenter
import io.dev.relic.global.RelicApplication

class DebugActivity : AppCompatActivity() {

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

    /* ======================== Lifecycle ======================== */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialization(savedInstanceState)
        initUi(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        binding.aMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.aMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.aMapView.onDestroy()
    }

    /* ======================== Logical ======================== */

    private fun initialization(savedInstanceState: Bundle?) {
        verifyAMapPrivacyAgreement()
    }

    private fun verifyAMapPrivacyAgreement() {
        AMapPrivacyCenter.verifyAMapPrivacyAgreement(RelicApplication.getApplicationContext())
    }

    /* ======================== Ui ======================== */

    private fun initUi(savedInstanceState: Bundle?) {
        setupDebugAMapView(savedInstanceState)
        setupDebugTomTomAMapView(savedInstanceState)
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
        val mapFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.tomtomFragmentContainerView)
        (mapFragment as? MapFragment)?.run {
            getMapAsync {
                //
            }
        }
    }
}