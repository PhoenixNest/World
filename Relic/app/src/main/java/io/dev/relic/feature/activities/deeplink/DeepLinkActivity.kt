package io.dev.relic.feature.activities.deeplink

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import io.common.util.LogUtil
import io.dev.relic.feature.activities.main.MainActivity
import io.dev.relic.feature.activities.splash.SplashActivity
import io.domain.app.AbsBaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DeepLinkActivity : AbsBaseActivity() {

    companion object {
        private const val TAG = "DeepLinkActivity"
    }

    /* ======================== Logical ======================== */

    override fun initialization(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            parseDeeplink()
            delay(200L)
            checkAndNavigate().also { finish() }
        }
    }

    private fun parseDeeplink() {
        if (intent == null) {
            LogUtil.e(TAG, "[Parse Deeplink] Parse failed, normal start.")
            return
        }

        intent.data?.parseData()
    }

    private fun Uri.parseData() {
        val originalUri = this
        LogUtil.apply {
            d(TAG, "[Parse Uri Data] Original Uri: $originalUri")
            d(TAG, "[Parse Uri Data] scheme: $scheme")
            d(TAG, "[Parse Uri Data] host: $host")
            d(TAG, "[Parse Uri Data] path: $path")
            d(TAG, "[Parse Uri Data] port: $port")
            for (parameter: String in queryParameterNames) {
                d(TAG, "[Parse Uri Data] parameter: $parameter")
            }
        }
    }

    private fun checkAndNavigate() {
        // TODO: Need a route guard to check the app route.
        // when {
        //     RelicLifecycleObserver.isFirstColdStart -> navigateToSplashActivity()
        //     else -> navigateToMainActivity()
        // }

        navigateToSplashActivity()
    }

    private fun navigateToSplashActivity() {
        LogUtil.d(TAG, "[Splash Navigator] Navigate to SplashActivity")
        SplashActivity.start(this)
    }

    private fun navigateToMainActivity() {
        LogUtil.d(TAG, "[Splash Navigator] Navigate to MainActivity")
        MainActivity.start(this)
    }

    /* ======================== Ui ======================== */

    override fun initUi(savedInstanceState: Bundle?) {
        val emptyView = View(this)
        setContentView(emptyView)
    }
}