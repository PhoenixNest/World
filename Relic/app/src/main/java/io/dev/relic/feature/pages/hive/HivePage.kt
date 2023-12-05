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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.core.ui.CommonLoadingComponent
import io.core.ui.CommonNoDataComponent
import io.core.ui.theme.RelicFontFamily
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
import io.dev.relic.feature.function.news.ui.NewsTabBar
import io.dev.relic.feature.function.news.ui.NewsTrendingPanel
import io.dev.relic.feature.pages.hive.viewmodel.HiveViewModel
import io.dev.relic.feature.screens.main.MainState

@Composable
fun HivePageRoute(
    mainViewModel: MainViewModel,
    hiveViewModel: HiveViewModel = hiltViewModel()
) {
    val mainState: MainState by mainViewModel.mainStateFlow.collectAsStateWithLifecycle()
    val everythingNewsDataState: EverythingNewsDataState = hiveViewModel.everythingNewsDataStateFlow.collectAsStateWithLifecycle().value
    val topHeadlineNewsDataState: TopHeadlineNewsDataState = hiveViewModel.topHeadlineNewsDataStateFlow.collectAsStateWithLifecycle().value

    /* ======================== Ui ======================== */

    val everythingNewsContentLazyListState: LazyListState = rememberLazyListState()
    val topHeadlineNewsTabLazyListState: LazyListState = rememberLazyListState()
    val topHeadlineNewsContentLazyListState: LazyListState = rememberLazyListState()

    LaunchedEffect(key1 = everythingNewsContentLazyListState) {
        //
    }

    LaunchedEffect(key1 = topHeadlineNewsContentLazyListState) {
        //
    }

    HivePage(
        everythingNewsDataState = everythingNewsDataState,
        topHeadlineNewsDataState = topHeadlineNewsDataState,
        currentSelectedNewsTabCategory = hiveViewModel.getSelectedTopHeadlineNewsCategoriesTab(),
        onTabItemClick = { currentSelectedTab: Int, keyWords: String ->
            hiveViewModel.updateSelectedTopHeadlineCategoriesTab(currentSelectedTab)
            if (currentSelectedTab == 0) {
                hiveViewModel.fetchEverythingNewsData()
            } else {
                hiveViewModel.fetchTopHeadlineNewsData(
                    keyWords = keyWords,
                    country = DEFAULT_NEWS_COUNTRY_TYPE,
                    category = DEFAULT_NEWS_CATEGORY,
                    pageSize = DEFAULT_INIT_NEWS_PAGE_SIZE,
                    page = DEFAULT_INIT_NEWS_PAGE_INDEX
                )
            }
        },
        onCardClick = {
            //
        },
        onLikeClick = {
            //
        },
        onShareClick = {
            //
        },
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
            )
        }
    }
}

@Suppress("FunctionName")
private fun LazyListScope.HiveTrendingPanel(
    everythingNewsDataState: EverythingNewsDataState,
    onCardClick: (model: NewsArticleModel) -> Unit,
    everythingNewsContentLazyListState: LazyListState
) {
    item {
        HiveNewsTitle()
        Spacer(modifier = Modifier.height(16.dp))
        HiveEverythingNewsPanel(
            everythingNewsDataState = everythingNewsDataState,
            onCardClick = onCardClick,
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
            fontFamily = RelicFontFamily.newsReader,
        )
    )
}

@Composable
private fun HiveEverythingNewsPanel(
    everythingNewsDataState: EverythingNewsDataState,
    onCardClick: (model: NewsArticleModel) -> Unit,
    lazyListState: LazyListState
) {
    when (val newsDataState: EverythingNewsDataState = everythingNewsDataState) {
        is EverythingNewsDataState.Init,
        is EverythingNewsDataState.Fetching -> {
            CommonLoadingComponent()
        }

        is EverythingNewsDataState.Empty,
        is EverythingNewsDataState.FetchFailed,
        is EverythingNewsDataState.NoNewsData -> {
            NewsTrendingPanel(
                modelList = emptyList(),
                onCardClick = onCardClick,
                lazyListState = lazyListState
            )
        }

        is EverythingNewsDataState.FetchSucceed -> {
            NewsTrendingPanel(
                modelList = newsDataState.modelList,
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
) {
    when (val newsDataState: TopHeadlineNewsDataState = topHeadlineNewsDataState) {
        is TopHeadlineNewsDataState.Init,
        is TopHeadlineNewsDataState.Fetching -> {
            item {
                CommonLoadingComponent()
            }
        }

        is TopHeadlineNewsDataState.Empty,
        is TopHeadlineNewsDataState.FetchFailed,
        is TopHeadlineNewsDataState.NoNewsData -> {
            item {
                CommonNoDataComponent()
            }
        }

        is TopHeadlineNewsDataState.FetchSucceed -> {
            itemsIndexed(newsDataState.modelList) { index: Int, data: NewsArticleModel? ->
                if (data == null) {
                    //
                } else {
                    val itemDecorationModifier: Modifier = Modifier.padding(
                        top = 16.dp,
                        bottom = if (index == newsDataState.modelList.size - 1) 120.dp else 0.dp
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