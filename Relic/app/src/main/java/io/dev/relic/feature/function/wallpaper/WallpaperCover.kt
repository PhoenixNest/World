package io.dev.relic.feature.function.wallpaper

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
@Preview
private fun WallpaperCoverPreview() {
    WallpaperCover(imageUrl = "https://cdn.dribbble.com/users/78703/screenshots/4813446/attachments/1081953/weather_full.png")
}