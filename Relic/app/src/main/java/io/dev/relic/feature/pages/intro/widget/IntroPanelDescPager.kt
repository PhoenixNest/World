package io.dev.relic.feature.pages.intro.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroPanelDescPager(
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {

    val state = rememberPagerState(
        pageCount = {
            10
        }
    )

    HorizontalPager(
        state = state,
        modifier = modifier.navigationBarsPadding(),
        contentPadding = PaddingValues(horizontal = 12.dp),
        pageSpacing = 8.dp
    ) { index ->
        Card(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .graphicsLayer {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val absoluteOffset = ((state.currentPage - index) + state.currentPageOffsetFraction)
                    val pageOffset = absoluteOffset.absoluteValue

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            Box(
                Modifier
                    .size(96.dp)
                    .background(color = Color.Red)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
private fun IntroPageDescPagerPreview() {
    IntroPanelDescPager(pagerState = rememberPagerState { 5 })
}
