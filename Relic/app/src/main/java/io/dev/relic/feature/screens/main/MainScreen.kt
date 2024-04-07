package io.dev.relic.feature.screens.main

import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.core.network.monitor.NetworkMonitor
import io.core.network.monitor.NetworkStatus
import io.core.ui.theme.mainThemeColor
import io.dev.relic.R
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.agent.gemini.viewmodel.GeminiAgentViewModel
import io.dev.relic.feature.function.food_recipes.viewmodel.FoodRecipesViewModel
import io.dev.relic.feature.function.news.viewmodel.NewsViewModel
import io.dev.relic.feature.function.todo.viewmodel.TodoViewModel
import io.dev.relic.feature.route.MainFeatureNavHost
import io.dev.relic.feature.screens.main.widget.MainBottomBar
import io.dev.relic.feature.screens.main.widget.MainDrawer
import io.dev.relic.feature.screens.main.widget.MainRailAppBar

/**
 * Main Screen entrance
 *
 * @param savedInstanceState
 * @param windowSizeClass
 * @param networkMonitor
 * @param mainViewModel                 Global ViewModel
 * @param geminiAgentViewModel          Provide the Ai Chat feature to Home page
 * @param foodRecipesViewModel          Provide the Food Recipes feature to Home page
 * @param todoViewModel                 Provide the todo feature to Studio page
 * @param newsViewModel                 Provide the News feature to Studio page
 * @param mainScreenState
 * */
@Composable
fun MainScreen(
    savedInstanceState: Bundle?,
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    mainViewModel: MainViewModel,
    geminiAgentViewModel: GeminiAgentViewModel,
    foodRecipesViewModel: FoodRecipesViewModel,
    todoViewModel: TodoViewModel,
    newsViewModel: NewsViewModel,
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

    /* ======================== Network ======================== */

    // Check the current network status by using networkMonitor flow.
    val networkStatus by networkMonitor.observe()
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

    /* ======================== Ui ======================== */

    // Initialization the App main screen.
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            if (mainScreenState.shouldShowRailBar) {
                MainRailAppBar(
                    destinations = mainScreenState.topLevelDestinations,
                    onNavigateToDestination = mainScreenState::navigateToTopLevelDestination,
                    currentDestination = mainScreenState.currentDestination
                )
            }

            ModalDrawer(
                drawerState = mainScreenState.drawerState,
                gesturesEnabled = mainScreenState.isEnableDrawerGesture,
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
                            navHostController = mainScreenState.navHostController,
                            mainViewModel = mainViewModel,
                            geminiAgentViewModel = geminiAgentViewModel,
                            foodRecipesViewModel = foodRecipesViewModel,
                            todoViewModel = todoViewModel,
                            newsViewModel = newsViewModel,
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