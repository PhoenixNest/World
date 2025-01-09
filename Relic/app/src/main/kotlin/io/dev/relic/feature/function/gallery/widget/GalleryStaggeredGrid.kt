package io.dev.relic.feature.function.gallery.widget

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.core.ui.RelicUiUtil
import io.data.model.pixabay.PixabayDataModel
import io.dev.relic.feature.function.gallery.GalleryDataState

private const val DEFAULT_AUTHOR = "Unknown Artist"
private const val DEFAULT_AUTHOR_AVATAR_PLACEHOLDER_URL = ""
private const val DEFAULT_PREVIEW_IMAGE_PLACEHOLDER_URL = ""

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GalleryStaggeredGrid(
    galleryDataState: GalleryDataState,
    lazyStaggeredGridState: LazyStaggeredGridState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onItemClick: (model: PixabayDataModel) -> Unit
) {
    when (galleryDataState) {
        is GalleryDataState.Init,
        is GalleryDataState.Fetching -> {
            //
        }

        is GalleryDataState.Empty,
        is GalleryDataState.NoImageData,
        is GalleryDataState.FetchFailed -> {
            //
        }

        is GalleryDataState.FetchSucceed -> {
            GalleryStaggeredGrid(
                imageList = galleryDataState.modelList,
                lazyStaggeredGridState = lazyStaggeredGridState,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope,
                onItemClick = onItemClick
            )
        }

        is GalleryDataState.FetchMoreFailed -> {
            GalleryStaggeredGrid(
                imageList = galleryDataState.cacheModelList,
                lazyStaggeredGridState = lazyStaggeredGridState,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope,
                onItemClick = onItemClick
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun GalleryStaggeredGrid(
    imageList: List<PixabayDataModel?>,
    lazyStaggeredGridState: LazyStaggeredGridState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onItemClick: (model: PixabayDataModel) -> Unit
) {
    val screenWidthDp = RelicUiUtil.getCurrentScreenWidthDp()
    val galleryItemSize = (screenWidthDp / 2) - 16.dp

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(galleryItemSize),
        modifier = Modifier.fillMaxSize(),
        state = lazyStaggeredGridState,
        contentPadding = PaddingValues(4.dp),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        itemsIndexed(imageList) { index, data ->
            data?.apply {
                val itemDecorationModifier = Modifier
                    .padding(
                        top = if (index < 2) 24.dp else 0.dp,
                        bottom = if (index == imageList.size - 1) 100.dp else 0.dp
                    )
                with(sharedTransitionScope) {
                    GalleryGridItem(
                        author = author ?: DEFAULT_AUTHOR,
                        authorAvatarUrl = authorAvatarUrl ?: DEFAULT_AUTHOR_AVATAR_PLACEHOLDER_URL,
                        previewImageUrl = originalImageUrl ?: DEFAULT_PREVIEW_IMAGE_PLACEHOLDER_URL,
                        previewImageWidth = originalImageWidth ?: 0,
                        previewImageHeight = (originalImageHeight ?: 0) / 6,
                        likeNumber = likes ?: 0,
                        onItemClick = {
                            onItemClick.invoke(data)
                        },
                        modifier = itemDecorationModifier.sharedBounds(
                            sharedContentState = sharedTransitionScope.rememberSharedContentState("image$id"),
                            animatedVisibilityScope = animatedContentScope
                        )
                    )
                }
            }
        }
    }
}