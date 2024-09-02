package io.dev.relic.feature.function.gallery.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import io.core.ui.CommonAsyncImage
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColor
import io.core.ui.utils.RelicUiUtil
import io.data.model.wallpaper.WallpaperImagesDataModel
import io.dev.relic.feature.function.gallery.GalleryDataState

private const val DEFAULT_AUTHOR = "Unknown Artist"
private const val DEFAULT_AUTHOR_AVATAR_PLACEHOLDER_URL = ""
private const val DEFAULT_PREVIEW_IMAGE_PLACEHOLDER_URL = ""

@Composable
fun GalleryStaggeredGrid(
    galleryDataState: GalleryDataState,
    lazyStaggeredGridState: LazyStaggeredGridState
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
                lazyStaggeredGridState = lazyStaggeredGridState
            )
        }
    }
}

@Composable
private fun GalleryStaggeredGrid(
    imageList: List<WallpaperImagesDataModel?>,
    lazyStaggeredGridState: LazyStaggeredGridState
) {
    val screenWidthDp = RelicUiUtil.getCurrentScreenWidthDp()
    val galleryItemSize = (screenWidthDp / 2) - 16.dp

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(galleryItemSize),
        modifier = Modifier.fillMaxSize(),
        state = lazyStaggeredGridState,
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(imageList) { dataModel ->
            dataModel?.apply {
                GalleryGridItem(
                    author = author ?: DEFAULT_AUTHOR,
                    authorAvatarUrl = authorAvatarUrl ?: DEFAULT_AUTHOR_AVATAR_PLACEHOLDER_URL,
                    previewImageUrl = largeImageUrl ?: DEFAULT_PREVIEW_IMAGE_PLACEHOLDER_URL,
                    previewImageWidth = originalImageWidth ?: 0,
                    previewImageHeight = originalImageHeight ?: 0
                )
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
    previewImageHeight: Int
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(12.dp),
        color = Color.Transparent
    ) {
        Box(modifier = Modifier.wrapContentSize()) {
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
        }
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
            .wrapContentHeight()
            .background(color = mainThemeColor.copy(alpha = 0.6F))
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommonAsyncImage(
            url = authorAvatarUrl,
            imageWidth = 32.dp,
            imageHeight = 32.dp,
            imageShape = CircleShape
        )
        Text(
            text = author,
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )
    }
}