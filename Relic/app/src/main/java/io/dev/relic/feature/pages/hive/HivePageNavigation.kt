package io.dev.relic.feature.pages.hive

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.route.RelicRoute.HIVE
import io.dev.relic.feature.screens.main.MainScreenState

fun NavController.navigateToHivePage(navOptions: NavOptions? = null) {
    this.navigate(
        route = HIVE,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.pageHive(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel
) {
    composable(route = HIVE) {
        HivePageRoute(
            mainScreenState = mainScreenState,
            mainViewModel = mainViewModel
        )
    }
}