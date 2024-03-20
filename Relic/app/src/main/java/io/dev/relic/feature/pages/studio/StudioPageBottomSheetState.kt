package io.dev.relic.feature.pages.studio

import androidx.compose.foundation.lazy.LazyListState
import io.dev.relic.feature.function.news.TopHeadlineNewsDataState
import io.dev.relic.feature.function.news.TrendingNewsDataState

data class StudioBottomSheetState(
    val currentSelectTab: Int,
    val trendingNewsDataState: TrendingNewsDataState,
    val topHeadlineNewsDataState: TopHeadlineNewsDataState,
    val listState: StudioNewsListState
)

data class StudioNewsListState(
    val trendingNewsListState: LazyListState,
    val topHeadlineNewsTabListState: LazyListState,
    val topHeadlineNewsListState: LazyListState
)