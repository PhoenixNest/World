package io.dev.relic.feature.pages.gallery

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.navOptions
import androidx.paging.compose.collectAsLazyPagingItems
import io.dev.relic.feature.function.gallery.vm.GalleryViewModel
import io.dev.relic.feature.function.gallery.widget.GalleryStaggeredGridAction
import io.dev.relic.feature.pages.detail.gallery.navigateToGalleryDetailPage
import io.dev.relic.feature.pages.gallery.ui.GalleryPageContent
import io.dev.relic.feature.screens.main.MainScreenState
import io.domain.use_case.pixabay.action.util.WallpaperOrientation

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GalleryPageRoute(
    mainScreenState: MainScreenState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    galleryViewModel: GalleryViewModel = hiltViewModel()
) {

    /* ======================== Common ======================== */

    val navController = mainScreenState.navHostController

    /* ======================== Field ======================== */

    val galleryPagingItems = galleryViewModel.getGalleryPager(
        imageOrientation = WallpaperOrientation.VERTICAL
    ).collectAsLazyPagingItems()

    /* ======================== Ui ======================== */

    val galleryStaggeredGridAction = GalleryStaggeredGridAction(
        pagingItems = galleryPagingItems,
        lazyStaggeredGridState = rememberLazyStaggeredGridState(),
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        onItemClick = { model ->
            navController.navigateToGalleryDetailPage(
                id = model.id,
                originalImageUrl = model.originalImageUrl,
                author = model.author,
                authorAvatarUrl = model.authorAvatarUrl,
                authorPageUrl = model.authorPageUrl,
                navOptions = navOptions {
                    launchSingleTop = true
                    restoreState = true
                }
            )
        }
    )

    /* ======================== Ui State ======================== */

    GalleryPage(galleryStaggeredGridAction = galleryStaggeredGridAction)
}

@Composable
private fun GalleryPage(
    galleryStaggeredGridAction: GalleryStaggeredGridAction
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        GalleryPageContent(
            galleryStaggeredGridAction = galleryStaggeredGridAction
        )
    }
}