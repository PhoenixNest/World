package io.dev.relic.feature.screen.mine

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import io.dev.relic.feature.route.RelicRoute.MineUnit.graphMine
import io.dev.relic.feature.route.RelicRoute.MineUnit.routeMineScreen
import io.dev.relic.global.RelicConstants.GlobalLogTags.GLOBAL_TAG_NAV_CONTROLLER
import io.dev.relic.global.utils.LogUtil

fun NavController.navigateToMineScreen(navOptions: NavOptions? = null) {
    LogUtil.debug(GLOBAL_TAG_NAV_CONTROLLER, "[Destination] -> MineScreen")
    this.navigate(
        route = routeMineScreen,
        navOptions = navOptions
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.navMineGraph(
    onBackClick: () -> Unit
) {
    navigation(
        route = graphMine,
        startDestination = routeMineScreen
    ) {
        composable(route = routeMineScreen) {
            MineScreenRoute(
                onBackClick = onBackClick
            )
        }
    }
}