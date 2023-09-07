package io.dev.relic.feature.page.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.route.RelicRoute
import io.dev.relic.feature.screen.main.MainScreenState

fun NavController.navigateToHomePage(navOptions: NavOptions? = null) {
    this.navigate(
        route = RelicRoute.HOME,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.pageHome(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel
) {
    composable(route = RelicRoute.HOME) {
        HomePageRoute(
            mainScreenState = mainScreenState,
            mainViewModel = mainViewModel
        )
    }
}