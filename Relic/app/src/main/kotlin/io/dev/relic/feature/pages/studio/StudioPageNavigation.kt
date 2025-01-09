package io.dev.relic.feature.pages.studio

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.dev.relic.feature.route.RelicRoute.STUDIO
import io.dev.relic.feature.screens.main.MainScreenState

fun NavController.navigateToStudioPage(navOptions: NavOptions? = null) {
    this.navigate(
        route = STUDIO,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.pageStudio(mainScreenState: MainScreenState) {
    composable(route = STUDIO) {
        StudioPageRoute(mainScreenState = mainScreenState)
    }
}