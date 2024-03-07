package io.dev.relic.feature.pages.studio.ui.bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.utils.RelicUiUtil
import io.data.model.news.NewsArticleModel
import io.dev.relic.feature.function.news.TopHeadlineNewsDataState
import io.dev.relic.feature.function.news.TrendingNewsDataState
import io.dev.relic.feature.pages.studio.ui.bottom_sheet.widget.StudioNewsTitle
import io.dev.relic.feature.pages.studio.ui.bottom_sheet.widget.StudioTabBar
import io.dev.relic.feature.pages.studio.ui.bottom_sheet.widget.StudioTopHeadlineNewsList
import io.dev.relic.feature.pages.studio.ui.bottom_sheet.widget.StudioTrendingNewsList

@Composable
fun StudioBottomSheet(
    trendingNewsDataState: TrendingNewsDataState,
    topHeadlineNewsDataState: TopHeadlineNewsDataState,
    currentSelectedCategory: Int,
    trendingNewsLazyListState: LazyListState,
    topHeadlineNewsTabLazyListState: LazyListState,
    topHeadlineNewsContentLazyListState: LazyListState,
    onResortClick: () -> Unit,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    onNewsCardClick: (model: NewsArticleModel) -> Unit,
    onLikeClick: (model: NewsArticleModel) -> Unit,
    onShareClick: (model: NewsArticleModel) -> Unit,
    onRetryTrendingClick: () -> Unit,
    onRetryTopHeadlineClick: () -> Unit
) {

    val screenHeight = RelicUiUtil.getCurrentScreenHeightDp()
    val bottomSheetHeight = screenHeight - 52.dp

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(bottomSheetHeight),
        state = topHeadlineNewsContentLazyListState,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        StudioNewsTitle(onResortClick = onResortClick)
        StudioTrendingNewsList(
            dataState = trendingNewsDataState,
            lazyListState = trendingNewsLazyListState,
            onCardClick = onNewsCardClick,
            onRetryClick = onRetryTrendingClick
        )
        StudioTabBar(
            currentSelectedTab = currentSelectedCategory,
            lazyListState = topHeadlineNewsTabLazyListState,
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

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF282C34)
private fun StudioBottomSheetPreview() {
    StudioBottomSheet(
        trendingNewsDataState = TrendingNewsDataState.Init,
        topHeadlineNewsDataState = TopHeadlineNewsDataState.Init,
        currentSelectedCategory = 0,
        trendingNewsLazyListState = rememberLazyListState(),
        topHeadlineNewsTabLazyListState = rememberLazyListState(),
        topHeadlineNewsContentLazyListState = rememberLazyListState(),
        onResortClick = {},
        onTabItemClick = { _, _ -> },
        onNewsCardClick = {},
        onLikeClick = {},
        onShareClick = {},
        onRetryTrendingClick = { },
        onRetryTopHeadlineClick = {}
    )
}