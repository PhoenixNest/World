package io.dev.relic.feature.pages.studio.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import io.core.ui.theme.RelicFontFamily.googleSans
import io.core.ui.theme.commonLoadingShimmerColor
import io.core.ui.theme.placeHolderHighlightColor
import io.core.ui.widget.CommonRetryComponent
import io.data.model.maxim.MaximModel
import io.dev.relic.feature.function.maxim.MaximDataState

data class StudioMaximWidgetAction(
    val state: MaximDataState,
    @Stable val onRefreshClick: () -> Unit,
    @Stable val onRetryClick: () -> Unit
)

@Composable
fun StudioMaximWidget(
    action: StudioMaximWidgetAction,
    maximTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    modifier: Modifier = Modifier
) {
    when (val state = action.state) {
        is MaximDataState.Init,
        is MaximDataState.Fetching -> {
            StudioMaximPlaceholder(modifier = modifier.height(100.dp))
        }

        is MaximDataState.FetchSucceed -> {
            StudioMaximWidget(
                model = state.data,
                maximTextColor = maximTextColor,
                onClick = action.onRefreshClick,
                modifier = modifier
            )
        }

        is MaximDataState.Empty,
        is MaximDataState.NoMaximData,
        is MaximDataState.FetchFailed -> {
            StudioMaximNoDataWidget(
                onRetryClick = action.onRetryClick,
                modifier = modifier.height(100.dp)
            )
        }
    }
}

@Composable
private fun StudioMaximPlaceholder(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .clip(RoundedCornerShape(32.dp))
                .placeholder(
                    visible = true,
                    color = commonLoadingShimmerColor,
                    highlight = PlaceholderHighlight.shimmer(highlightColor = placeHolderHighlightColor)
                )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1F)
                    .height(24.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .placeholder(
                        visible = true,
                        color = commonLoadingShimmerColor,
                        highlight = PlaceholderHighlight.shimmer(highlightColor = placeHolderHighlightColor)
                    )
            )
            Box(
                modifier = Modifier
                    .weight(1F)
                    .height(24.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .placeholder(
                        visible = true,
                        color = commonLoadingShimmerColor,
                        highlight = PlaceholderHighlight.shimmer(highlightColor = placeHolderHighlightColor)
                    )
            )
        }
    }
}

@Composable
private fun StudioMaximWidget(
    model: MaximModel,
    onClick: () -> Unit,
    maximTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.Transparent,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable { onClick.invoke() }
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = model.hitokoto ?: "---",
                color = maximTextColor,
                fontFamily = googleSans,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = model.from ?: "Unknown",
                    color = maximTextColor,
                    fontFamily = googleSans,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "•",
                    color = maximTextColor,
                    fontFamily = googleSans,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = (model.fromWho as? String) ?: "Unknown",
                    color = maximTextColor,
                    fontFamily = googleSans,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun StudioMaximNoDataWidget(
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CommonRetryComponent(
        onRetryClick = onRetryClick,
        modifier = modifier,
        backgroundColor = Color.LightGray.copy(alpha = 0.3F)
    )
}

@Composable
@Preview(showBackground = true)
private fun StudioMaximPlaceholderPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        StudioMaximPlaceholder(
            modifier = Modifier
                .width(300.dp)
                .height(72.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun StudioMaximWidgetPreview() {
    StudioMaximWidget(
        model = MaximModel(
            hitokoto = "羌笛何须怨杨柳，春风不度玉门关。",
            from = "凉州词二首·其一",
            fromWho = "王之涣",
            creator = "---",
            createdAt = "---"
        ),
        maximTextColor = Color.DarkGray,
        onClick = {}
    )
}

@Composable
@Preview(showBackground = true)
private fun StudioMaximNoDataWidgetPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        StudioMaximNoDataWidget(
            onRetryClick = {},
            modifier = Modifier
                .width(300.dp)
                .height(72.dp)
        )
    }
}