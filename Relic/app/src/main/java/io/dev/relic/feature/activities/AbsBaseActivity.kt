package io.dev.relic.feature.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.dev.relic.core.data.network.monitor.NetworkMonitor
import io.dev.relic.core.data.network.monitor.NetworkStatus
import io.dev.relic.global.utils.LogUtil
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
abstract class AbsBaseActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    companion object {
        private const val TAG: String = "AbsBaseActivity"
    }

    /* ======================== Lifecycle ======================== */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preInitialization(
            doOnFinish = {
                initialization(savedInstanceState)
            }
        )

        preInitUi(
            doOnFinish = {
                initUi(savedInstanceState)
            }
        )
    }

    /* ======================== Logical ======================== */

    /**
     * Pre-initialize some common function or parameters.
     * */
    private fun preInitialization(doOnFinish: () -> Unit) {
        lifecycleScope.launch {
            activeNetworkMonitor()
            doOnFinish.invoke()
        }
    }

    /**
     * Active the global network monitor.
     * */
    private fun activeNetworkMonitor() {
        networkMonitor.observe().onEach { status: NetworkStatus ->
            LogUtil.debug(TAG, "Current Network status: ${status.name}")
        }.launchIn(lifecycleScope)
    }

    abstract fun initialization(savedInstanceState: Bundle?)

    /* ======================== Ui ======================== */

    private fun preInitUi(doOnFinish: () -> Unit) {
        lifecycleScope.launch {
            activeImmersiveStatusBar()
            doOnFinish.invoke()
        }
    }

    private fun activeImmersiveStatusBar() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    abstract fun initUi(savedInstanceState: Bundle?)

}