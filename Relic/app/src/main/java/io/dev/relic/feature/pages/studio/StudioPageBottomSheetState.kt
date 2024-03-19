package io.dev.relic.feature.pages.studio

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.dev.relic.feature.function.news.TopHeadlineNewsDataState
import io.dev.relic.feature.function.news.TrendingNewsDataState

@Composable
fun rememberStudioNewsListState(
    trendingNewsListState: LazyListState = rememberLazyListState(),
    topHeadlineNewsTabListState: LazyListState = rememberLazyListState(),
    topHeadlineNewsListState: LazyListState = rememberLazyListState()
): StudioNewsListState {
    return remember(
        keys = arrayOf(
            trendingNewsListState,
            topHeadlineNewsListState
        )
    ) {
        StudioNewsListState(
            trendingNewsListState = trendingNewsListState,
            topHeadlineNewsTabListState = topHeadlineNewsTabListState,
            topHeadlineNewsListState = topHeadlineNewsListState
        )
    }
}

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