package io.dev.relic.feature

import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import io.dev.relic.feature.base.AbsBaseActivity
import io.dev.relic.global.util.UiUtil.StatusBarUtil.setImmersiveStatusBar

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : AbsBaseActivity() {

    /* ======================== Logical ======================== */

    override fun initialization() {
    }

    /* ======================== Ui ======================== */

    override fun initUi() {
        setContent {
            val windowSizeClass: WindowSizeClass = calculateWindowSizeClass(activity = this)

            // Enable immersive status bar.
            setImmersiveStatusBar()

            RelicApp(
                windowSizeClass = windowSizeClass,
                networkMonitor = networkMonitor,
            )
        }
    }
}