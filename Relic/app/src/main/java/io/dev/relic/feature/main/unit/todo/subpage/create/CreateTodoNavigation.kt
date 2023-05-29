package io.dev.relic.feature.main.unit.todo.subpage.create

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import io.dev.relic.feature.main.route.MainFeatureRoute.TodoUnit.routeCreateTodoPage

fun NavController.navigateToCreateTodoPage(navOptions: NavOptions? = null) {
    this.navigate(
        route = routeCreateTodoPage,
        navOptions = navOptions
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.navCreateTodoPage(
    onBackClick: () -> Unit,
    onFinishClick: () -> Unit
) {
    composable(route = routeCreateTodoPage) {
        CreateTodoPageRoute(
            onBackClick = onBackClick,
            onFinishClick = onFinishClick
        )
    }
}