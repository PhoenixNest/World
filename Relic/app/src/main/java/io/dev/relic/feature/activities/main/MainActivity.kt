package io.dev.relic.feature.activities.main

import android.content.Context
import android.content.Intent
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import io.dev.relic.core.device.location.AmapLocationCenter
import io.dev.relic.feature.activities.AbsBaseActivity
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.screen.main.MainScreen
import io.dev.relic.global.utils.UiUtil
import io.dev.relic.ui.theme.RelicAppTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : AbsBaseActivity() {

    /**
     * VM
     * */
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    companion object {
        private const val TAG = "MainActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ MainActivity::class.java
                ).apply {
                    action = "[Activity] Main"
                }
            )
        }
    }

    /* ======================== Logical ======================== */

    override fun initialization() {
        AmapLocationCenter.verifyAmapPrivacyAgreement(this)
    }

    /* ======================== Ui ======================== */

    override fun initUi() {
        setContent {
            // Setup immersive mode.
            UiUtil.SystemUtil.setImmersiveMode()
            UiUtil.StatusBarUtil.setImmersiveStatusBar()

            // A surface container using the 'background' color from the theme
            RelicAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Unspecified)
                        .imePadding()
                        .navigationBarsPadding()
                ) {
                    MainScreen(
                        windowSizeClass = calculateWindowSizeClass(activity = this),
                        networkMonitor = networkMonitor
                    )
                }
            }
        }
    }
}