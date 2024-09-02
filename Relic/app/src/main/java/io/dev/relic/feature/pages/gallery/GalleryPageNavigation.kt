package io.dev.relic.feature.pages.gallery

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

fun NavGraphBuilder.pageGallery(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    galleryViewModel: GalleryViewModel
) {
    composable(route = GALLERY) {
        GalleryPageRoute(
            mainScreenState = mainScreenState,
            mainViewModel = mainViewModel,
            galleryViewModel = galleryViewModel
        )
    }
}