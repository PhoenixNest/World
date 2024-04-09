package io.dev.relic.feature.function.wallpaper

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.google.accompanist.placeholder.material.placeholder
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.mainBackgroundColor
import io.dev.relic.R

@Composable
fun OnlineWallpaperCover(
    url: String,
    modifier: Modifier = Modifier
) {
    WallpaperCover(
        url = url,
        modifier = modifier
    )
}

@Composable
fun LocalWallpaperCover(
    @DrawableRes resId: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = DEFAULT_DESC,
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.FillHeight
    )
}

@Composable
private fun WallpaperCover(
    url: String,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = DEFAULT_DESC,
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .placeholder(
                        visible = true,
                        color = mainBackgroundColor,
                    )
            )

            is AsyncImagePainter.State.Empty,
            is AsyncImagePainter.State.Error -> {
                Image(
                    painter = painterResource(id = R.mipmap.home),
                    contentDescription = DEFAULT_DESC,
                    contentScale = ContentScale.Crop
                )
            }

            is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
        }
    }
}