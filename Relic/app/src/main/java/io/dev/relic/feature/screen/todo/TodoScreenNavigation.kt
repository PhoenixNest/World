package io.dev.relic.feature.screen.todo

import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.squareup.moshi.Moshi
import io.dev.relic.domain.model.todo.TodoDataModel
import io.dev.relic.domain.nav_type.TodoDataNavType
import io.dev.relic.feature.route.MainFeatureRoute.TodoUnit.graphTodo
import io.dev.relic.feature.route.MainFeatureRoute.TodoUnit.routeAddAndUpdateTodoPage
import io.dev.relic.feature.route.MainFeatureRoute.TodoUnit.routeTodoScreen
import io.dev.relic.feature.screen.todo.subpage.add_and_update.AddAndUpdateTodoPageRoute
import io.dev.relic.global.RelicConstants.GlobalLogTags.GLOBAL_TAG_NAV_CONTROLLER
import io.dev.relic.global.utils.LogUtil

internal const val todoDataArg: String = "todoDataArg"

fun NavController.navigateToTodoScreen(navOptions: NavOptions? = null) {
    LogUtil.debug(GLOBAL_TAG_NAV_CONTROLLER, "[Navigate destination]: TodoScreen")
    this.navigate(
        route = routeTodoScreen,
        navOptions = navOptions
    )
}

fun NavController.navigateToAddAndUpdateTodoScreen(
    args: TodoDataModel?,
    navOptions: NavOptions? = null
) {
    LogUtil.debug(GLOBAL_TAG_NAV_CONTROLLER, "[Navigate destination]: AddAndUpdateTodoScreen, args: $args")

    val routeArgs: String = Moshi.Builder()
        .build()
        .adapter(TodoDataModel::class.java)
        .toJson(args)

    this.navigate(
        route = "${routeAddAndUpdateTodoPage}/${Uri.encode(routeArgs)}",
        navOptions = navOptions
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.navTodoGraph(
    onBackClick: () -> Unit,
    onItemClick: (TodoDataModel) -> Unit,
    onCreateClick: () -> Unit
) {
    navigation(
        route = graphTodo,
        startDestination = routeTodoScreen
    ) {
        composable(route = routeTodoScreen) {
            TodoScreenRoute(
                onBackClick = onBackClick,
                onItemClick = onItemClick,
                onCreateClick = onCreateClick
            )
        }
        composable(
            route = "${routeAddAndUpdateTodoPage}/{$todoDataArg}",
            arguments = listOf(
                navArgument(name = todoDataArg) {
                    type = TodoDataNavType()
                }
            )
        ) { navBackStackEntry: NavBackStackEntry ->
            val todoDataModel: TodoDataModel = navBackStackEntry.arguments?.getParcelable(
                /* key = */ todoDataArg
            ) ?: TodoDataModel.emptyModel()

            AddAndUpdateTodoPageRoute(
                todoDataModel = todoDataModel,
                onBackClick = onBackClick,
                onFinishClick = {}
            )
        }
    }
}