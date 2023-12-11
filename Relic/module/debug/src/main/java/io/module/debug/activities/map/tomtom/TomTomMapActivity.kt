package io.module.debug.activities.map.tomtom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.common.util.LogUtil
import io.domain.AbsBaseActivity
import io.module.debug.databinding.ActivityTomtommapBinding

/**
 * [TomTomMap](https://developer.tomtom.com/)
 * */
class TomTomMapActivity : AbsBaseActivity() {

    private val binding: ActivityTomtommapBinding by lazy {
        ActivityTomtommapBinding.inflate(layoutInflater)
    }

    companion object {
        private const val TAG = "TomTomMapActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ TomTomMapActivity::class.java
                ).apply {
                    action = "[Activity] TomTomMap"
                }
            )
        }
    }

    /* ======================== override ======================== */

    override fun initialization(savedInstanceState: Bundle?) {
        //
    }

    override fun initUi(savedInstanceState: Bundle?) {
        setContentView(binding.root)
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

    override fun onDestroy() {
        super.onDestroy()

        // Avoid OOM
        LogUtil.debug(TAG, "[TomTomMap] onDestroy")
        binding.tomtomMapView.onDestroy()
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