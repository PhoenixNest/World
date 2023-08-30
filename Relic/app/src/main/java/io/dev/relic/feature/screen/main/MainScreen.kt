package io.dev.relic.feature.screen.main

import androidx.compose.foundation.layout.Box
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
import io.dev.relic.core.data.network.monitor.NetworkMonitor
import io.dev.relic.core.data.network.monitor.NetworkStatus
import io.dev.relic.feature.route.MainFeatureNavHost

@Composable
fun MainScreen(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    mainScreenState: MainScreenState = rememberMainScreenState(
        networkMonitor = networkMonitor
    )
) {
    // Current host state of snackBar.
    val snackBarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }

    // Check the current network status by using networkMonitor flow.
    val networkStatus: NetworkStatus by networkMonitor.observe()
        .collectAsStateWithLifecycle(initialValue = NetworkStatus.Available)

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
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { paddingValues: PaddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                MainFeatureNavHost(
                    mainScreenState = mainScreenState,
                    navHostController = mainScreenState.navHostController
                )
            }
        }
    }
}