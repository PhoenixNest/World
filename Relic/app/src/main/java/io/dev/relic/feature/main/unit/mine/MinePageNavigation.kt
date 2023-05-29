package io.dev.relic.feature.main.unit.mine

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import io.dev.relic.feature.main.route.MainFeatureRoute.MineUnit.graphMine
import io.dev.relic.feature.main.route.MainFeatureRoute.MineUnit.routeMinePage

fun NavController.navigateToMineUnit(navOptions: NavOptions? = null) {
    this.navigate(
        route = graphMine,
        navOptions = navOptions
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.navMineGraph() {
    navigation(
        route = graphMine,
        startDestination = routeMinePage
    ) {
        composable(route = routeMinePage) {
            MinePageRoute()
        }
    }
}
