package io.dev.relic.feature.common.setting

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import io.dev.relic.feature.route.MainFeatureRoute.SettingUnit.routeSettingScreen

fun NavController.navigateToSettingScreen(navOptions: NavOptions? = null) {
    this.navigate(
        route = routeSettingScreen,
        navOptions = navOptions
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.navSettingScreen(
    onBackClick: () -> Unit
) {
    composable(route = routeSettingScreen) {
        SettingScreenRoute(
            onBackClick = onBackClick
        )
    }
}