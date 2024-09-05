package io.dev.relic.feature.pages.intro

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.common.RelicConstants
import io.core.ui.theme.mainThemeColorAccent
import io.core.ui.utils.RelicUiUtil.getCurrentScreenWidthDp
import io.dev.relic.R
import io.dev.relic.feature.pages.intro.widget.IntroPanel
import io.dev.relic.feature.pages.intro.widget.IntroPanelDescPager

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroPageLarge(onNavigateClick: () -> Unit) {

    val screenWidth = getCurrentScreenWidthDp()
    val leftPanelWidth = screenWidth / 3
    val rightPanelWidth = screenWidth - leftPanelWidth - 16.dp

    val pagerState = rememberPagerState { 5 }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.mipmap.intro),
            contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        IntroPanel(
            isLargeMode = true,
            onClick = onNavigateClick,
            modifier = Modifier
                .width(leftPanelWidth)
                .align(Alignment.BottomStart)
        )
        IntroPanelDescPager(
            pagerState = pagerState,
            modifier = Modifier
                .width(rightPanelWidth)
                .height(520.dp)
                .align(Alignment.BottomEnd)
                .background(
                    color = mainThemeColorAccent.copy(alpha = 0.9F),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp
                    )
                )
        )
    }
}

@Composable
@Preview(device = "spec:width=673dp,height=841dp,orientation=landscape")
private fun IntroPageLargePreview() {
    IntroPageLarge(onNavigateClick = {})
}
