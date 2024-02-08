package io.dev.relic.feature.pages.home

import androidx.compose.material.DrawerState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.agent.gemini.viewmodel.GeminiAgentViewModel
import io.dev.relic.feature.route.RelicRoute.HOME
import io.dev.relic.feature.screens.main.MainScreenState

fun NavController.navigateToHomePage(navOptions: NavOptions? = null) {
    this.navigate(
        route = HOME,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.pageHome(
    mainScreenState: MainScreenState,
    drawerState: DrawerState,
    mainViewModel: MainViewModel,
    geminiAgentViewModel: GeminiAgentViewModel
) {
    composable(route = HOME) {
        HomePageRoute(
            mainScreenState = mainScreenState,
            drawerState = drawerState,
            mainViewModel = mainViewModel,
            geminiAgentViewModel = geminiAgentViewModel
        )
    }
}