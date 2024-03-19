package io.dev.relic.feature.pages.studio.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.utils.RelicUiUtil
import io.data.model.news.NewsArticleModel
import io.dev.relic.feature.function.news.TopHeadlineNewsDataState
import io.dev.relic.feature.function.news.TrendingNewsDataState
import io.dev.relic.feature.pages.studio.StudioBottomSheetState
import io.dev.relic.feature.pages.studio.rememberStudioNewsListState
import io.dev.relic.feature.pages.studio.ui.widget.StudioNewsTitle
import io.dev.relic.feature.pages.studio.ui.widget.StudioTabBar
import io.dev.relic.feature.pages.studio.ui.widget.StudioTopHeadlineNewsList
import io.dev.relic.feature.pages.studio.ui.widget.StudioTrendingNewsList

@Composable
fun StudioBottomSheet(
    bottomSheetState: StudioBottomSheetState,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    onNewsCardClick: (model: NewsArticleModel) -> Unit,
    onLikeClick: (model: NewsArticleModel) -> Unit,
    onShareClick: (model: NewsArticleModel) -> Unit,
    onRetryTrendingClick: () -> Unit,
    onRetryTopHeadlineClick: () -> Unit
) {
    val screenHeight = RelicUiUtil.getCurrentScreenHeightDp()
    val bottomSheetHeight = screenHeight - 52.dp

    bottomSheetState.apply {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(bottomSheetHeight),
            state = listState.topHeadlineNewsTabListState,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            StudioNewsTitle()
            StudioTrendingNewsList(
                dataState = trendingNewsDataState,
                lazyListState = listState.trendingNewsListState,
                onCardClick = onNewsCardClick,
                onRetryClick = onRetryTrendingClick
            )
            StudioTabBar(
                currentSelectedTab = currentSelectTab,
                lazyListState = listState.topHeadlineNewsTabListState,
                onTabItemClick = onTabItemClick
            )
            StudioTopHeadlineNewsList(
                dataState = topHeadlineNewsDataState,
                onCardClick = onNewsCardClick,
                onLikeClick = onLikeClick,
                onShareClick = onShareClick,
                onRetryClick = onRetryTopHeadlineClick
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF282C34)
private fun StudioBottomSheetPreview() {
    StudioBottomSheet(
        bottomSheetState = StudioBottomSheetState(
            currentSelectTab = 0,
            trendingNewsDataState = TrendingNewsDataState.Init,
            topHeadlineNewsDataState = TopHeadlineNewsDataState.Init,
            listState = rememberStudioNewsListState()
        ),
        onTabItemClick = { _, _ -> },
        onNewsCardClick = {},
        onLikeClick = {},
        onShareClick = {},
        onRetryTrendingClick = { },
        onRetryTopHeadlineClick = {}
    )
}