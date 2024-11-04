package io.dev.relic.feature.function.gallery.widget

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.CommonAsyncImage
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColor
import io.core.ui.utils.RelicUiUtil
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
                val itemDecorationModifier = Modifier.padding(
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

@Composable
private fun GalleryGridItem(
    author: String,
    authorAvatarUrl: String,
    previewImageUrl: String,
    previewImageWidth: Int,
    previewImageHeight: Int,
    likeNumber: Int,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .clickable {
                    onItemClick.invoke()
                }
        ) {
            OnlineWallpaperCover(
                url = previewImageUrl,
                imageWidth = previewImageWidth,
                imageHeight = previewImageHeight
            )
            GalleryAuthorInfo(
                author = author,
                authorAvatarUrl = authorAvatarUrl,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
            GalleryLikesNumber(
                likeNumber = likeNumber,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }
}

@Composable
private fun GalleryLikesNumber(
    likeNumber: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .wrapContentSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(io.core.ui.R.drawable.ic_like),
            contentDescription = DEFAULT_DESC,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = "$likeNumber",
            style = TextStyle(
                color = mainTextColor,
                fontSize = 12.sp,
                fontFamily = ubuntu
            )
        )
    }
}

@Composable
private fun GalleryAuthorInfo(
    author: String,
    authorAvatarUrl: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = mainThemeColor.copy(alpha = 0.6F))
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommonAsyncImage(
            url = authorAvatarUrl,
            imageWidth = 24.dp,
            imageHeight = 24.dp,
            imageShape = CircleShape
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = author,
            style = TextStyle(
                color = mainTextColor,
                fontSize = 12.sp,
                fontFamily = ubuntu
            )
        )
    }
}