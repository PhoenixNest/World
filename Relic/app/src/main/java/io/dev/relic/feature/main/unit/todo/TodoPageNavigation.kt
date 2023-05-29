package io.dev.relic.feature.main.unit.todo

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import io.dev.relic.feature.main.route.MainFeatureRoute.TodoUnit.routeTodoPage

fun NavController.navigateToTodoPage(navOptions: NavOptions? = null) {
    this.navigate(
        route = routeTodoPage,
        navOptions = navOptions
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.navTodoPage(
    onItemClick: () -> Unit,
    onBackClick: () -> Unit,
    onCreateClick: () -> Unit
) {
    composable(route = routeTodoPage) {
        TodoPageRoute(
            onBackClick = onBackClick,
            onItemClick = onItemClick,
            onCreateClick = onCreateClick
        )
    }
}