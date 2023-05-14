package io.dev.relic.core.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import io.dev.relic.core.route.RelicAppRoute.HomeUnit.graphHome
import io.dev.relic.feature.main.home.navHomeGraph
import io.dev.relic.feature.main.hub.navHubGraph

@Composable
fun RelicNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = graphHome
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        navHomeGraph(
            onNavigate = {},
            onBackClick = navHostController::popBackStack
        )
        navHubGraph(
            onNavigate = {},
            onBackClick = navHostController::popBackStack
        )
    }
}