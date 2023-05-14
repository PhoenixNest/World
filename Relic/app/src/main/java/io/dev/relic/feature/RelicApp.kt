package io.dev.relic.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.dev.relic.R
import io.dev.relic.core.module.data.network.monitor.NetworkMonitor
import io.dev.relic.core.module.data.network.monitor.NetworkStatus
import io.dev.relic.core.route.RelicNavHost
import io.dev.relic.core.route.widget.RelicBottomAppBar
import io.dev.relic.core.route.widget.RelicRailAppBar
import io.dev.relic.ui.theme.RelicAppTheme

@Composable
fun RelicApp(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    modifier: Modifier = Modifier,
    relicAppState: RelicAppState = rememberRelicAppState(
        windowSizeClass = windowSizeClass,
        networkMonitor = networkMonitor
    )
) {
    // Current host state of snackbar.
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    // Check current network status by using networkMonitor flow.
    val networkStatus: NetworkStatus by networkMonitor.observe()
        .collectAsStateWithLifecycle(initialValue = NetworkStatus.Unavailable)

    val noNetworkMessage: String = stringResource(id = R.string.no_network_connection_message)
    LaunchedEffect(networkStatus) {
        if (networkStatus != NetworkStatus.Available) {
            snackbarHostState.showSnackbar(
                message = noNetworkMessage,
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    // Initialization the App main page.
    RelicAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if (relicAppState.currentTopLevelDestination != null) {
                    RelicBottomAppBar(
                        destinations = relicAppState.topLevelDestinations,
                        onNavigateToDestination = relicAppState::navigateToTopLevelDestination,
                        currentDestination = relicAppState.currentDestination
                    )
                }
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { paddingValues: PaddingValues ->
            Surface(modifier = modifier.padding(paddingValues)) {
                Row(modifier = modifier.fillMaxSize()) {
                    if (relicAppState.shouldShowRailBar) {
                        RelicRailAppBar(
                            destinations = relicAppState.topLevelDestinations,
                            onNavigateToDestination = relicAppState::navigateToTopLevelDestination,
                            currentDestination = relicAppState.currentDestination
                        )
                    }
                    Column(modifier = modifier.fillMaxSize()) {
                        RelicNavHost(navHostController = relicAppState.navHostController)
                    }
                }
            }
        }
    }
}