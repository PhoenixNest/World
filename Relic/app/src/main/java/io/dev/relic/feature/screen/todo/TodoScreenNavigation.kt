package io.dev.relic.feature.screen.todo

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import io.dev.relic.feature.route.MainFeatureRoute.TodoUnit.graphTodo
import io.dev.relic.feature.route.MainFeatureRoute.TodoUnit.routeAddAndUpdateTodoPage
import io.dev.relic.feature.route.MainFeatureRoute.TodoUnit.routeTodoScreen
import io.dev.relic.feature.screen.todo.subpage.add_and_update.AddAndUpdateTodoPageRoute

fun NavController.navigateToTodoScreen(navOptions: NavOptions? = null) {
    this.navigate(
        route = routeTodoScreen,
        navOptions = navOptions
    )
}

fun NavController.navigateToAddAndUpdateTodoScreen(
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = routeAddAndUpdateTodoPage,
        navOptions = navOptions
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.navTodoGraph(
    onItemClick: () -> Unit,
    onBackClick: () -> Unit,
    onCreateClick: () -> Unit
) {
    navigation(
        startDestination = routeTodoScreen,
        route = graphTodo
    ) {
        composable(route = routeTodoScreen) {
            TodoScreenRoute(
                onBackClick = onBackClick,
                onItemClick = {},
                onCreateClick = onCreateClick
            )
        }
        composable(route = routeAddAndUpdateTodoPage) {
            AddAndUpdateTodoPageRoute(
                onBackClick = onBackClick,
                onFinishClick = {}
            )
        }
    }
}