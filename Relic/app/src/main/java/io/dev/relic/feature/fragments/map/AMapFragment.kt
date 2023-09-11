package io.dev.relic.feature.fragments.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import io.dev.relic.databinding.FragmentAmapBinding
import io.dev.relic.domain.map.amap.AMapPrivacyCenter
import io.dev.relic.feature.fragments.AbsBaseFragment
import io.dev.relic.global.RelicApplication

/**
 * [Ali-Map](https://lbs.amap.com/api/android-sdk/summary/)
 * */
class AMapFragment : AbsBaseFragment() {

    private var _binding: FragmentAmapBinding? = null
    private val binding: FragmentAmapBinding get() = _binding!!

    companion object {
        private const val TAG = "AMapFragment"
    }

    /* ======================== override ======================== */

    override fun initialization(savedInstanceState: Bundle?) {
        verifyAMapPrivacyAgreement()
    }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAmapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initUi(savedInstanceState: Bundle?) {
        setupMapView(savedInstanceState)
    }

    /* ======================== Lifecycle ======================== */

    override fun onResume() {
        super.onResume()
        binding.aMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.aMapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.aMapView.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Avoid OOM
        binding.aMapView.onDestroy()
        _binding = null
    }

    /* ======================== Logical ======================== */

    private fun verifyAMapPrivacyAgreement() {
        AMapPrivacyCenter.verifyAMapPrivacyAgreement(RelicApplication.getApplicationContext())
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