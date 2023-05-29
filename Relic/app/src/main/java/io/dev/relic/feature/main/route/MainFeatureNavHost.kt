package io.dev.relic.feature.main.route

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import io.dev.relic.feature.main.MainScreenState
import io.dev.relic.feature.main.route.MainFeatureRoute.HomeUnit.graphHome
import io.dev.relic.feature.main.unit.hive.navHiveGraph
import io.dev.relic.feature.main.unit.home.navHomeGraph
import io.dev.relic.feature.main.unit.mine.navMineGraph
import io.dev.relic.feature.main.unit.todo.navTodoPage
import io.dev.relic.feature.main.unit.todo.subpage.create.navCreateTodoPage
import io.dev.relic.feature.main.unit.todo.subpage.create.navigateToCreateTodoPage

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
        // Graph Unit
        navHomeGraph(
            onNavigateToSubscribePage = {},
            onNavigateToSettingPage = {},
            onNavigateToCreateTodoPage = navHostController::navigateToCreateTodoPage,
            onNavigateToWeatherDetailPage = {},
            onNavigateToFoodRecipesDetailPage = {}
        )
        navHiveGraph(
            onNavigate = {},
            onBackClick = navHostController::popBackStack
        )
        navMineGraph()

        // Page Unit
        navTodoPage(
            onItemClick = {},
            onBackClick = navHostController::popBackStack,
            onCreateClick = {}
        )
        navCreateTodoPage(
            onBackClick = navHostController::popBackStack,
            onFinishClick = {
                navHostController.apply {
                    popBackStack()
                    mainScreenState.navigateToTopLevelDestination(
                        topLevelDestination = MainFeatureTopLevelDestination.Mine
                    )
                }
            }
        )
    }
}