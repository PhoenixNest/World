package io.dev.relic.feature.pages.hive.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.core.ui.theme.mainThemeColor
import io.data.model.news.NewsArticleModel
import io.dev.relic.feature.function.news.TrendingNewsDataState
import io.dev.relic.feature.function.news.TopHeadlineNewsDataState
import io.dev.relic.feature.pages.hive.ui.widget.HiveNewsTitle
import io.dev.relic.feature.pages.hive.ui.widget.HiveTabBar
import io.dev.relic.feature.pages.hive.ui.widget.HiveTopHeadlineNewsList
import io.dev.relic.feature.pages.hive.ui.widget.HiveTrendingNewsList

@Composable
fun HivePageContent(
    trendingNewsDataState: TrendingNewsDataState,
    topHeadlineNewsDataState: TopHeadlineNewsDataState,
    currentSelectedCategory: Int,
    trendingNewsLazyListState: LazyListState,
    topHeadlineNewsTabLazyListState: LazyListState,
    topHeadlineNewsContentLazyListState: LazyListState,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    onNewsCardClick: (model: NewsArticleModel) -> Unit,
    onLikeClick: (model: NewsArticleModel) -> Unit,
    onShareClick: (model: NewsArticleModel) -> Unit,
    onRetryTrendingClick: () -> Unit,
    onRetryTopHeadlineClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mainThemeColor
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            state = topHeadlineNewsContentLazyListState,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            HiveNewsTitle()
            item { Spacer(modifier = Modifier.height(16.dp)) }
            HiveTrendingNewsList(
                dataState = trendingNewsDataState,
                lazyListState = trendingNewsLazyListState,
                onCardClick = onNewsCardClick,
                onRetryClick = onRetryTrendingClick
            )
            item { Spacer(modifier = Modifier.height(16.dp)) }
            HiveTabBar(
                currentSelectedTab = currentSelectedCategory,
                lazyListState = topHeadlineNewsTabLazyListState,
                onTabItemClick = onTabItemClick
            )
            item { Spacer(modifier = Modifier.height(16.dp)) }
            HiveTopHeadlineNewsList(
                dataState = topHeadlineNewsDataState,
                onCardClick = onNewsCardClick,
                onLikeClick = onLikeClick,
                onShareClick = onShareClick,
                onRetryClick = onRetryTopHeadlineClick
            )
        }
    }
}
