package io.core.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
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
import io.core.ui.R
import io.core.ui.theme.commonLoadingShimmerColor
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
    val context = LocalContext.current

    Surface(
        shape = imageShape,
        color = Color.Transparent
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context)
                .data(url)
                // .transformations(
                //     listOf(
                //         BlurTransformation(context)
                //     )
                // )
                .build(),
            contentDescription = DEFAULT_DESC,
            modifier = modifier
                .width(imageWidth)
                .height(imageHeight),
            contentScale = contentScale
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .placeholder(
                                visible = true,
                                color = commonLoadingShimmerColor,
                                highlight = PlaceholderHighlight.shimmer(highlightColor = placeHolderHighlightColor)
                            )
                    )
                }

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