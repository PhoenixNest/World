package io.dev.relic.feature.pages.intro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.utils.RelicUiUtil.getCurrentScreenWidthDp
import io.dev.relic.feature.pages.intro.widget.IntroPager
import io.dev.relic.feature.pages.intro.widget.IntroPanel

@Composable
fun IntroPageLarge(onNavigateClick: () -> Unit) {

    val screenWidth = getCurrentScreenWidthDp()
    val leftPanelWidth = screenWidth / 3
    val rightPanelWidth = screenWidth - leftPanelWidth - 16.dp

    val pagerState = rememberPagerState { 5 }

    Box(modifier = Modifier.fillMaxSize()) {
        IntroPanel(
            isLargeMode = true,
            onClick = onNavigateClick,
            modifier = Modifier
                .width(leftPanelWidth)
                .align(Alignment.BottomStart)
        )
        IntroPager(
            pagerState = pagerState,
            pagerResList = IntroPagerModel.defaultPagerList(),
            modifier = Modifier
                .width(rightPanelWidth)
                .height(520.dp)
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
@Preview(device = "spec:width=673dp,height=841dp,orientation=landscape")
private fun IntroPageLargePreview() {
    IntroPageLarge(onNavigateClick = {})
}
