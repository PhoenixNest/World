package io.dev.relic.feature.pages.hive

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.common.RelicShareCenter.shareWebLink
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.news.NewsUnitConfig.DEFAULT_INIT_NEWS_PAGE_INDEX
import io.dev.relic.feature.function.news.NewsUnitConfig.DEFAULT_INIT_NEWS_PAGE_SIZE
import io.dev.relic.feature.function.news.NewsUnitConfig.TopHeadline.DEFAULT_NEWS_CATEGORY
import io.dev.relic.feature.function.news.NewsUnitConfig.TopHeadline.DEFAULT_NEWS_COUNTRY_TYPE
import io.dev.relic.feature.pages.detail.news.navigateToNewsDetailPage
import io.dev.relic.feature.pages.hive.ui.HivePageContent
import io.dev.relic.feature.pages.hive.viewmodel.HiveViewModel
import io.dev.relic.feature.screens.main.MainScreenState

@Composable
fun HivePageRoute(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    hiveViewModel: HiveViewModel = hiltViewModel()
) {

    /* ======================== Common ======================== */

    val context = LocalContext.current

    /* ======================== Field ======================== */

    // Trending
    val trendingNewsDataState by hiveViewModel.trendingNewsDataStateFlow.collectAsStateWithLifecycle()

    // Top-headline
    val topHeadlineNewsDataState by hiveViewModel.topHeadlineNewsDataStateFlow.collectAsStateWithLifecycle()

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

    HivePageContent(
        trendingNewsDataState = trendingNewsDataState,
        topHeadlineNewsDataState = topHeadlineNewsDataState,
        currentSelectedCategory = hiveViewModel.getSelectedTopHeadlineNewsCategoriesTab(),
        trendingNewsLazyListState = trendingNewsLazyListState,
        topHeadlineNewsTabLazyListState = topHeadlineNewsTabLazyListState,
        topHeadlineNewsContentLazyListState = topHeadlineNewsContentLazyListState,
        onTabItemClick = { currentSelectedTab, keyWords ->
            hiveViewModel.apply {
                updateSelectedTopHeadlineCategoriesTab(currentSelectedTab)
                getTopHeadlineNewsData(
                    keyWords = keyWords,
                    country = DEFAULT_NEWS_COUNTRY_TYPE,
                    category = DEFAULT_NEWS_CATEGORY,
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
        onRetryTrendingClick = hiveViewModel::getTrendingNewsData,
        onRetryTopHeadlineClick = hiveViewModel::getTopHeadlineNewsData
    )
}