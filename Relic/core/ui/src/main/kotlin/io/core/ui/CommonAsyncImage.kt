package io.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.mainBackgroundColor
import io.core.ui.theme.placeHolderHighlightColor

@Composable
fun CommonAsyncImage(
    url: String?,
    imageWidth: Dp,
    imageHeight: Dp,
    modifier: Modifier = Modifier,
    imageRadius: Dp = 0.dp,
    imageShape: Shape = RoundedCornerShape(imageRadius),
    contentScale: ContentScale = ContentScale.Crop
) {
    Surface(
        modifier = modifier
            .width(imageWidth)
            .height(imageHeight),
        shape = imageShape,
        color = Color.Transparent
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .build(),
            contentDescription = DEFAULT_DESC,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .placeholder(
                            visible = true,
                            color = mainBackgroundColor,
                            highlight = PlaceholderHighlight.shimmer(highlightColor = placeHolderHighlightColor)
                        )
                )

                is AsyncImagePainter.State.Empty,
                is AsyncImagePainter.State.Error -> {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(imageRadius),
                        color = Color.Transparent
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.no_data_placeholder),
                            contentDescription = DEFAULT_DESC,
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
            }
        }
    }
}