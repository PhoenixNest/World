package io.dev.relic.feature.pages.intro.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroPanelDescPager(
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier.navigationBarsPadding()
    ) { index ->
        //
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
private fun IntroPageDescPagerPreview() {
    IntroPanelDescPager(pagerState = rememberPagerState { 5 })
}
