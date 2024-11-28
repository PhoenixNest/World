package io.dev.relic.feature.activities.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.DisposableEffect
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dagger.hilt.android.AndroidEntryPoint
import io.common.RelicConstants.IntentAction.INTENT_ACTION_VIEW
import io.core.ui.theme.RelicAppBackground
import io.core.ui.theme.RelicAppTheme
import io.dev.relic.feature.screens.main.MainScreen
import io.domain.app.AbsBaseActivity

@AndroidEntryPoint
class MainActivity : AbsBaseActivity() {

    companion object {
        private const val TAG = "MainActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ MainActivity::class.java
                ).apply {
                    action = INTENT_ACTION_VIEW
                }
            )
        }
    }

    /* ======================== Logical ======================== */

    override fun initialization(savedInstanceState: Bundle?) {
        setupCoilImageLoader()
    }

    private fun setupCoilImageLoader() {
        val imageLoader = ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(this.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.2)
                    .build()
            }
            .build()
        Coil.setImageLoader(imageLoader)
    }

    /* ======================== Ui ======================== */

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun initUi(savedInstanceState: Bundle?) {
        setContent {

            val isDarkTheme = isSystemInDarkTheme()

            // Update the edge to edge configuration to match the theme
            // This is the same parameters as the default enableEdgeToEdge call, but we manually
            // resolve whether or not to show dark theme using uiState, since it can be different
            // than the configuration's dark theme value based on the user preference.
            DisposableEffect(isDarkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        lightScrim = Color.TRANSPARENT,
                        darkScrim = Color.TRANSPARENT,
                        detectDarkMode = { isDarkTheme }
                    ),
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim = Color.TRANSPARENT,
                        darkScrim = Color.TRANSPARENT,
                        detectDarkMode = { isDarkTheme }
                    )
                )
                onDispose {}
            }

            RelicAppTheme {
                RelicAppBackground {
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