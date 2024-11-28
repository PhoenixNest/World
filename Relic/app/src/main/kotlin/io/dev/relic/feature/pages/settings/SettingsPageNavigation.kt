package io.dev.relic.feature.pages.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.dev.relic.feature.route.RelicRoute.SETTINGS

fun NavController.navigateToSettingsPage(navOptions: NavOptions? = null) {
    this.navigate(
        route = SETTINGS,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.pageSettings(
    onBackClick: () -> Unit
) {
    composable(route = SETTINGS) {
        SettingsPageRoute(onBackClick = onBackClick)
    }
}