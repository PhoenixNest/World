package io.dev.relic.feature.activities.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import io.dev.relic.domain.location.amap.AMapLocationCenter
import io.dev.relic.feature.activities.AbsBaseActivity
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.screen.main.MainScreen
import io.dev.relic.global.RelicApplication
import io.dev.relic.global.utils.UiUtil.SystemUtil.setImmersiveMode
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

    override fun initialization(savedInstanceState: Bundle?) {
        AMapLocationCenter.verifyAMapPrivacyAgreement(RelicApplication.getApplicationContext())
    }

    /* ======================== Ui ======================== */

    override fun initUi(savedInstanceState: Bundle?) {
        setContent {
            // Setup immersive mode.
            setImmersiveMode()

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
                        savedInstanceState = savedInstanceState,
                        windowSizeClass = calculateWindowSizeClass(activity = this),
                        networkMonitor = networkMonitor
                    )
                }
            }
        }
    }
}