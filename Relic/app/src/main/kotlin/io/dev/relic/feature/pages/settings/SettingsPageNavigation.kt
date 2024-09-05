package io.dev.relic.feature.pages.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.route.RelicRoute.SETTINGS
import io.dev.relic.feature.screens.main.MainScreenState

fun NavController.navigateToSettingsPage(navOptions: NavOptions? = null) {
    this.navigate(
        route = SETTINGS,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.pageSettings(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    onBackClick: () -> Unit
) {
    composable(route = SETTINGS) {
        SettingsPageRoute(
            mainScreenState = mainScreenState,
            mainViewModel = mainViewModel,
            onBackClick = onBackClick
        )
    }
}