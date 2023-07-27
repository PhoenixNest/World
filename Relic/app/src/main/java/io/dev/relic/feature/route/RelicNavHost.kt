package io.dev.relic.feature.route

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import io.dev.relic.feature.common.setting.navSettingScreen
import io.dev.relic.feature.common.setting.navigateToSettingScreen
import io.dev.relic.feature.route.RelicRoute.HomeUnit.graphHome
import io.dev.relic.feature.screen.main.MainScreenState
import io.dev.relic.feature.screen.main.navHiveGraph
import io.dev.relic.feature.screen.main.navHomeGraph
import io.dev.relic.feature.screen.mine.navMineGraph

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainFeatureNavHost(
    mainScreenState: MainScreenState,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = graphHome
) {
    AnimatedNavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = 350)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = 350)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = 350)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = 350)
            )
        }
    ) {
        navHomeGraph(
            onNavigateToSubscribePage = {},
            onNavigateToSettingPage = navHostController::navigateToSettingScreen,
            onNavigateToWeatherDetailPage = {},
            onNavigateToFoodRecipesDetailPage = {}
        )

        navHiveGraph(
            onNavigateToMineScreen = {},
        )

        navMineGraph(
            onBackClick = navHostController::popBackStack
        )

        navSettingScreen(onBackClick = navHostController::popBackStack)
    }
}