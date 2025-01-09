package io.dev.relic.feature.pages.gallery.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import io.dev.relic.feature.function.gallery.widget.GalleryStaggeredGrid
import io.dev.relic.feature.pages.gallery.GalleryState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GalleryPageContent(
    galleryState: GalleryState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    GalleryStaggeredGrid(
        galleryDataState = galleryState.dataState,
        lazyStaggeredGridState = galleryState.listState.lazyStaggeredGridState,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        onItemClick = { dataModel ->
            galleryState.action.onItemClick.invoke(dataModel)
        }
    )
}