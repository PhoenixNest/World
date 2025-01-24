package io.dev.relic.feature.pages.todo

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.dev.relic.feature.route.RelicRoute.TODO_TASK
import io.dev.relic.feature.screens.main.MainScreenState

fun NavController.navigateToTodoPage(navOptions: NavOptions? = null) {
    this.navigate(
        route = TODO_TASK,
        navOptions = navOptions
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.pageTodo(
    mainScreenState: MainScreenState,
    sharedTransitionScope: SharedTransitionScope,
    onBackClick: () -> Unit
) {
    composable(route = TODO_TASK) {
        TodoPageRoute(
            mainScreenState = mainScreenState,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this,
            onBackClick = onBackClick
        )
    }
}