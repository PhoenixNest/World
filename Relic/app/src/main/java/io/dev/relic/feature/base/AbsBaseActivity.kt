package io.dev.relic.feature.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.dev.relic.core.module.data.network.monitor.NetworkMonitor
import io.dev.relic.core.module.data.network.monitor.NetworkStatus
import io.dev.relic.global.util.LogUtil
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
abstract class AbsBaseActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    companion object {
        private const val TAG: String = "BaseActivity"
    }

    /* ======================== Lifecycle ======================== */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preInitialization()
        initialization()
        preInitUi()
    }

    /* ======================== Logical ======================== */

    /**
     * Pre-initialize some common function or parameters.
     * */
    private fun preInitialization() {
        activeNetworkMonitor()
    }

    /**
     * Active the global network monitor.
     * */
    private fun activeNetworkMonitor() {
        networkMonitor.observe().onEach { status: NetworkStatus ->
            LogUtil.debug(TAG, "Current Network status: ${status.name}")
        }.launchIn(lifecycleScope)
    }

    abstract fun initialization()

    /* ======================== Ui ======================== */

    private fun preInitUi() {
        activeImmersiveStatusBar()
    }

    private fun activeImmersiveStatusBar() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    abstract fun initUi()

}