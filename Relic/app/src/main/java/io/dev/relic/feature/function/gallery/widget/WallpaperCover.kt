package io.dev.relic.feature.function.gallery.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.CommonLoadingComponent
import io.core.ui.R
import io.core.ui.utils.RelicUiUtil.convertPixelToDp

@Composable
fun OnlineWallpaperCover(
    url: String,
    imageWidth: Int,
    imageHeight: Int,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val density = context.resources.displayMetrics.density
    val imageWidthDp = convertPixelToDp(density, imageWidth)
    val imageHeightDp = convertPixelToDp(density, imageHeight)

    WallpaperCover(
        url = url,
        modifier = modifier
            .width(imageWidthDp)
            .height(imageHeightDp)
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
        modifier = modifier,
        contentScale = ContentScale.FillBounds
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
        modifier = modifier,
        contentScale = ContentScale.Crop
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                CommonLoadingComponent(iconSize = 72.dp)
            }

            is AsyncImagePainter.State.Empty,
            is AsyncImagePainter.State.Error -> {
                Image(
                    painter = painterResource(id = R.drawable.no_data_placeholder),
                    contentDescription = DEFAULT_DESC,
                    contentScale = ContentScale.Crop
                )
            }

            is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
        }
    }
}