package io.dev.relic.feature.function.wallpaper

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import io.core.ui.CommonAsyncImage

@Composable
fun WallpaperCover(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    CommonAsyncImage(
        url = imageUrl,
        imageWidth = screenWidth,
        imageHeight = screenHeight,
        imageShape = RectangleShape
    )
}