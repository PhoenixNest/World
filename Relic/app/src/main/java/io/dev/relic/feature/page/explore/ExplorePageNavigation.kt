package io.dev.relic.feature.page.explore

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.route.RelicRoute
import io.dev.relic.feature.screen.main.MainScreenState

fun NavController.navigateToExplorePage(navOptions: NavOptions? = null) {
    this.navigate(
        route = RelicRoute.EXPLORE,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.pageExplore(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel
) {
    composable(route = RelicRoute.EXPLORE) {
        ExplorePageRoute(
            mainScreenState = mainScreenState,
            mainViewModel = mainViewModel
        )
    }
}