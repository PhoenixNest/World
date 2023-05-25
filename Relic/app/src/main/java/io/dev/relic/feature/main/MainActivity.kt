package io.dev.relic.feature.main

import android.content.Context
import android.content.Intent
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import io.dev.relic.feature.base.AbsBaseActivity
import io.dev.relic.feature.main.viewmodel.MainViewModel
import io.dev.relic.global.utils.UiUtil.StatusBarUtil.setImmersiveStatusBar
import io.dev.relic.ui.theme.RelicAppTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : AbsBaseActivity() {

    /**
     * ViewModel
     * */
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    companion object {
        private const val TAG = "MainActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    context,
                    MainActivity::class.java
                ).apply {
                    //
                }
            )
        }
    }

    /* ======================== Logical ======================== */

    override fun initialization() {
        //
    }

    /* ======================== Ui ======================== */

    override fun initUi(modifier: Modifier) {
        setContent {
            // Enable immersive status bar.
            setImmersiveStatusBar()

            // A surface container using the 'background' color from the theme
            RelicAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Unspecified)
                        .statusBarsPadding()
                        .captionBarPadding()
                        .imePadding()
                        .navigationBarsPadding()
                ) {
                    MainScreen(
                        windowSizeClass = calculateWindowSizeClass(activity = this),
                        networkMonitor = networkMonitor,
                    )
                }
            }
        }
    }
}