package io.dev.relic.feature.page.hive

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.route.RelicRoute

fun NavController.navigateToHivePage(navOptions: NavOptions? = null) {
    this.navigate(
        route = RelicRoute.HIVE,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.pageHive(mainViewModel: MainViewModel) {
    composable(route = RelicRoute.HIVE) {
        HivePageRoute(mainViewModel = mainViewModel)
    }
}