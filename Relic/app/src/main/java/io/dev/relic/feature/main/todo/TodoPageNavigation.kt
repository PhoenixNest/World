package io.dev.relic.feature.main.todo

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import io.dev.relic.core.route.RelicAppRoute.TodoUnit.graphTodo
import io.dev.relic.core.route.RelicAppRoute.TodoUnit.routeTodoPage

fun NavController.navigateToTodoPage(navOptions: NavOptions? = null) {
    this.navigate(
        route = graphTodo,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.navTodoGraph(
    onNavigate: () -> Unit,
    onBackClick: () -> Unit
) {
    navigation(
        route = graphTodo,
        startDestination = routeTodoPage
    ) {
        composable(route = routeTodoPage) {
            TodoPageRoute { onNavigate() }
        }
    }
}