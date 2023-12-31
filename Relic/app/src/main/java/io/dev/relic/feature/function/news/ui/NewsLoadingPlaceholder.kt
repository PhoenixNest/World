package io.dev.relic.feature.function.news.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import io.core.ui.theme.mainBackgroundColor
import io.core.ui.theme.placeHolderHighlightColor

@Composable
fun NewsLoadingPlaceholder(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = mainBackgroundColor.copy(alpha = 0.3F),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .placeholder(
                    visible = true,
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(16.dp),
                    highlight = PlaceholderHighlight.shimmer(highlightColor = placeHolderHighlightColor)
                )
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun NewsLoadingPlaceholderPreview() {
    NewsLoadingPlaceholder()
}