package io.dev.relic.feature.pages.hive

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.common.RelicShareCenter.shareWebLink
import io.core.ui.CommonRetryComponent
import io.core.ui.theme.RelicFontFamily.newsReader
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColor
import io.data.model.news.NewsArticleModel
import io.dev.relic.R
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.news.EverythingNewsDataState
import io.dev.relic.feature.function.news.NewsUnitConfig.DEFAULT_INIT_NEWS_PAGE_INDEX
import io.dev.relic.feature.function.news.NewsUnitConfig.DEFAULT_INIT_NEWS_PAGE_SIZE
import io.dev.relic.feature.function.news.NewsUnitConfig.TopHeadline.DEFAULT_NEWS_CATEGORY
import io.dev.relic.feature.function.news.NewsUnitConfig.TopHeadline.DEFAULT_NEWS_COUNTRY_TYPE
import io.dev.relic.feature.function.news.TopHeadlineNewsDataState
import io.dev.relic.feature.function.news.ui.NewsCardItem
import io.dev.relic.feature.function.news.ui.NewsLoadingPlaceholder
import io.dev.relic.feature.function.news.ui.NewsTabBar
import io.dev.relic.feature.function.news.ui.NewsTrendingPanel
import io.dev.relic.feature.pages.detail.news.navigateToNewsDetailPage
import io.dev.relic.feature.pages.hive.viewmodel.HiveViewModel
import io.dev.relic.feature.screens.main.MainScreenState

@Composable
fun HivePageRoute(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    hiveViewModel: HiveViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    /* ======================== Field ======================== */

    // Trending
    val remoteEverythingNewsDataState by hiveViewModel.everythingNewsDataStateFlow.collectAsStateWithLifecycle()

    // Top-headline
    val topHeadlineNewsDataState by hiveViewModel.topHeadlineNewsDataStateFlow.collectAsStateWithLifecycle()

    /* ======================== Ui ======================== */

    val everythingNewsContentLazyListState = rememberLazyListState()
    val topHeadlineNewsTabLazyListState = rememberLazyListState()
    val topHeadlineNewsContentLazyListState = rememberLazyListState()

    LaunchedEffect(everythingNewsContentLazyListState) {
        //
    }

    LaunchedEffect(topHeadlineNewsContentLazyListState) {
        //
    }

    HivePage(
        everythingNewsDataState = remoteEverythingNewsDataState,
        topHeadlineNewsDataState = topHeadlineNewsDataState,
        currentSelectedNewsTabCategory = hiveViewModel.getSelectedTopHeadlineNewsCategoriesTab(),
        onTabItemClick = { currentSelectedTab, keyWords ->
            hiveViewModel.apply {
                updateSelectedTopHeadlineCategoriesTab(currentSelectedTab)
                fetchTopHeadlineNewsData(
                    keyWords = keyWords,
                    country = DEFAULT_NEWS_COUNTRY_TYPE,
                    category = DEFAULT_NEWS_CATEGORY,
                    pageSize = DEFAULT_INIT_NEWS_PAGE_SIZE,
                    page = DEFAULT_INIT_NEWS_PAGE_INDEX
                )
            }
        },
        onCardClick = {
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
        onRetryTrendingClick = hiveViewModel::fetchEverythingNewsData,
        onRetryTopHeadlineClick = hiveViewModel::fetchTopHeadlineNewsData,
        everythingNewsContentLazyListState = everythingNewsContentLazyListState,
        topHeadlineNewsTabLazyListState = topHeadlineNewsTabLazyListState,
        topHeadlineNewsContentLazyListState = topHeadlineNewsContentLazyListState
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HivePage(
    everythingNewsDataState: EverythingNewsDataState,
    topHeadlineNewsDataState: TopHeadlineNewsDataState,
    currentSelectedNewsTabCategory: Int,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    onCardClick: (model: NewsArticleModel) -> Unit,
    onLikeClick: (model: NewsArticleModel) -> Unit,
    onShareClick: (model: NewsArticleModel) -> Unit,
    onRetryTrendingClick: () -> Unit,
    onRetryTopHeadlineClick: () -> Unit,
    everythingNewsContentLazyListState: LazyListState,
    topHeadlineNewsTabLazyListState: LazyListState,
    topHeadlineNewsContentLazyListState: LazyListState
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
            HiveTrendingPanel(
                everythingNewsDataState = everythingNewsDataState,
                onCardClick = onCardClick,
                onRetryClick = onRetryTrendingClick,
                everythingNewsContentLazyListState = everythingNewsContentLazyListState
            )
            stickyHeader {
                HiveTabBar(
                    currentSelectedNewsTabCategory,
                    onTabItemClick,
                    lazyListState = topHeadlineNewsTabLazyListState
                )
            }
            HiveTopHeadlineNewsPanel(
                topHeadlineNewsDataState = topHeadlineNewsDataState,
                onCardClick = onCardClick,
                onLikeClick = onLikeClick,
                onShareClick = onShareClick,
                onRetryClick = onRetryTopHeadlineClick
            )
        }
    }
}

@Suppress("FunctionName")
private fun LazyListScope.HiveTrendingPanel(
    everythingNewsDataState: EverythingNewsDataState,
    onCardClick: (model: NewsArticleModel) -> Unit,
    onRetryClick: () -> Unit,
    everythingNewsContentLazyListState: LazyListState
) {
    item {
        HiveNewsTitle()
        Spacer(modifier = Modifier.height(16.dp))
        HiveEverythingNewsPanel(
            everythingNewsDataState = everythingNewsDataState,
            onCardClick = onCardClick,
            onRetryClick = onRetryClick,
            lazyListState = everythingNewsContentLazyListState
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun HiveNewsTitle() {
    Text(
        text = stringResource(R.string.news_title),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        maxLines = 1,
        style = TextStyle(
            color = mainTextColor,
            fontSize = 72.sp,
            fontFamily = newsReader,
        )
    )
}

@Composable
private fun HiveEverythingNewsPanel(
    everythingNewsDataState: EverythingNewsDataState,
    onCardClick: (model: NewsArticleModel) -> Unit,
    onRetryClick: () -> Unit,
    lazyListState: LazyListState
) {
    when (everythingNewsDataState) {
        is EverythingNewsDataState.Init,
        is EverythingNewsDataState.Fetching -> {
            NewsLoadingPlaceholder(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(12.dp)
            )
        }

        is EverythingNewsDataState.Empty,
        is EverythingNewsDataState.FetchFailed,
        is EverythingNewsDataState.NoNewsData -> {
            CommonRetryComponent(
                onRetryClick = onRetryClick,
                modifier = Modifier.padding(12.dp),
                containerHeight = 196.dp
            )
        }

        is EverythingNewsDataState.FetchSucceed -> {
            NewsTrendingPanel(
                modelList = everythingNewsDataState.modelList,
                onCardClick = onCardClick,
                lazyListState = lazyListState
            )
        }
    }
}

@Composable
private fun HiveTabBar(
    currentSelectedTab: Int,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    lazyListState: LazyListState
) {
    NewsTabBar(
        currentSelectedTab = currentSelectedTab,
        onTabItemClick = onTabItemClick,
        lazyListState = lazyListState
    )
}

@Suppress("FunctionName")
private fun LazyListScope.HiveTopHeadlineNewsPanel(
    topHeadlineNewsDataState: TopHeadlineNewsDataState,
    onCardClick: (model: NewsArticleModel) -> Unit,
    onLikeClick: (model: NewsArticleModel) -> Unit,
    onShareClick: (model: NewsArticleModel) -> Unit,
    onRetryClick: () -> Unit
) {
    when (topHeadlineNewsDataState) {
        is TopHeadlineNewsDataState.Init,
        is TopHeadlineNewsDataState.Fetching -> {
            item {
                NewsLoadingPlaceholder(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(12.dp)
                )
            }
        }

        is TopHeadlineNewsDataState.Empty,
        is TopHeadlineNewsDataState.FetchFailed,
        is TopHeadlineNewsDataState.NoNewsData -> {
            item {
                CommonRetryComponent(
                    onRetryClick = onRetryClick,
                    modifier = Modifier.padding(12.dp),
                    containerHeight = 300.dp
                )
            }
        }

        is TopHeadlineNewsDataState.FetchSucceed -> {
            itemsIndexed(topHeadlineNewsDataState.modelList) { index, data ->
                if (data == null) {
                    //
                } else {
                    val itemDecorationModifier = Modifier.padding(
                        top = 16.dp,
                        bottom = if (index == topHeadlineNewsDataState.modelList.size - 1) 120.dp else 0.dp
                    )
                    NewsCardItem(
                        data = data,
                        onCardClick = { onCardClick.invoke(data) },
                        onLikeClick = { onLikeClick.invoke(data) },
                        onShareClick = { onShareClick.invoke(data) },
                        modifier = itemDecorationModifier
                    )
                }
            }
        }
    }
}