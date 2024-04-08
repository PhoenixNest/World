package io.dev.relic.feature.pages.studio

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.common.RelicConstants.Common.EMPTY_STRING
import io.common.RelicShareCenter
import io.data.util.NewsCategory
import io.data.util.NewsConfig.DEFAULT_INIT_NEWS_PAGE_INDEX
import io.data.util.NewsConfig.DEFAULT_INIT_NEWS_PAGE_SIZE
import io.data.util.NewsConfig.TopHeadline.DEFAULT_NEWS_COUNTRY_TYPE
import io.dev.relic.feature.function.news.viewmodel.NewsViewModel
import io.dev.relic.feature.pages.detail.news.navigateToNewsDetailPage
import io.dev.relic.feature.pages.studio.ui.StudioPageBottomSheetContent
import io.dev.relic.feature.screens.main.MainScreenState
import kotlinx.coroutines.launch

@Composable
fun StudioPageBottomSheet(
    newsViewModel: NewsViewModel,
    mainScreenState: MainScreenState
) {

    /* ======================== Common ======================== */

    val context = LocalContext.current
    val navHostController = mainScreenState.navHostController
    val coroutineScope = mainScreenState.coroutineScope

    /* ======================== Field ======================== */

    // Trending
    val trendingNewsDataState by newsViewModel.trendingNewsDataStateFlow
        .collectAsStateWithLifecycle()

    // Top-headline
    val topHeadlineNewsDataState by newsViewModel.topHeadlineNewsDataStateFlow
        .collectAsStateWithLifecycle()

    /* ======================== Ui ======================== */

    // List state
    val newListState = StudioNewsListState(
        trendingNewsListState = rememberLazyListState(),
        topHeadlineNewsTabListState = rememberLazyListState(),
        topHeadlineNewsListState = rememberLazyListState()
    )

    // Data state
    val bottomSheetState = StudioBottomSheetState(
        currentSelectTab = newsViewModel.getSelectedNewsTab(),
        trendingNewsDataState = trendingNewsDataState,
        topHeadlineNewsDataState = topHeadlineNewsDataState,
        listState = newListState
    )

    StudioPageBottomSheetContent(
        bottomSheetState = bottomSheetState,
        onTabItemClick = { currentSelectedTab, _ ->
            newsViewModel.apply {
                updateSelectedTopHeadlineCategoriesTab(currentSelectedTab)
                getTopHeadlineNewsData(
                    keyWords = EMPTY_STRING,
                    country = DEFAULT_NEWS_COUNTRY_TYPE,
                    category = NewsCategory.entries[currentSelectedTab],
                    pageSize = DEFAULT_INIT_NEWS_PAGE_SIZE,
                    page = DEFAULT_INIT_NEWS_PAGE_INDEX
                )
                coroutineScope.launch {
                    newListState.topHeadlineNewsListState.scrollToItem(3)
                }
            }
        },
        onNewsCardClick = {
            navHostController.navigateToNewsDetailPage(
                title = it.title,
                contUrl = it.contentUrl
            )
        },
        onLikeClick = {
            //
        },
        onShareClick = {
            RelicShareCenter.shareWebLink(
                context = context,
                title = it.title,
                url = it.contentUrl
            )
        },
        onRetryTrendingClick = newsViewModel::getTrendingNewsData,
        onRetryTopHeadlineClick = newsViewModel::getTopHeadlineNewsData
    )
}