package io.dev.relic.global.utils.ext

import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.SystemUiController
import io.dev.relic.ui.theme.mainThemeColor

object SystemUiControllerExt {

    fun SystemUiController.enableImmersiveMode() {
        setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false
        )
        setNavigationBarColor(
            color = mainThemeColor,
            darkIcons = false
        )
    }

}