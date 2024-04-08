package io.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import io.core.ui.theme.mainBackgroundColor
import io.core.ui.theme.placeHolderHighlightColor

@Composable
fun CommonLoadingPlaceholder(
    isVertical: Boolean = true,
    itemCount: Int = 3
) {
    if (isVertical) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.Start
        ) {
            repeat(itemCount) {
                CommonLoadingCardItem()
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Start
            ),
            verticalAlignment = Alignment.Top
        ) {
            repeat(itemCount) {
                CommonLoadingCardItem()
            }
        }
    }
}

@Composable
private fun CommonLoadingCardItem(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        color = mainBackgroundColor.copy(alpha = 0.3F)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = mainBackgroundColor.copy(alpha = 0.3F))
                .padding(20.dp)
        ) {
            CommonLoadingIntroItem()
            Spacer(modifier = Modifier.height(18.dp))
            CommonLoadingDescItem()
        }
    }
}

@Composable
private fun CommonLoadingIntroItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .width(96.dp)
                .height(96.dp)
                .placeholder(
                    visible = true,
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(16.dp),
                    highlight = PlaceholderHighlight.shimmer(highlightColor = placeHolderHighlightColor)
                )
        )
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
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
private fun CommonLoadingDescItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
            .placeholder(
                visible = true,
                color = Color.DarkGray,
                shape = RoundedCornerShape(16.dp),
                highlight = PlaceholderHighlight.shimmer(highlightColor = placeHolderHighlightColor)
            )
    )
}

@Composable
@Preview
private fun CommonLoadingCardItemPreview() {
    CommonLoadingCardItem()
}