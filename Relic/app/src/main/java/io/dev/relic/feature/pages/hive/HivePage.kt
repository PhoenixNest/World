package io.dev.relic.feature.pages.hive

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.common.RelicShareCenter.shareWebLink
import io.data.model.news.NewsArticleModel
import io.data.util.NewsCategory
import io.data.util.NewsConfig.DEFAULT_INIT_NEWS_PAGE_INDEX
import io.data.util.NewsConfig.DEFAULT_INIT_NEWS_PAGE_SIZE
import io.data.util.NewsConfig.TopHeadline.DEFAULT_NEWS_COUNTRY_TYPE
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.news.TopHeadlineNewsDataState
import io.dev.relic.feature.function.news.TrendingNewsDataState
import io.dev.relic.feature.function.news.viewmodel.NewsViewModel
import io.dev.relic.feature.pages.detail.news.navigateToNewsDetailPage
import io.dev.relic.feature.pages.hive.ui.HivePageContent
import io.dev.relic.feature.screens.main.MainScreenState

@Composable
fun HivePageRoute(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    newsViewModel: NewsViewModel
) {

    /* ======================== Common ======================== */

    val context = LocalContext.current

    /* ======================== Field ======================== */

    // Trending
    val trendingNewsDataState by newsViewModel.trendingNewsDataStateFlow.collectAsStateWithLifecycle()

    // Top-headline
    val topHeadlineNewsDataState by newsViewModel.topHeadlineNewsDataStateFlow.collectAsStateWithLifecycle()

    /* ======================== Ui ======================== */

    val trendingNewsLazyListState = rememberLazyListState()
    val topHeadlineNewsTabLazyListState = rememberLazyListState()
    val topHeadlineNewsContentLazyListState = rememberLazyListState()

    LaunchedEffect(trendingNewsLazyListState) {
        //
    }

    LaunchedEffect(topHeadlineNewsContentLazyListState) {
        //
    }

    HivePage(
        trendingNewsDataState = trendingNewsDataState,
        topHeadlineNewsDataState = topHeadlineNewsDataState,
        currentSelectedCategory = newsViewModel.getSelectedTopHeadlineNewsCategoriesTab(),
        trendingNewsLazyListState = trendingNewsLazyListState,
        topHeadlineNewsTabLazyListState = topHeadlineNewsTabLazyListState,
        topHeadlineNewsContentLazyListState = topHeadlineNewsContentLazyListState,
        onResortClick = {
            //
        },
        onTabItemClick = { currentSelectedTab, _ ->
            val newsCategories = NewsCategory.entries
            newsViewModel.apply {
                updateSelectedTopHeadlineCategoriesTab(currentSelectedTab)
                getTopHeadlineNewsData(
                    keyWords = "",
                    country = DEFAULT_NEWS_COUNTRY_TYPE,
                    category = newsCategories[currentSelectedTab],
                    pageSize = DEFAULT_INIT_NEWS_PAGE_SIZE,
                    page = DEFAULT_INIT_NEWS_PAGE_INDEX
                )
            }
        },
        onNewsCardClick = {
            mainScreenState.navHostController.navigateToNewsDetailPage(
                title = it.title,
                contUrl = it.contentUrl
            )
        },
        onLikeClick = {
            //
        },
        onShareClick = {
            shareWebLink(
                context = context,
                title = it.title,
                url = it.contentUrl
            )
        },
        onRetryTrendingClick = newsViewModel::getTrendingNewsData,
        onRetryTopHeadlineClick = newsViewModel::getTopHeadlineNewsData
    )
}

@Composable
private fun HivePage(
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
    HivePageContent(
        trendingNewsDataState = trendingNewsDataState,
        topHeadlineNewsDataState = topHeadlineNewsDataState,
        currentSelectedCategory = currentSelectedCategory,
        trendingNewsLazyListState = trendingNewsLazyListState,
        topHeadlineNewsTabLazyListState = topHeadlineNewsTabLazyListState,
        topHeadlineNewsContentLazyListState = topHeadlineNewsContentLazyListState,
        onResortClick = onResortClick,
        onTabItemClick = onTabItemClick,
        onNewsCardClick = onNewsCardClick,
        onLikeClick = onLikeClick,
        onShareClick = onShareClick,
        onRetryTrendingClick = onRetryTrendingClick,
        onRetryTopHeadlineClick = onRetryTopHeadlineClick
    )
}

@Composable
@Preview
private fun HivePagePreview() {
    HivePage(
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