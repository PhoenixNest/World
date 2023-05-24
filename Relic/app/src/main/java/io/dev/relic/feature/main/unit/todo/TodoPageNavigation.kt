package io.dev.relic.feature.main.unit.todo

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import io.dev.relic.feature.main.route.MainFeatureRoute.TodoUnit.graphTodo
import io.dev.relic.feature.main.route.MainFeatureRoute.TodoUnit.routeTodoPage

fun NavController.navigateToTodoPage(navOptions: NavOptions? = null) {
    this.navigate(
        route = graphTodo,
        navOptions = navOptions
    )
}

@OptIn(ExperimentalAnimationApi::class)
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