package io.dev.relic.feature.main.unit.todo.subpage.add_and_update

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import io.dev.relic.feature.main.route.MainFeatureRoute.TodoUnit.routeAddAndUpdateTodoPage

fun NavController.navigateToAddAndUpdateTodoPage(navOptions: NavOptions? = null) {
    this.navigate(
        route = routeAddAndUpdateTodoPage,
        navOptions = navOptions
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.navAddAndUpdateTodoPage(
    onBackClick: () -> Unit,
    onFinishClick: () -> Unit
) {
    composable(route = routeAddAndUpdateTodoPage) {
        AddAndUpdateTodoPageRoute(
            onBackClick = onBackClick,
            onFinishClick = onFinishClick
        )
    }
}