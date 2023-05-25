package io.dev.relic.global.utils

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

object UiUtil {

    /**
     * 状态栏工具
     * */
    object StatusBarUtil {

        /**
         * 沉浸式状态栏
         * */
        @SuppressLint("ComposableNaming")
        @Composable
        fun setImmersiveStatusBar() {
            rememberSystemUiController().setStatusBarColor(
                color = Color.Transparent,
                darkIcons = MaterialTheme.colors.isLight
            )
        }
    }

}