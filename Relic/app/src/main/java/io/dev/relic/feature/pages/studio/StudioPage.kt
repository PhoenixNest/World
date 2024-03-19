package io.dev.relic.feature.pages.studio

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.common.RelicConstants.Common.EMPTY_STRING
import io.common.RelicShareCenter.shareWebLink
import io.core.ui.theme.mainThemeColor
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
import io.dev.relic.feature.pages.studio.ui.StudioBottomSheet
import io.dev.relic.feature.pages.studio.ui.StudioPageContent
import io.dev.relic.feature.screens.main.MainScreenState
import kotlinx.coroutines.launch

@Composable
fun StudioPageRoute(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    newsViewModel: NewsViewModel
) {

    /* ======================== Common ======================== */

    val context = LocalContext.current
    val coroutineScope = mainScreenState.coroutineScope

    /* ======================== Field ======================== */

    // Trending
    val trendingNewsDataState by newsViewModel
        .trendingNewsDataStateFlow
        .collectAsStateWithLifecycle()

    // Top-headline
    val topHeadlineNewsDataState by newsViewModel
        .topHeadlineNewsDataStateFlow
        .collectAsStateWithLifecycle()

    /* ======================== Ui ======================== */

    // List state
    val newListState = rememberStudioNewsListState(
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

    StudioPage(
        bottomSheetState = bottomSheetState,
        onNewsTabItemClick = { currentSelectedTab, _ ->
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
            mainScreenState.navHostController.navigateToNewsDetailPage(
                title = it.title,
                contUrl = it.contentUrl
            )
        },
        onLikeNewsClick = {
            //
        },
        onShareNewsClick = {
            shareWebLink(
                context = context,
                title = it.title,
                url = it.contentUrl
            )
        },
        onTrendingNewsRetryClick = newsViewModel::getTrendingNewsData,
        onTopHeadlineNewsRetryClick = newsViewModel::getTopHeadlineNewsData
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun StudioPage(
    // Data state
    bottomSheetState: StudioBottomSheetState,
    // Click handler
    onNewsTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    onNewsCardClick: (model: NewsArticleModel) -> Unit,
    onLikeNewsClick: (model: NewsArticleModel) -> Unit,
    onShareNewsClick: (model: NewsArticleModel) -> Unit,
    onTrendingNewsRetryClick: () -> Unit,
    onTopHeadlineNewsRetryClick: () -> Unit
) {
    BottomSheetScaffold(
        sheetContent = {
            StudioBottomSheet(
                bottomSheetState = bottomSheetState,
                onTabItemClick = onNewsTabItemClick,
                onNewsCardClick = onNewsCardClick,
                onLikeClick = onLikeNewsClick,
                onShareClick = onShareNewsClick,
                onRetryTrendingClick = onTrendingNewsRetryClick,
                onRetryTopHeadlineClick = onTopHeadlineNewsRetryClick
            )
        },
        scaffoldState = rememberBottomSheetScaffoldState(),
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        sheetBackgroundColor = mainThemeColor,
        sheetPeekHeight = 140.dp,
    ) {
        StudioPageContent()
    }
}

@Composable
@Preview
private fun StudioPagePreview() {
    StudioPage(
        bottomSheetState = StudioBottomSheetState(
            currentSelectTab = 0,
            trendingNewsDataState = TrendingNewsDataState.Init,
            topHeadlineNewsDataState = TopHeadlineNewsDataState.Init,
            listState = rememberStudioNewsListState()
        ),
        onNewsTabItemClick = { _, _ -> },
        onNewsCardClick = {},
        onLikeNewsClick = {},
        onShareNewsClick = {},
        onTrendingNewsRetryClick = { },
        onTopHeadlineNewsRetryClick = {},
    )
}