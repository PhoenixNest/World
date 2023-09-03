package io.dev.relic.feature.activities.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import io.dev.relic.feature.activities.AbsBaseActivity
import io.dev.relic.global.utils.UiUtil
import io.dev.relic.ui.theme.RelicAppTheme

@SuppressLint("CustomSplashScreen")
class SplashActivity : AbsBaseActivity() {

    companion object {
        private const val TAG = "SplashActivity"
    }

    /* ======================== Lifecycle ======================== */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization()
        initUi()
    }

    /* ======================== Logical ======================== */

    override fun initialization() {
        //
    }

    /* ======================== Ui ======================== */

    override fun initUi() {
        setContent {
            // Setup immersive mode.
            UiUtil.SystemUtil.setImmersiveMode()
            UiUtil.StatusBarUtil.setImmersiveStatusBar()

            // A surface container using the 'background' color from the theme
            RelicAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SplashScreen()
                }
            }
        }
    }
}