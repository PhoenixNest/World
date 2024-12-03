package io.dev.relic.feature.pages.studio.ui.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.dev.relic.R
import io.dev.relic.feature.pages.studio.util.StudioWorkFlowType
import kotlin.math.absoluteValue

@Composable
fun StudioWorkFlowPager(
    pagerState: PagerState,
    workFlowList: List<StudioWorkFlowType>,
    onItemClick: () -> Unit
) {
    HorizontalPager(
        state = pagerState,
        snapPosition = SnapPosition.Center
    ) { pageIndex ->
        val pageOffset =
            ((pagerState.currentPage - pageIndex)
                    + pagerState.currentPageOffsetFraction).absoluteValue

        println("pageOffset: $pageOffset")

        val workFlowType = workFlowList[pageIndex]
        StudioWorkFlowCard(
            label = workFlowType.labelResId,
            backgroundResId = workFlowType.backgroundResId,
            onClick = onItemClick,
            modifier = Modifier.graphicsLayer {
                //
            }
        )
    }
}

@Composable
private fun StudioWorkFlowCard(
    @StringRes label: Int,
    @DrawableRes backgroundResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val aspectRatio = 9F / 16F

    Card(
        onClick = onClick,
        modifier = modifier
            .width(240.dp)
            .aspectRatio(aspectRatio),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box {
            Image(
                painter = painterResource(backgroundResId),
                contentDescription = DEFAULT_DESC,
                modifier = Modifier
                    .width(240.dp)
                    .aspectRatio(aspectRatio),
                contentScale = ContentScale.Crop,
                alignment = Alignment.BottomCenter
            )
            Box(
                modifier = Modifier
                    .width(240.dp)
                    .height(52.dp)
                    .background(color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8F))
                    .padding(horizontal = 12.dp)
                    .align(Alignment.BottomCenter),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(text = stringResource(label))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun StudioWorkFlowCardPreview() {
    StudioWorkFlowCard(
        label = R.string.studio_label,
        backgroundResId = R.mipmap.night,
        onClick = {}
    )
}