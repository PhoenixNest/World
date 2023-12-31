package io.module.debug.activities.map.amap

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import io.common.util.LogUtil
import io.core.datastore.RelicDatastoreCenter.readSyncData
import io.core.datastore.preference_keys.UserPreferenceKeys.KEY_IS_AGREE_USER_PRIVACY
import io.core.datastore.preference_keys.UserPreferenceKeys.KEY_IS_SHOW_USER_AGREEMENT
import io.domain.app.AbsBaseActivity
import io.module.debug.databinding.ActivityAmapBinding
import io.module.map.amap.AMapPrivacyCenter

/**
 * [Ali-Map](https://lbs.amap.com/api/android-sdk/summary/)
 * */
class AMapActivity : AbsBaseActivity() {

    private val binding: ActivityAmapBinding by lazy {
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

    /* ======================== override ======================== */

    override fun initialization(savedInstanceState: Bundle?) {
        verifyAMapPrivacyAgreement()
    }

    override fun initUi(savedInstanceState: Bundle?) {
        setContentView(binding.root)
        setupMapView(savedInstanceState)
    }

    /* ======================== Lifecycle ======================== */

    override fun onResume() {
        super.onResume()
        LogUtil.debug(TAG, "[AMap] onResume")
        binding.aMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        LogUtil.debug(TAG, "[AMap] onPause")
        binding.aMapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtil.debug(TAG, "[AMap] onSaveInstanceState")
        binding.aMapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()

        // Avoid OOM
        LogUtil.debug(TAG, "[AMap] onDestroy")
        binding.aMapView.onDestroy()
    }

    /* ======================== Logical ======================== */

    private fun verifyAMapPrivacyAgreement() {
        val isShowUserAgreement: Boolean = readSyncData(KEY_IS_SHOW_USER_AGREEMENT, false)
        val isAgreeUserPrivacy: Boolean = readSyncData(KEY_IS_AGREE_USER_PRIVACY, false)
        LogUtil.debug(TAG, "[UserAgreement] 是否同意用户协议: $isShowUserAgreement")
        LogUtil.debug(TAG, "[UserPrivacy] 是够同意用户隐私协议: $isAgreeUserPrivacy")

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