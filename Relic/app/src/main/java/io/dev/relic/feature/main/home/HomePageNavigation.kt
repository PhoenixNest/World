package io.dev.relic.feature.main.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import io.dev.relic.core.route.RelicAppRoute.HomeUnit.graphHome
import io.dev.relic.core.route.RelicAppRoute.HomeUnit.routeHomePage

fun NavController.navigateToHomeUnit(navOptions: NavOptions? = null) {
    this.navigate(
        route = graphHome,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.navHomeGraph(
    onNavigate: () -> Unit,
    onBackClick: () -> Unit
) {
    navigation(
        route = graphHome,
        startDestination = routeHomePage
    ) {
        composable(route = routeHomePage) {
            HomePageRoute { onNavigate() }
        }
    }
}