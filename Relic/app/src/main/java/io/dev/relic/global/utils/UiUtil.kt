package io.dev.relic.global.utils

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

object UiUtil {

    object SystemUtil {

        @SuppressLint("ComposableNaming")
        @Composable
        fun setImmersiveMode() {
            rememberSystemUiController().apply {
                setStatusBarColor(
                    color = Color.Transparent,
                    darkIcons = false
                )
                setNavigationBarColor(
                    color = Color.Transparent,
                    darkIcons = false
                )
            }
        }
    }
}