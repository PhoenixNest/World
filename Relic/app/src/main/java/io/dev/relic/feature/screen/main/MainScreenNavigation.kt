package io.dev.relic.feature.screen.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import io.dev.relic.feature.route.MainFeatureRoute.HiveUnit.graphHive
import io.dev.relic.feature.route.MainFeatureRoute.HiveUnit.routeHivePage
import io.dev.relic.feature.route.MainFeatureRoute.HomeUnit.graphHome
import io.dev.relic.feature.route.MainFeatureRoute.HomeUnit.routeHomePage
import io.dev.relic.feature.screen.main.sub_page.hive.HivePageRoute
import io.dev.relic.feature.screen.main.sub_page.home.HomePageRoute
import io.dev.relic.global.RelicConstants.GlobalLogTags.GLOBAL_TAG_NAV_CONTROLLER
import io.dev.relic.global.utils.LogUtil

fun NavController.navigateToHomePage(navOptions: NavOptions? = null) {
    LogUtil.debug(GLOBAL_TAG_NAV_CONTROLLER, "[Navigate destination]: HomePage")
    this.navigate(
        route = graphHome,
        navOptions = navOptions
    )
}

fun NavController.navigateToHivePage(navOptions: NavOptions? = null) {
    LogUtil.debug(GLOBAL_TAG_NAV_CONTROLLER, "[Navigate destination]: HivePage")
    this.navigate(
        route = graphHive,
        navOptions = navOptions
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.navHomeGraph(
    onNavigateToSubscribePage: () -> Unit,
    onNavigateToSettingPage: () -> Unit,
    onNavigateToCreateTodoPage: () -> Unit,
    onNavigateToWeatherDetailPage: () -> Unit,
    onNavigateToFoodRecipesDetailPage: () -> Unit
) {
    navigation(
        route = graphHome,
        startDestination = routeHomePage
    ) {
        composable(route = routeHomePage) {
            HomePageRoute(
                onNavigateToSubscribePage = onNavigateToSubscribePage,
                onNavigateToSettingPage = onNavigateToSettingPage,
                onNavigateToCreateTodoPage = onNavigateToCreateTodoPage,
                onNavigateToWeatherDetailPage = onNavigateToWeatherDetailPage,
                onNavigateToFoodRecipesDetailPage = onNavigateToFoodRecipesDetailPage
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.navHiveGraph(
    onNavigateToMine: () -> Unit,
    onNavigateToTodoEdit: () -> Unit,
    onTodoClick: () -> Unit
) {
    navigation(
        route = graphHive,
        startDestination = routeHivePage
    ) {
        composable(route = routeHivePage) {
            HivePageRoute(
                onNavigateToMine = onNavigateToMine,
                onNavigateToTodo = onNavigateToTodoEdit,
                onTodoClick = onTodoClick
            )
        }
    }
}