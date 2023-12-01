package io.dev.relic.feature.pages.hive

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.core.ui.theme.mainThemeColor
import io.data.model.news.NewsArticleModel
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.news.EverythingNewsDataState
import io.dev.relic.feature.function.news.NewsUnitConfig.DEFAULT_INIT_NEWS_PAGE_INDEX
import io.dev.relic.feature.function.news.NewsUnitConfig.DEFAULT_INIT_NEWS_PAGE_SIZE
import io.dev.relic.feature.function.news.NewsUnitConfig.TopHeadline.DEFAULT_NEWS_CATEGORY
import io.dev.relic.feature.function.news.NewsUnitConfig.TopHeadline.DEFAULT_NEWS_COUNTRY_TYPE
import io.dev.relic.feature.function.news.TopHeadlineNewsDataState
import io.dev.relic.feature.function.news.ui.NewsPanel
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

    HivePage(
        everythingNewsDataState = everythingNewsDataState,
        topHeadlineNewsDataState = topHeadlineNewsDataState,
        currentSelectedNewsTabCategories = hiveViewModel.getSelectedTopHeadlineNewsCategoriesTab(),
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
        }
    )
}

@Composable
private fun HivePage(
    everythingNewsDataState: EverythingNewsDataState,
    topHeadlineNewsDataState: TopHeadlineNewsDataState,
    currentSelectedNewsTabCategories: Int,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    onCardClick: (model: NewsArticleModel) -> Unit,
    onLikeClick: (model: NewsArticleModel) -> Unit,
    onShareClick: (model: NewsArticleModel) -> Unit,
) {

    val newsList: MutableList<NewsArticleModel?> = mutableListOf()

    when (everythingNewsDataState) {
        is EverythingNewsDataState.Init,
        is EverythingNewsDataState.Fetching,
        is EverythingNewsDataState.NoNewsData -> {
            newsList.clear()
        }

        is EverythingNewsDataState.Empty,
        is EverythingNewsDataState.FetchFailed -> {
            newsList.clear()
        }

        is EverythingNewsDataState.FetchSucceed -> {
            newsList.clear()
            val modelList = everythingNewsDataState.modelList
            if (!modelList.isNullOrEmpty()) {
                newsList.addAll(modelList)
            }
        }
    }

    when (topHeadlineNewsDataState) {
        is TopHeadlineNewsDataState.Init,
        is TopHeadlineNewsDataState.Fetching,
        is TopHeadlineNewsDataState.NoNewsData -> {
            newsList.clear()
        }

        is TopHeadlineNewsDataState.Empty,
        is TopHeadlineNewsDataState.FetchFailed -> {
            newsList.clear()
        }

        is TopHeadlineNewsDataState.FetchSucceed -> {
            newsList.clear()
            val modelList: List<NewsArticleModel?>? = topHeadlineNewsDataState.modelList
            if (!modelList.isNullOrEmpty()) {
                newsList.addAll(modelList)
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mainThemeColor
    ) {
        NewsPanel(
            currentSelectedTab = currentSelectedNewsTabCategories,
            modelList = newsList,
            onTabItemClick = onTabItemClick,
            onCardClick = onCardClick,
            onLikeClick = onLikeClick,
            onShareClick = onShareClick
        )
    }
}