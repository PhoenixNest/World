package io.dev.relic.feature.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
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
import io.dev.relic.feature.main.route.MainFeatureNavHost
import io.dev.relic.feature.main.route.widget.MainBottomBar
import io.dev.relic.feature.main.route.widget.MainRailAppBar

@Composable
fun MainScreen(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    modifier: Modifier = Modifier,
    mainScreenState: MainScreenState = rememberMainScreenState(
        windowSizeClass = windowSizeClass,
        networkMonitor = networkMonitor
    )
) {
    // Current host state of snackBar.
    val snackBarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }

    // Check the current network status by using networkMonitor flow.
    val networkStatus: NetworkStatus by networkMonitor.observe()
        .collectAsStateWithLifecycle(initialValue = NetworkStatus.Unavailable)

    val noNetworkMessage: String = stringResource(id = R.string.no_network_connection_message)
    LaunchedEffect(networkStatus) {
        if (networkStatus != NetworkStatus.Available) {
            snackBarHostState.showSnackbar(
                message = noNetworkMessage,
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    // Initialization the App main page.
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (mainScreenState.currentTopLevelDestination != null) {
                MainBottomBar(
                    destinations = mainScreenState.topLevelDestinations,
                    onNavigateToDestination = mainScreenState::navigateToTopLevelDestination,
                    currentDestination = mainScreenState.currentDestination
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { paddingValues: PaddingValues ->
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (mainScreenState.shouldShowRailBar) {
                MainRailAppBar(
                    destinations = mainScreenState.topLevelDestinations,
                    onNavigateToDestination = mainScreenState::navigateToTopLevelDestination,
                    currentDestination = mainScreenState.currentDestination
                )
            }
            Column(modifier = modifier.fillMaxSize()) {
                MainFeatureNavHost(navHostController = mainScreenState.navHostController)
            }
        }
    }
}