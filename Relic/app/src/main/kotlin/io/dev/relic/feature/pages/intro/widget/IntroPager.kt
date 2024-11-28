package io.dev.relic.feature.pages.intro.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.dev.relic.feature.pages.intro.IntroPagerModel

@Composable
fun IntroPager(
    pagerState: PagerState,
    pagerResList: List<IntroPagerModel>,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 12.dp),
        pageSpacing = 8.dp
    ) { index ->
        val pagerItem = pagerResList[index]
        IntroPagerItem(
            pagerDrawableResId = pagerItem.pagerDrawableResId,
            pagerTitleStrResId = pagerItem.pagerTitleStrResId,
            pagerDescStrResId = pagerItem.pagerDescStrResId
        )
    }
}

@Composable
private fun IntroPagerItem(
    @DrawableRes pagerDrawableResId: Int,
    @StringRes pagerTitleStrResId: Int,
    @StringRes pagerDescStrResId: Int
) {
    Image(
        painter = painterResource(pagerDrawableResId),
        contentDescription = DEFAULT_DESC,
        contentScale = ContentScale.Crop,
        alignment = Alignment.BottomCenter
    )
}