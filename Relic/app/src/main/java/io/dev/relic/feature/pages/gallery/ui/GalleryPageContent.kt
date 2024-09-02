package io.dev.relic.feature.pages.gallery.ui

import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.dev.relic.feature.function.gallery.GalleryDataState
import io.dev.relic.feature.function.gallery.widget.GalleryStaggeredGrid
import io.dev.relic.feature.pages.gallery.GalleryAction
import io.dev.relic.feature.pages.gallery.GalleryListState
import io.dev.relic.feature.pages.gallery.GalleryState

@Composable
fun GalleryPageContent(galleryState: GalleryState) {
    GalleryStaggeredGrid(
        galleryDataState = galleryState.dataState,
        lazyStaggeredGridState = galleryState.listState.stagedGridState
    )
}

@Preview
@Composable
private fun GalleryPageContentPreview() {
    GalleryPageContent(
        GalleryState(
            dataState = GalleryDataState.Init,
            action = GalleryAction(
                onItemClick = {},
                onRetryClick = {}
            ),
            listState = GalleryListState(stagedGridState = rememberLazyStaggeredGridState())
        )
    )
}