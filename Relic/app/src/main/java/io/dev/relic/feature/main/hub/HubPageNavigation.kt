package io.dev.relic.feature.main.hub

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import io.dev.relic.core.route.RelicAppRoute.HubUnit.graphHub
import io.dev.relic.core.route.RelicAppRoute.HubUnit.routeHubPage

fun NavController.navigateToHubUnit(navOptions: NavOptions? = null) {
    this.navigate(
        route = graphHub,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.navHubGraph(
    onNavigate: () -> Unit,
    onBackClick: () -> Unit
) {
    navigation(
        route = graphHub,
        startDestination = routeHubPage
    ) {
        composable(route = routeHubPage) {
            HubPageRoute { onNavigate() }
        }
    }
}