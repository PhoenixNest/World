package io.dev.relic.feature.function.gallery.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.CommonAsyncImage
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColor
import io.data.model.wallpaper.WallpaperImagesDataModel

private const val DEFAULT_AUTHOR = "Unknown Artist"
private const val DEFAULT_AUTHOR_AVATAR_PLACEHOLDER_URL = ""
private const val DEFAULT_PREVIEW_IMAGE_PLACEHOLDER_URL = ""

@Composable
fun WallpaperStaggeredGrid(
    imageList: List<WallpaperImagesDataModel>,
    wallpaperGridState: LazyStaggeredGridState
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(180.dp),
        modifier = Modifier.fillMaxSize(),
        state = wallpaperGridState,
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(imageList) { dataModel ->
            dataModel.apply {
                WallpaperGridItem(
                    author = author ?: DEFAULT_AUTHOR,
                    authorAvatarUrl = authorAvatarUrl ?: DEFAULT_AUTHOR_AVATAR_PLACEHOLDER_URL,
                    previewImageUrl = previewImageUrl ?: DEFAULT_PREVIEW_IMAGE_PLACEHOLDER_URL,
                    previewImageWidth = originalImageWidth ?: 0,
                    previewImageHeight = originalImageHeight ?: 0
                )
            }
        }
    }
}

@Composable
private fun WallpaperGridItem(
    author: String,
    authorAvatarUrl: String,
    previewImageUrl: String,
    previewImageWidth: Int,
    previewImageHeight: Int,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        OnlineWallpaperCover(
            url = previewImageUrl,
            imageWidth = previewImageWidth,
            imageHeight = previewImageHeight
        )
        WallpaperAuthorInfo(
            author = author,
            authorAvatarUrl = authorAvatarUrl,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun WallpaperAuthorInfo(
    author: String,
    authorAvatarUrl: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = mainThemeColor.copy(alpha = 0.3F))
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

@Preview
@Composable
private fun WallpaperGridItemPreview() {
    WallpaperGridItem(
        previewImageWidth = 180,
        previewImageHeight = 640,
        author = "Author",
        authorAvatarUrl = "",
        previewImageUrl = ""
    )
}