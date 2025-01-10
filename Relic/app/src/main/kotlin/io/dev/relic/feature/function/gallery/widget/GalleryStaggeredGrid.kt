package io.dev.relic.feature.function.gallery.widget

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import io.data.model.pixabay.PixabayDataModel

private const val DEFAULT_AUTHOR = "Unknown Artist"
private const val DEFAULT_AUTHOR_AVATAR_PLACEHOLDER_URL = ""
private const val DEFAULT_PREVIEW_IMAGE_PLACEHOLDER_URL = ""

@OptIn(ExperimentalSharedTransitionApi::class)
data class GalleryStaggeredGridAction(
    val pagingItems: LazyPagingItems<PixabayDataModel>,
    @Stable val lazyStaggeredGridState: LazyStaggeredGridState,
    @Stable val sharedTransitionScope: SharedTransitionScope,
    @Stable val animatedContentScope: AnimatedContentScope,
    @Stable val onItemClick: (model: PixabayDataModel) -> Unit
)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GalleryStaggeredGrid(action: GalleryStaggeredGridAction) {
    val pagingItems = action.pagingItems
    val loadState = pagingItems.loadState
    when (loadState.refresh) {
        is LoadState.Loading -> {

        }

        is LoadState.Error -> {

        }

        is LoadState.NotLoading -> {
            GalleryStaggeredGridContent(
                galleryPagingItems = pagingItems,
                lazyStaggeredGridState = action.lazyStaggeredGridState,
                sharedTransitionScope = action.sharedTransitionScope,
                animatedContentScope = action.animatedContentScope,
                onItemClick = action.onItemClick
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun GalleryStaggeredGridContent(
    galleryPagingItems: LazyPagingItems<PixabayDataModel>,
    lazyStaggeredGridState: LazyStaggeredGridState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onItemClick: (model: PixabayDataModel) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        state = lazyStaggeredGridState,
        contentPadding = PaddingValues(4.dp),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(galleryPagingItems.itemCount) { index ->
            val data = galleryPagingItems[index] ?: return@items
            with(sharedTransitionScope) {
                GalleryGridItem(
                    author = data.author ?: DEFAULT_AUTHOR,
                    authorAvatarUrl = data.authorAvatarUrl ?: DEFAULT_AUTHOR_AVATAR_PLACEHOLDER_URL,
                    previewImageUrl = data.originalImageUrl ?: DEFAULT_PREVIEW_IMAGE_PLACEHOLDER_URL,
                    previewImageWidth = data.originalImageWidth ?: 0,
                    previewImageHeight = (data.originalImageHeight ?: 0) / 6,
                    likeNumber = data.likes ?: 0,
                    onItemClick = { onItemClick.invoke(data) },
                    modifier = Modifier.sharedBounds(
                        sharedContentState = sharedTransitionScope.rememberSharedContentState("image$index"),
                        animatedVisibilityScope = animatedContentScope
                    )
                )
            }
        }
    }
}