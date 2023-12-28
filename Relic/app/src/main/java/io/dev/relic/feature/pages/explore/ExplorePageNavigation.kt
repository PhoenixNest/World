package io.dev.relic.feature.pages.explore

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.route.RelicRoute.EXPLORE
import io.dev.relic.feature.screens.main.MainScreenState

fun NavController.navigateToExplorePage(navOptions: NavOptions? = null) {
    this.navigate(
        route = EXPLORE,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.pageExplore(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel
) {
    composable(route = EXPLORE) {
        ExplorePageRoute(
            mainScreenState = mainScreenState,
            mainViewModel = mainViewModel
        )
    }
}