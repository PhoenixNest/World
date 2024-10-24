package io.dev.relic.feature.pages.gallery

import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import io.data.model.pixabay.PixabayDataModel
import io.dev.relic.feature.function.gallery.GalleryDataState

data class GalleryState(
    val dataState: GalleryDataState,
    val action: GalleryAction,
    val listState: GalleryListState
)

data class GalleryAction(
    val onItemClick: (dataModel: PixabayDataModel) -> Unit,
    val onRetryClick: () -> Unit
)

data class GalleryListState(
    val stagedGridState: LazyStaggeredGridState
)