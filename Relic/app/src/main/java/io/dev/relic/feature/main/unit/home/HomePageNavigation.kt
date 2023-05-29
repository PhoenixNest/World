package io.dev.relic.feature.main.unit.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.navigation
import com.google.accompanist.navigation.animation.composable
import io.dev.relic.feature.main.route.MainFeatureRoute.HomeUnit.graphHome
import io.dev.relic.feature.main.route.MainFeatureRoute.HomeUnit.routeHomePage

fun NavController.navigateToHomeUnit(navOptions: NavOptions? = null) {
    this.navigate(
        route = graphHome,
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