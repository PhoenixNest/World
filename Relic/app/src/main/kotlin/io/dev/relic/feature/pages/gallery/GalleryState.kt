package io.dev.relic.feature.pages.gallery

import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import io.data.model.wallpaper.WallpaperImagesDataModel
import io.dev.relic.feature.function.gallery.GalleryDataState

data class GalleryState(
    val dataState: GalleryDataState,
    val action: GalleryAction,
    val listState: GalleryListState
)

data class GalleryAction(
    val onItemClick: (dataModel: WallpaperImagesDataModel) -> Unit,
    val onRetryClick: () -> Unit
)

data class GalleryListState(
    val stagedGridState: LazyStaggeredGridState
)