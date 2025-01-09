package io.dev.relic.feature.pages.gallery

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.dev.relic.feature.route.RelicRoute.GALLERY
import io.dev.relic.feature.screens.main.MainScreenState

fun NavController.navigateToGalleryPage(navOptions: NavOptions? = null) {
    this.navigate(
        route = GALLERY,
        navOptions = navOptions
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.pageGallery(
    mainScreenState: MainScreenState,
    sharedTransitionScope: SharedTransitionScope
) {
    composable(route = GALLERY) {
        val animatedContentScope = this
        GalleryPageRoute(
            mainScreenState = mainScreenState,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = animatedContentScope
        )
    }
}