package io.dev.relic.feature.main.unit.hive

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import io.dev.relic.feature.main.route.MainFeatureRoute.HiveUnit.graphHive
import io.dev.relic.feature.main.route.MainFeatureRoute.HiveUnit.routeHivePage

fun NavController.navigateToHiveUnit(navOptions: NavOptions? = null) {
    this.navigate(
        route = graphHive,
        navOptions = navOptions
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.navHiveGraph(
    onNavigate: () -> Unit,
    onBackClick: () -> Unit
) {
    navigation(
        route = graphHive,
        startDestination = routeHivePage
    ) {
        composable(route = routeHivePage) {
            HivePageRoute(onNavigate = onNavigate)
        }
    }
}