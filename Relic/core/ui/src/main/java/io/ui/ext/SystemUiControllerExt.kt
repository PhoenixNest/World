package io.ui.ext

import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.SystemUiController
import io.ui.theme.mainThemeColor

object SystemUiControllerExt {

    fun SystemUiController.enableImmersiveMode(
        statusBarColor: Color = Color.Transparent,
        navigationBarColor: Color = mainThemeColor,
        darkIcons: Boolean = false
    ) {
        setStatusBarColor(
            color = statusBarColor,
            darkIcons = darkIcons
        )
        setNavigationBarColor(
            color = navigationBarColor,
            darkIcons = darkIcons
        )
    }

    fun SystemUiController.updateStatusBarColor(
        statusBarColor: Color = Color.Transparent,
        darkIcons: Boolean = false
    ) {
        setStatusBarColor(
            color = statusBarColor,
            darkIcons = darkIcons
        )
    }

    fun SystemUiController.updateNavigationBarColor(
        navigationBarColor: Color = mainThemeColor,
        darkIcons: Boolean = false
    ) {
        setNavigationBarColor(
            color = navigationBarColor,
            darkIcons = darkIcons
        )
    }
}