package io.dev.relic.feature.function.news.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.ext.ViewModelExt.operationInViewModelScope
import io.common.ext.ViewModelExt.setState
import io.common.util.LogUtil
import io.common.util.TimeUtil
import io.core.datastore.RelicDatastoreCenter.readSyncData
import io.core.datastore.RelicDatastoreCenter.writeSyncData
import io.domain.preference_key.NewsPreferenceKey.KEY_TOP_HEADLINE_STATUS
import io.domain.preference_key.NewsPreferenceKey.KEY_TOP_HEADLINE_TIME_DURATION
import io.domain.preference_key.NewsPreferenceKey.KEY_TRENDING_STATUS
import io.domain.preference_key.NewsPreferenceKey.KEY_TRENDING_TIME_DURATION
import io.data.dto.news.top_headlines.TopHeadlinesNewsDTO
import io.data.dto.news.trending.TrendingNewsDTO
import io.data.mappers.NewsDataMapper.toNewsArticleModelList
import io.data.mappers.NewsDataMapper.toTopHeadlineNewsEntity
import io.data.mappers.NewsDataMapper.toTrendingNewsEntity
import io.data.model.NetworkResult
import io.data.util.NewsCategory
import io.data.util.NewsConfig
import io.data.util.NewsConfig.DEFAULT_INIT_NEWS_PAGE_INDEX
import io.data.util.NewsConfig.DEFAULT_INIT_NEWS_PAGE_SIZE
import io.data.util.NewsConfig.DEFAULT_NEWS_SORT_RULE
import io.data.util.NewsConfig.TopHeadline.DEFAULT_NEWS_CATEGORY
import io.data.util.NewsConfig.TopHeadline.DEFAULT_NEWS_COUNTRY_TYPE
import io.data.util.NewsConfig.Trending.DEFAULT_NEWS_LANGUAGE
import io.data.util.NewsConfig.Trending.DEFAULT_NEWS_SOURCE
import io.data.util.NewsCountryType
import io.data.util.NewsLanguageType
import io.data.util.NewsSortRule
import io.dev.relic.feature.function.news.TopHeadlineNewsDataState
import io.dev.relic.feature.function.news.TrendingNewsDataState
import io.dev.relic.feature.function.news.util.NewsDataStatus
import io.dev.relic.feature.function.news.util.NewsType
import io.dev.relic.feature.function.news.util.NewsType.TOP_HEADLINE
import io.dev.relic.feature.function.news.util.NewsType.TRENDING
import io.domain.use_case.news.NewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    application: Application,
    private val newsUseCase: NewsUseCase
) : AndroidViewModel(application) {

    /**
     * Indicate the current selected top headline categories tab.
     * */
    private var _currentSelectedTopHeadlineNewsCategories by mutableIntStateOf(0)

    /**
     * The data flow of trending news.
     * */
    private val _trendingNewsDataStateFlow = MutableStateFlow<TrendingNewsDataState>(TrendingNewsDataState.Init)
    val trendingNewsDataStateFlow: StateFlow<TrendingNewsDataState> get() = _trendingNewsDataStateFlow

    /**
     * The data flow of top-headline news.
     * */
    private val _topHeadlineNewsDataStateFlow = MutableStateFlow<TopHeadlineNewsDataState>(TopHeadlineNewsDataState.Init)
    val topHeadlineNewsDataStateFlow: StateFlow<TopHeadlineNewsDataState> get() = _topHeadlineNewsDataStateFlow

    companion object {
        private const val TAG = "NewsViewModel"
        private const val DEFAULT_STOP_TIMEOUT_MILLIS = 5 * 1000L
        private const val DEFAULT_LAST_REFRESH_TIME = 0L
    }

    init {
        exec()
    }

    private fun exec() {
        checkShouldRefresh()
    }

    private fun checkShouldRefresh() {
        checkShouldRefreshTrendingData()
        checkShouldRefreshTopHeadlineData()
    }

    private fun checkShouldRefreshTrendingData() {
        val lastTimeDuration = readSyncData(KEY_TRENDING_TIME_DURATION, DEFAULT_LAST_REFRESH_TIME)
        val lastRefreshStatus = readSyncData(KEY_TRENDING_STATUS, NewsDataStatus.INIT.typeValue)
        if (TimeUtil.getCurrentTimeInMillis() > lastTimeDuration
            || lastRefreshStatus == NewsDataStatus.INIT.typeValue
            || lastRefreshStatus == NewsDataStatus.FAILED.typeValue
        ) {
            LogUtil.d(TAG, "[Trending News Checker] Refresh data.")
            getTrendingNewsData()
        } else {
            LogUtil.w(TAG, "[Top-Headline News Checker] Read local data.")
            queryLocalTrendingNewsData()
        }
    }

    private fun checkShouldRefreshTopHeadlineData() {
        val lastTimeDuration = readSyncData(KEY_TOP_HEADLINE_TIME_DURATION, DEFAULT_LAST_REFRESH_TIME)
        val lastRefreshStatus = readSyncData(KEY_TOP_HEADLINE_STATUS, NewsDataStatus.INIT.typeValue)
        if (TimeUtil.getCurrentTimeInMillis() > lastTimeDuration
            || lastRefreshStatus == NewsDataStatus.INIT.typeValue
            || lastRefreshStatus == NewsDataStatus.FAILED.typeValue
        ) {
            LogUtil.d(TAG, "[Top-Headline News Checker] Refresh data.")
            getTopHeadlineNewsData()
        } else {
            LogUtil.w(TAG, "[Top-Headline News Checker] Read local data.")
            queryLocalTopHeadlineNewsData()
        }
    }

    /* ======================== Local ======================== */

    /**
     * Query the local cache data from the database.
     * */
    private fun queryLocalTrendingNewsData() {
        operationInViewModelScope { scope ->
            LogUtil.w(TAG, "[Handle Trending News Cache] Loading...")
            setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.Fetching)

            // Query the local cache data from the database
            setState(_trendingNewsDataStateFlow, TrendingNewsDataState.Fetching)
            newsUseCase.queryAllTrendingNewsData()
                .stateIn(
                    scope = scope,
                    started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIMEOUT_MILLIS),
                    initialValue = emptyList()
                )
                .collect { entities ->
                    if (entities.isNotEmpty()) {
                        val localDatasource = entities.first().datasource
                        val modelList = localDatasource.articles?.toNewsArticleModelList()
                        modelList?.also { list ->
                            setState(_trendingNewsDataStateFlow, TrendingNewsDataState.FetchSucceed(list))
                        } ?: {
                            LogUtil.w(TAG, "[Handle Trending News Cache] Succeed without [Articles] data")
                            setState(_trendingNewsDataStateFlow, TrendingNewsDataState.NoNewsData)
                        }
                    } else {
                        LogUtil.e(TAG, "[Handle Trending News Cache] Succeed without data")
                        setState(_trendingNewsDataStateFlow, TrendingNewsDataState.NoNewsData)
                    }
                }
        }
    }

    /**
     * Query the local cache data from the database.
     * */
    private fun queryLocalTopHeadlineNewsData() {
        operationInViewModelScope {
            LogUtil.w(TAG, "[Handle Top-headline News Cache] Loading...")
            setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.Fetching)

            // Query the local cache data from the database
            newsUseCase.queryAllTopHeadlineNewsData()
                .stateIn(
                    scope = it,
                    started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIMEOUT_MILLIS),
                    initialValue = emptyList()
                )
                .collect { entities ->
                    if (entities.isNotEmpty()) {
                        val localDatasource = entities.first().datasource
                        val modelList = localDatasource.articles?.toNewsArticleModelList()
                        modelList?.also { list ->
                            setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.FetchSucceed(list))
                        } ?: {
                            LogUtil.w(TAG, "[Handle Top-headline News Cache] Succeed without [Articles] data")
                            setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.NoNewsData)
                        }
                    } else {
                        LogUtil.e(TAG, "[Handle Top-headline News Cache] Succeed without data")
                        setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.NoNewsData)
                    }
                }
        }
    }

    /* ======================== Remote ======================== */

    /**
     * Search through millions of articles from over 80,000 large and small news sources and blogs.
     *
     * `This endpoint suits article discovery and analysis.`
     *
     * @param keyWords          Keywords or phrases to search for in the article title and body. `Max length: 500 chars.`
     * @param source            A comma-seperated string of identifiers (maximum 20) for the news sources or blogs you want headlines from.
     *                          Use the /sources endpoint to locate these programmatically or look at the [sources index](https://newsapi.org/sources).
     * @param language          The 2-letter ISO-639-1 code of the language you want to get headlines for.
     *                          `Possible options: ar, de, en, es, fr, he, it, nl, no, pt, ru, sv, ud, zh.`
     * @param sortBy            The order to sort the articles in. `Possible options: relevancy, popularity, publishedAt`.
     *                          (1) relevancy = articles more closely related to q come first.
     *                          (2) popularity = articles from popular sources and publishers come first.
     *                          (3) publishedAt = newest articles come first.
     * @param pageSize          The number of results to return per page.
     * @param page              Use this to page through the results.
     * */
    fun getTrendingNewsData(
        keyWords: String = NewsConfig.Trending.DEFAULT_SEARCH_KEYWORDS,
        source: String = DEFAULT_NEWS_SOURCE,
        language: NewsLanguageType = DEFAULT_NEWS_LANGUAGE,
        sortBy: NewsSortRule = DEFAULT_NEWS_SORT_RULE,
        pageSize: Int = DEFAULT_INIT_NEWS_PAGE_SIZE,
        page: Int = DEFAULT_INIT_NEWS_PAGE_INDEX
    ) {
        if (trendingNewsDataStateFlow.value == TrendingNewsDataState.Fetching) {
            LogUtil.e(TAG, "[Trending News] Loading, no need request the data twice.")
            return
        }

        operationInViewModelScope { scope ->
            newsUseCase.getTrendingNewsData(
                keyWords = keyWords,
                source = source,
                language = language,
                sortBy = sortBy,
                pageSize = pageSize,
                page = page
            ).stateIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIMEOUT_MILLIS),
                initialValue = NetworkResult.Loading()
            ).also { stateFlow ->
                stateFlow.collect { result ->
                    handleTrendingNewsData(result)
                }
            }
        }
    }

    /**
     * This endpoint provides live top and breaking headlines for a country,
     * specific category in a country, single source, or multiple sources.
     * You can also search with keywords.
     *
     * `Articles are sorted by the earliest date published first`.
     *
     * @param keyWords          Keywords or a phrase to search for.
     * @param country           The 2-letter ISO 3166-1 code of the country you want to get headlines for.
     *                          `Possible options: ae, ar, at, au, be, bg, br, ca, ch, cn, co, cu, cz, de, eg, fr, gb, gr, hk, hu, id, ie, il, in, it, jp, kr, lt, lv, ma, mx, my, ng, nl, no, nz, ph, pl, pt, ro, rs, ru, sa, se, sg, si, sk, th, tr, tw, ua, us, ve, za.`
     *                          Note: you can't mix this param with the sources param.
     * @param category          The category you want to get headlines for.
     *                          `Possible options: business, entertainment, general, health, science, sports, technology.`
     *                          Note: you can't mix this param with the sources param.
     * @param pageSize          The number of results to return per page.
     * @param page              Use this to page through the results.
     * */
    fun getTopHeadlineNewsData(
        keyWords: String = NewsConfig.TopHeadline.DEFAULT_SEARCH_KEYWORDS,
        country: NewsCountryType = DEFAULT_NEWS_COUNTRY_TYPE,
        category: NewsCategory = DEFAULT_NEWS_CATEGORY,
        pageSize: Int = DEFAULT_INIT_NEWS_PAGE_SIZE,
        page: Int = DEFAULT_INIT_NEWS_PAGE_INDEX
    ) {
        if (topHeadlineNewsDataStateFlow.value == TopHeadlineNewsDataState.Fetching) {
            LogUtil.e(TAG, "[Top-headline News] Loading, no need request the data twice.")
            return
        }

        operationInViewModelScope { scope ->
            newsUseCase.getTopHeadlineNews(
                keyWords = keyWords,
                country = country,
                category = category,
                pageSize = pageSize,
                page = page
            ).stateIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIMEOUT_MILLIS),
                initialValue = NetworkResult.Loading()
            ).also { stateFlow ->
                stateFlow.collect { result ->
                    handleTopHeadlineNewsData(result)
                }
            }
        }
    }

    /**
     * Return the latest index value of tab.
     * */
    fun getSelectedNewsTab(): Int {
        return _currentSelectedTopHeadlineNewsCategories
    }

    /**
     * Update the current selected news category of top-headline news.
     *
     * @param newIndex      the index value of tab when the user has clicked the tab
     * */
    fun updateSelectedTopHeadlineCategoriesTab(newIndex: Int) {
        _currentSelectedTopHeadlineNewsCategories = newIndex
    }

    /**
     * Handle the original network result of network-layer.
     *
     * @param result        received DTO from the remote-server
     * */
    private suspend fun handleTrendingNewsData(result: NetworkResult<TrendingNewsDTO>) {
        updateNewsRefreshTime(TRENDING)
        when (result) {
            is NetworkResult.Loading -> {
                LogUtil.d(TAG, "[Handle Trending News Data] Loading...")
                setState(_trendingNewsDataStateFlow, TrendingNewsDataState.Fetching)
            }

            is NetworkResult.Success -> {
                result.data?.also { dto ->
                    LogUtil.d(TAG, "[Handle Trending News Data] Succeed, data: $dto")
                    dto.articles?.also {
                        updateNewsRefreshStatus(TRENDING, NewsDataStatus.SUCCESS)
                        setState(_trendingNewsDataStateFlow, TrendingNewsDataState.FetchSucceed(it.toNewsArticleModelList()))
                        newsUseCase.cacheTrendingNewsData.invoke(dto.toTrendingNewsEntity())
                    } ?: {
                        LogUtil.w(TAG, "[Handle Trending News Data] Succeed without [Articles] data")
                        updateNewsRefreshStatus(TRENDING, NewsDataStatus.SUCCESS_WITHOUT_DATA)
                        setState(_trendingNewsDataStateFlow, TrendingNewsDataState.NoNewsData)
                    }
                } ?: {
                    LogUtil.w(TAG, "[Handle Trending News Data] Succeed without data")
                    updateNewsRefreshStatus(TRENDING, NewsDataStatus.SUCCESS_WITHOUT_DATA)
                    setState(_trendingNewsDataStateFlow, TrendingNewsDataState.NoNewsData)
                }
            }

            is NetworkResult.Failed -> {
                val errorCode = result.code
                val errorMessage = result.message
                LogUtil.e(TAG, "[Handle Trending News Data] Failed, ($errorCode, $errorMessage)")
                updateNewsRefreshStatus(TRENDING, NewsDataStatus.FAILED)
                setState(_trendingNewsDataStateFlow, TrendingNewsDataState.FetchFailed(errorCode, errorMessage))
            }
        }
    }

    /**
     * Handle the original network result of network-layer.
     *
     * @param result        received DTO from the remote-server
     * */
    private suspend fun handleTopHeadlineNewsData(result: NetworkResult<TopHeadlinesNewsDTO>) {
        updateNewsRefreshTime(TOP_HEADLINE)
        when (result) {
            is NetworkResult.Loading -> {
                LogUtil.d(TAG, "[Handle Top-Headline News Data] Loading...")
                setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.Fetching)
            }

            is NetworkResult.Success -> {
                result.data?.also { dto ->
                    LogUtil.d(TAG, "[Handle Top-Headline News Data] Succeed, data: $dto")
                    dto.articles?.also {
                        updateNewsRefreshStatus(TOP_HEADLINE, NewsDataStatus.SUCCESS)
                        setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.FetchSucceed(it.toNewsArticleModelList()))
                        newsUseCase.cacheTopHeadlineNewsData.invoke(dto.toTopHeadlineNewsEntity())
                    } ?: {
                        LogUtil.w(TAG, "[Handle Top-Headline News Data] Succeed without [Articles] data")
                        updateNewsRefreshStatus(TOP_HEADLINE, NewsDataStatus.SUCCESS_WITHOUT_DATA)
                        setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.NoNewsData)
                    }
                } ?: {
                    LogUtil.w(TAG, "[Handle Top-Headline News Data] Succeed without data")
                    updateNewsRefreshStatus(TOP_HEADLINE, NewsDataStatus.SUCCESS_WITHOUT_DATA)
                    setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.NoNewsData)
                }
            }

            is NetworkResult.Failed -> {
                val errorCode = result.code
                val errorMessage = result.message
                LogUtil.e(TAG, "[Handle Top-Headline News Data] Failed, ($errorCode, $errorMessage)")
                updateNewsRefreshStatus(TOP_HEADLINE, NewsDataStatus.FAILED)
                setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.FetchFailed(errorCode, errorMessage))
            }
        }
    }

    /**
     * Update the latest refresh time into datastore.
     *
     * @param type
     *
     * @see NewsType
     * */
    private fun updateNewsRefreshTime(type: NewsType) {
        val key = when (type) {
            TRENDING -> KEY_TRENDING_TIME_DURATION
            TOP_HEADLINE -> KEY_TOP_HEADLINE_TIME_DURATION
        }

        writeSyncData(key, TimeUtil.getCurrentTimeInMillis())
    }

    /**
     * Update the latest refresh status into datastore.
     *
     * @param type
     * @param status
     *
     * @see NewsType
     * @see NewsDataStatus
     * */
    private fun updateNewsRefreshStatus(
        type: NewsType,
        status: NewsDataStatus
    ) {
        val key = when (type) {
            TRENDING -> KEY_TRENDING_STATUS
            TOP_HEADLINE -> KEY_TOP_HEADLINE_STATUS
        }

        writeSyncData(key, status.typeValue)
    }

}