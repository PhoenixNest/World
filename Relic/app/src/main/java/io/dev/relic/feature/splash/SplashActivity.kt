package io.dev.relic.feature.splash

import android.annotation.SuppressLint
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import io.dev.relic.feature.base.AbsBaseActivity
import io.dev.relic.feature.main.MainActivity
import io.dev.relic.global.utils.UiUtil.StatusBarUtil.setImmersiveStatusBar
import io.dev.relic.ui.theme.RelicAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AbsBaseActivity() {

    companion object {
        private const val TAG = "SplashActivity"
    }

    override fun initialization() {
        lifecycleScope.launch {
            delay(2 * 1000L)
            MainActivity.start(this@SplashActivity)
            finish()
        }
    }

    override fun initUi(modifier: Modifier) {
        setContent {
            // Enable immersive status bar.
            setImmersiveStatusBar()

            // A surface container using the 'background' color from the theme
            RelicAppTheme {
                Surface(
                    modifier = modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .captionBarPadding()
                        .navigationBarsPadding()
                ) {
                    SplashScreen()
                }
            }
        }
    }
}