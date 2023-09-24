package io.dev.relic.global.widget

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import io.dev.relic.global.RelicConstants

@Composable
fun CommonAsyncImage(
    url: String?,
    lottieViewWidth: Dp = 72.dp,
    lottieViewHeight: Dp = 72.dp
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .build(),
        contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> CommonLoadingComponent(
                modifier = Modifier.fillMaxSize(),
                iconSizeModifier = Modifier
                    .width(lottieViewWidth)
                    .height(lottieViewHeight)
            )

            is AsyncImagePainter.State.Empty,
            is AsyncImagePainter.State.Error -> CommonNoDataComponent(
                modifier = Modifier.fillMaxSize(),
                iconSizeModifier = Modifier
                    .width(lottieViewWidth)
                    .height(lottieViewHeight),
                isShowText = false
            )

            is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
        }
    }
}