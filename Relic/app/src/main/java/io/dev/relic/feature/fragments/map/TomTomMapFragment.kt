package io.dev.relic.feature.fragments.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.dev.relic.databinding.FragmentTomtommapBinding
import io.dev.relic.feature.fragments.AbsBaseFragment

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
        binding.tomtomMapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.tomtomMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.tomtomMapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.tomtomMapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.tomtomMapView.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Avoid OOM
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