package io.dev.relic.feature.main.route

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import io.dev.relic.feature.main.route.MainFeatureRoute.HomeUnit.graphHome
import io.dev.relic.feature.main.unit.hive.navHiveGraph
import io.dev.relic.feature.main.unit.home.navHomeGraph
import io.dev.relic.feature.main.unit.todo.navigateToTodoPage

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainFeatureNavHost(
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
            onNavigateToTodoPage = navHostController::navigateToTodoPage,
            onNavigateToWeatherDetailPage = {},
            onNavigateToFoodRecipesDetailPage = {},
            onBackClick = navHostController::popBackStack
        )
        navHiveGraph(
            onNavigate = {},
            onBackClick = navHostController::popBackStack
        )
    }
}