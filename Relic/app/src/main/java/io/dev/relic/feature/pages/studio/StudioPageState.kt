package io.dev.relic.feature.pages.studio

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.dev.relic.feature.function.news.TopHeadlineNewsDataState
import io.dev.relic.feature.function.news.TrendingNewsDataState

@Composable
fun rememberStudioPageState(
    bottomSheetScaffoldState: StudioPageBottomSheetScaffoldState
): StudioPageState {
    return remember(keys = arrayOf(bottomSheetScaffoldState)) {
        StudioPageState(bottomSheetScaffoldState)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberStudioPageBottomSheetState(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    newsDataState: StudioPageNewsDataState,
    newsListState: StudioPageNewsListState
): StudioPageBottomSheetScaffoldState {
    return remember(keys = arrayOf(bottomSheetScaffoldState)) {
        StudioPageBottomSheetScaffoldState(
            bottomSheetScaffoldState = bottomSheetScaffoldState,
            newsDataState = newsDataState,
            newsListState = newsListState
        )
    }
}

@Composable
fun rememberStudioPageNewsListState(
    trendingNewsListState: LazyListState = rememberLazyListState(),
    topHeadlineNewsListState: LazyListState = rememberLazyListState()
): StudioPageNewsListState {
    return remember(
        keys = arrayOf(
            trendingNewsListState,
            topHeadlineNewsListState
        )
    ) {
        StudioPageNewsListState(
            trendingNewsListState = trendingNewsListState,
            topHeadlineNewsListState = topHeadlineNewsListState
        )
    }
}

data class StudioPageState(
    val bottomSheetScaffoldState: StudioPageBottomSheetScaffoldState,
)

@OptIn(ExperimentalMaterialApi::class)
data class StudioPageBottomSheetScaffoldState(
    val bottomSheetScaffoldState: BottomSheetScaffoldState,
    val newsDataState: StudioPageNewsDataState,
    val newsListState: StudioPageNewsListState
)

data class StudioPageNewsDataState(
    val trendingNewsDataState: TrendingNewsDataState,
    val topHeadlineNewsDataState: TopHeadlineNewsDataState
)

data class StudioPageNewsListState(
    val trendingNewsListState: LazyListState,
    val topHeadlineNewsListState: LazyListState
)