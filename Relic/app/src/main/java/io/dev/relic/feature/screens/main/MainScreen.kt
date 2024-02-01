package io.dev.relic.feature.screens.main

import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.common.util.LogUtil
import io.core.network.monitor.NetworkMonitor
import io.core.network.monitor.NetworkStatus
import io.core.ui.theme.mainThemeColor
import io.dev.relic.R
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.route.MainFeatureNavHost
import io.dev.relic.feature.screens.main.widget.MainBottomBar
import io.dev.relic.feature.screens.main.widget.MainDrawer
import io.dev.relic.feature.screens.main.widget.MainRailAppBar

@Composable
fun MainScreen(
    savedInstanceState: Bundle?,
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    mainViewModel: MainViewModel,
    mainScreenState: MainScreenState = rememberMainScreenState(
        savedInstanceState = savedInstanceState,
        windowSizeClass = windowSizeClass,
        networkMonitor = networkMonitor
    )
) {
    /* ======================== Common ======================== */

    // Current host state of snackBar.
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    // Check if the current can include the bottom bar.
    val isShowBottomBar = (mainScreenState.shouldShowBottomBar)
            && (mainScreenState.currentTopLevelDestination != null)

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed,
        confirmStateChange = { drawerValue ->
            LogUtil.d("Drawer", "[Drawer] State: ${drawerValue.name}")
            true
        }
    )

    /* ======================== Network ======================== */

    // Check the current network status by using networkMonitor flow.
    val networkStatus by networkMonitor.observe()
        .collectAsStateWithLifecycle(initialValue = NetworkStatus.Available)

    val noNetworkMessage = stringResource(id = R.string.no_network_connection_message)

    LaunchedEffect(networkStatus) {
        if (networkStatus != NetworkStatus.Available) {
            snackBarHostState.showSnackbar(
                message = noNetworkMessage,
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    /* ======================== Main State ======================== */

    // Location
    val mainState by mainViewModel.mainStateFlow.collectAsStateWithLifecycle()

    // Weather
    val weatherState by mainViewModel.weatherDataStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(mainState) {
        when (mainState) {
            is MainState.Init,
            is MainState.AccessingLocation -> {
                //
            }

            is MainState.Empty,
            is MainState.AccessLocationFailed -> {
                //
            }

            is MainState.AccessLocationSucceed -> {
                val state = (mainState as MainState.AccessLocationSucceed)
                state.location?.also {
                    mainViewModel.getWeatherData(it.latitude, it.longitude)
                }
            }
        }
    }

    // Initialization the App main screen.
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
            if (mainScreenState.shouldShowRailBar) {
                MainRailAppBar(
                    destinations = mainScreenState.topLevelDestinations,
                    onNavigateToDestination = mainScreenState::navigateToTopLevelDestination,
                    currentDestination = mainScreenState.currentDestination
                )
            }

            ModalDrawer(
                drawerState = drawerState,
                drawerContent = {
                    MainDrawer(
                        weatherDataState = weatherState,
                        onWeatherRetry = {
                            mainViewModel.latestLocation?.also {
                                mainViewModel.getWeatherData(it.latitude, it.longitude)
                            }
                        }
                    )
                },
                drawerBackgroundColor = mainThemeColor,
                content = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        MainFeatureNavHost(
                            mainScreenState = mainScreenState,
                            drawerState = drawerState,
                            navHostController = mainScreenState.navHostController,
                            mainViewModel = mainViewModel,
                            modifier = Modifier.fillMaxSize()
                        )
                        if (isShowBottomBar) {
                            MainBottomBar(
                                destinations = mainScreenState.topLevelDestinations,
                                onNavigateToDestination = mainScreenState::navigateToTopLevelDestination,
                                currentDestination = mainScreenState.currentDestination,
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }
                    }
                }
            )
        }
    }
}