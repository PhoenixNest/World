package io.dev.relic.feature.activities.deeplink

import android.os.Bundle
import android.view.View
import io.common.util.LogUtil
import io.dev.relic.feature.activities.main.MainActivity
import io.dev.relic.feature.activities.splash.SplashActivity
import io.domain.app.AbsBaseActivity

class DeepLinkActivity : AbsBaseActivity() {

    companion object {
        private const val TAG = "DeepLinkActivity"
    }

    /* ======================== Logical ======================== */

    override fun initialization(savedInstanceState: Bundle?) {
        checkAndNavigate().also { finish() }
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