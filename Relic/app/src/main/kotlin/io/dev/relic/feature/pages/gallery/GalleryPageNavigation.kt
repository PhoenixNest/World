package io.dev.relic.feature.pages.gallery

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.gallery.viewmodel.GalleryViewModel
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
    mainViewModel: MainViewModel,
    galleryViewModel: GalleryViewModel,
    sharedTransitionScope: SharedTransitionScope,
    onBackClick: () -> Unit
) {
    composable(
        route = GALLERY,
        enterTransition = {
            fadeIn()
        },
        exitTransition = {
            fadeOut()
        },
        popEnterTransition = {
            fadeIn()
        },
        popExitTransition = {
            fadeOut()
        }
    ) {
        val animatedContentScope = this
        GalleryPageRoute(
            mainScreenState = mainScreenState,
            mainViewModel = mainViewModel,
            galleryViewModel = galleryViewModel,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = animatedContentScope,
            onBackClick = onBackClick
        )
    }
}