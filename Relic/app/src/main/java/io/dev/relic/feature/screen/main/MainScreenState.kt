package io.dev.relic.feature.screen.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import io.dev.relic.core.data.network.monitor.NetworkMonitor
import io.dev.relic.core.data.network.monitor.NetworkStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberMainScreenState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navHostController: NavHostController = rememberAnimatedNavController()
): MainScreenState {
    return remember(
        keys = arrayOf(
            networkMonitor,
            coroutineScope,
            navHostController
        )
    ) {
        MainScreenState(
            networkMonitor,
            coroutineScope,
            navHostController
        )
    }
}

@Stable
class MainScreenState(
    val networkMonitor: NetworkMonitor,
    val coroutineScope: CoroutineScope,
    val navHostController: NavHostController
) {

    val currentNetworkStatus: StateFlow<NetworkStatus> = networkMonitor.observe().stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5 * 1000),
        initialValue = NetworkStatus.Available
    )

    val currentDestination: NavDestination?
        @Composable get() = navHostController
            .currentBackStackEntryAsState()
            .value
            ?.destination

    var shouldShowPrivacyDialog: Boolean by mutableStateOf(value = false)
        private set

}