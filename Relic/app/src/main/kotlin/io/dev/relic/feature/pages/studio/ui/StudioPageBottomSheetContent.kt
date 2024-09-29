package io.dev.relic.feature.pages.studio.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.theme.mainThemeColor
import io.core.ui.utils.RelicUiUtil
import io.data.model.news.NewsArticleModel
import io.dev.relic.feature.function.news.TopHeadlineNewsDataState
import io.dev.relic.feature.function.news.TrendingNewsDataState
import io.dev.relic.feature.pages.studio.StudioBottomSheetState
import io.dev.relic.feature.pages.studio.StudioNewsListState
import io.dev.relic.feature.pages.studio.ui.bottom_sheet.StudioNewsTabBar
import io.dev.relic.feature.pages.studio.ui.bottom_sheet.StudioNewsTitle
import io.dev.relic.feature.pages.studio.ui.bottom_sheet.StudioTopHeadlineNewsList
import io.dev.relic.feature.pages.studio.ui.bottom_sheet.StudioTrendingNewsList

@Composable
fun StudioPageBottomSheetContent(
    bottomSheetState: StudioBottomSheetState,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    onNewsCardClick: (model: NewsArticleModel) -> Unit,
    onLikeClick: (model: NewsArticleModel) -> Unit,
    onShareClick: (model: NewsArticleModel) -> Unit,
    onRetryTrendingClick: () -> Unit,
    onRetryTopHeadlineClick: () -> Unit,
    onScrollToTopClick: () -> Unit
) {
    val screenHeight = RelicUiUtil.getCurrentScreenHeightDp()
    val studioPageTopBarHeight = 64.dp
    val bottomSheetHeight = screenHeight - studioPageTopBarHeight

    bottomSheetState.apply {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(bottomSheetHeight)
                .background(color = mainThemeColor),
            state = listState.topHeadlineNewsListState,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            StudioNewsTitle(modifier = Modifier.background(color = mainThemeColor))
            StudioTrendingNewsList(
                dataState = trendingNewsDataState,
                lazyListState = listState.trendingNewsListState,
                onCardClick = onNewsCardClick,
                onRetryClick = onRetryTrendingClick,
            )
            item { Spacer(modifier = Modifier.height(8.dp)) }
            StudioNewsTabBar(
                currentSelectedTab = currentSelectTab,
                lazyListState = listState.topHeadlineNewsTabListState,
                onTabItemClick = onTabItemClick,
                modifier = Modifier.background(color = mainThemeColor)
            )
            item { Spacer(modifier = Modifier.height(8.dp)) }
            StudioTopHeadlineNewsList(
                dataState = topHeadlineNewsDataState,
                onCardClick = onNewsCardClick,
                onLikeClick = onLikeClick,
                onShareClick = onShareClick,
                onRetryClick = onRetryTopHeadlineClick,
                onScrollToTopClick = onScrollToTopClick
            )
        }
    }
}

@Composable
@Preview
private fun StudioBottomSheetContentPreview() {
    StudioPageBottomSheetContent(
        bottomSheetState = StudioBottomSheetState(
            currentSelectTab = 0,
            trendingNewsDataState = TrendingNewsDataState.Init,
            topHeadlineNewsDataState = TopHeadlineNewsDataState.Init,
            listState = StudioNewsListState(
                trendingNewsListState = rememberLazyListState(),
                topHeadlineNewsTabListState = rememberLazyListState(),
                topHeadlineNewsListState = rememberLazyListState()
            )
        ),
        onTabItemClick = { _, _ -> },
        onNewsCardClick = {},
        onLikeClick = {},
        onShareClick = {},
        onRetryTrendingClick = {},
        onRetryTopHeadlineClick = {},
        onScrollToTopClick = {}
    )
}