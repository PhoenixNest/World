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
import io.dev.relic.feature.activities.AbsBaseActivity
import io.dev.relic.feature.screen.main.MainScreen
import io.dev.relic.global.utils.UiUtil
import io.dev.relic.ui.theme.RelicAppTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : AbsBaseActivity() {

    companion object {
        private const val TAG = "MainActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    context,
                    MainActivity::class.java
                ).apply {
                    action = "[Activity] Main"
                }
            )
        }
    }

    /* ======================== Logical ======================== */

    override fun initialization() {
        //
    }

    /* ======================== Ui ======================== */

    override fun initUi() {
        setContent {
            // Setup immersive status bar.
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