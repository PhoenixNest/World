package io.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import io.module.common.RelicConstants
import io.ui.theme.placeHolderHighlightColor

@Composable
fun CommonAsyncImage(
    url: String?,
    imageWidth: Dp,
    imageHeight: Dp,
    modifier: Modifier = Modifier,
    lottieViewWidth: Dp = 72.dp,
    lottieViewHeight: Dp = 72.dp
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .build(),
        contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
        modifier = modifier
            .width(imageWidth)
            .height(imageHeight),
        contentScale = ContentScale.Crop
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .placeholder(
                        visible = true,
                        color = Color.DarkGray,
                        shape = RoundedCornerShape(16.dp),
                        highlight = PlaceholderHighlight.shimmer(highlightColor = placeHolderHighlightColor)
                    )
            )

            is AsyncImagePainter.State.Empty,
            is AsyncImagePainter.State.Error -> CommonNoDataComponent(
                modifier = Modifier.fillMaxSize(),
                backgroundColor = Color.DarkGray,
                iconSizeModifier = Modifier
                    .width(lottieViewWidth)
                    .height(lottieViewHeight),
                isShowText = false
            )

            is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
        }
    }
}