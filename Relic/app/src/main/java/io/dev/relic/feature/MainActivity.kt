package io.dev.relic.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.dev.relic.core.module.data.network.monitor.NetworkMonitor
import io.dev.relic.core.module.data.network.monitor.NetworkStatus
import io.dev.relic.global.util.LogUtil
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    companion object {
        private val TAG: String = MainActivity::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkMonitor.observe().onEach { status: NetworkStatus ->
            LogUtil.debug(TAG, "Current Network status: ${status.name}")
        }.launchIn(lifecycleScope)

        setContent {
            val windowSizeClass: WindowSizeClass = calculateWindowSizeClass(activity = this)

            RelicApp(
                windowSizeClass = windowSizeClass,
                networkMonitor = networkMonitor,
            )
        }
    }
}