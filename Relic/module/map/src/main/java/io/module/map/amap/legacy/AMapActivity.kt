package io.module.map.amap.legacy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps.AMap
import io.module.map.amap.AMapPrivacyCenter
import io.module.map.databinding.ActivityAmapBinding
import io.module.map.utils.LogUtil

/**
 * [Ali-Map](https://lbs.amap.com/api/android-sdk/summary/)
 * */
class AMapActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAmapBinding.inflate(layoutInflater)
    }

    companion object {
        private const val TAG = "AMapActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ AMapActivity::class.java
                ).apply {
                    action = "[Activity] AMap"
                }
            )
        }
    }

    /* ======================== Lifecycle ======================== */

    override fun onResume() {
        super.onResume()
        LogUtil.d(TAG, "[AMap] onResume")
        binding.aMapView.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        verifyAMapPrivacyAgreement()
        setupMapView(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        LogUtil.d(TAG, "[AMap] onPause")
        binding.aMapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtil.d(TAG, "[AMap] onSaveInstanceState")
        binding.aMapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()

        // Avoid OOM
        LogUtil.d(TAG, "[AMap] onDestroy")
        binding.aMapView.onDestroy()
    }

    /* ======================== Logical ======================== */

    private fun verifyAMapPrivacyAgreement() {
        val isShowUserAgreement = true
        val isAgreeUserPrivacy = true
        LogUtil.d(TAG, "[UserAgreement] 是否同意用户协议: $isShowUserAgreement")
        LogUtil.d(TAG, "[UserPrivacy] 是够同意用户隐私协议: $isAgreeUserPrivacy")

        AMapPrivacyCenter.verifyAMapPrivacyAgreement(
            context = this,
            isShowUserAgreement = isShowUserAgreement,
            isAgreeUserPrivacy = isShowUserAgreement
        )
    }

    /* ======================== Ui ======================== */

    private fun setupMapView(savedInstanceState: Bundle?) {
        binding.aMapView.apply {
            // 此方法必须重写
            onCreate(savedInstanceState)
        }.also { mapView ->
            val aMap = mapView.map
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