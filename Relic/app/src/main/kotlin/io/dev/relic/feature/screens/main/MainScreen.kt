package io.dev.relic.feature.screens.main

import android.os.Bundle
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.core.network.monitor.NetworkMonitor
import io.core.network.monitor.NetworkStatus
import io.dev.relic.R
import io.dev.relic.feature.route.LocalNavHostController
import io.dev.relic.feature.route.MainFeatureNavHost
import io.dev.relic.feature.screens.main.widget.MainNavigationBarScaffold

/**
 * Main Screen entrance
 *
 * @param savedInstanceState
 * @param windowSizeClass
 * @param networkMonitor
 * @param mainScreenState
 * */
@Composable
fun MainScreen(
    savedInstanceState: Bundle?,
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    mainScreenState: MainScreenState = rememberMainScreenState(
        savedInstanceState = savedInstanceState,
        windowSizeClass = windowSizeClass,
        networkMonitor = networkMonitor
    )
) {
    /* ======================== Common ======================== */

    // Current host state of snackBar.
    val snackBarHostState by remember {
        mutableStateOf(SnackbarHostState())
    }

    /* ======================== Network ======================== */

    // Check the current network status by using networkMonitor flow.
    val networkStatus by mainScreenState.currentNetworkStatus
        .collectAsStateWithLifecycle(initialValue = NetworkStatus.AVAILABLE)

    val noNetworkMessage = stringResource(id = R.string.no_network_connection_message)

    LaunchedEffect(networkStatus) {
        if (networkStatus != NetworkStatus.AVAILABLE) {
            snackBarHostState.showSnackbar(
                message = noNetworkMessage,
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    /* ======================== Ui ======================== */

    val isShowBottomBar = mainScreenState.isShowBottomBar
            && (mainScreenState.currentTopLevelDestination != null)

    // Initialization the App main screen.
    MainNavigationBarScaffold(
        currentDestination = mainScreenState.currentDestination,
        destinations = mainScreenState.topLevelDestinations,
        onItemClick = { mainScreenState.navigateToTopLevelDestination(it) },
        isShowBottomBar = isShowBottomBar,
        isShowRailBar = mainScreenState.isShowRailBar,
        snackBarHostState = snackBarHostState
    ) { paddingValues ->
        MainFeatureNavHost(
            mainScreenState = mainScreenState,
            modifier = Modifier
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
                .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal))
        )
    }
}