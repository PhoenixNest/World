package io.dev.relic.feature.fragments.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.dev.relic.databinding.FragmentTomtommapBinding
import io.dev.relic.feature.fragments.AbsBaseFragment
import io.dev.relic.global.utils.LogUtil

/**
 * [TomTomMap](https://developer.tomtom.com/android/maps/documentation/overview/introduction)
 */
class TomTomMapFragment : AbsBaseFragment() {

    private var _binding: FragmentTomtommapBinding? = null
    private val binding: FragmentTomtommapBinding get() = _binding!!

    companion object {
        private const val TAG = "TomTomMapFragment"
    }

    /* ======================== override ======================== */

    override fun initialization(savedInstanceState: Bundle?) {
        //
    }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTomtommapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initUi(savedInstanceState: Bundle?) {
        setupDebugTomTomAMapView(savedInstanceState)
    }

    /* ======================== Lifecycle ======================== */

    override fun onStart() {
        super.onStart()
        LogUtil.debug(TAG, "[TomTomMap] onStart")
        binding.tomtomMapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        LogUtil.debug(TAG, "[TomTomMap] onResume")
        binding.tomtomMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        LogUtil.debug(TAG, "[TomTomMap] onPause")
        binding.tomtomMapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        LogUtil.debug(TAG, "[TomTomMap] onStop")
        binding.tomtomMapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtil.debug(TAG, "[TomTomMap] onSaveInstanceState")
        binding.tomtomMapView.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Avoid OOM
        LogUtil.debug(TAG, "[TomTomMap] onDestroy")
        binding.tomtomMapView.onDestroy()
        _binding = null
    }

    /* ======================== Ui ======================== */

    private fun setupDebugTomTomAMapView(savedInstanceState: Bundle?) {
        binding.tomtomMapView.apply {
            onCreate(savedInstanceState)
        }.getMapAsync {
            //
        }
    }
}