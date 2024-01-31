package io.dev.relic.feature.pages.hive.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.ext.ViewModelExt.setState
import io.common.util.LogUtil
import io.common.util.TimeUtil.getCurrentTimeInMillis
import io.core.database.repository.RelicDatabaseRepository
import io.core.datastore.RelicDatastoreCenter.readSyncData
import io.core.datastore.RelicDatastoreCenter.writeSyncData
import io.core.datastore.preference_keys.NewsPreferenceKeys.KEY_EVERYTHING_STATUS
import io.core.datastore.preference_keys.NewsPreferenceKeys.KEY_EVERYTHING_TIME_DURATION
import io.core.datastore.preference_keys.NewsPreferenceKeys.KEY_TOP_HEADLINE_STATUS
import io.core.datastore.preference_keys.NewsPreferenceKeys.KEY_TOP_HEADLINE_TIME_DURATION
import io.data.dto.news.everything.TrendingNewsDTO
import io.data.dto.news.top_headlines.TopHeadlinesNewsDTO
import io.data.mappers.NewsDataMapper.toNewsArticleModelList
import io.data.model.NetworkResult
import io.data.util.NewsCategory
import io.data.util.NewsCountryType
import io.data.util.NewsLanguageType
import io.data.util.NewsSortRule
import io.dev.relic.feature.function.news.EverythingNewsDataState
import io.dev.relic.feature.function.news.NewsUnitConfig
import io.dev.relic.feature.function.news.NewsUnitConfig.DEFAULT_INIT_NEWS_PAGE_INDEX
import io.dev.relic.feature.function.news.NewsUnitConfig.DEFAULT_INIT_NEWS_PAGE_SIZE
import io.dev.relic.feature.function.news.NewsUnitConfig.DEFAULT_NEWS_SORT_RULE
import io.dev.relic.feature.function.news.NewsUnitConfig.Everything.DEFAULT_NEWS_LANGUAGE
import io.dev.relic.feature.function.news.NewsUnitConfig.Everything.DEFAULT_NEWS_SOURCE
import io.dev.relic.feature.function.news.NewsUnitConfig.TopHeadline.DEFAULT_NEWS_CATEGORY
import io.dev.relic.feature.function.news.NewsUnitConfig.TopHeadline.DEFAULT_NEWS_COUNTRY_TYPE
import io.dev.relic.feature.function.news.TopHeadlineNewsDataState
import io.dev.relic.feature.function.news.util.NewsDataStatus
import io.dev.relic.feature.function.news.util.NewsDataStatus.Failed
import io.dev.relic.feature.function.news.util.NewsDataStatus.Init
import io.dev.relic.feature.function.news.util.NewsDataStatus.Success
import io.dev.relic.feature.function.news.util.NewsDataStatus.SuccessWithoutData
import io.dev.relic.feature.function.news.util.NewsType
import io.dev.relic.feature.function.news.util.NewsType.Everything
import io.dev.relic.feature.function.news.util.NewsType.TopHeadline
import io.domain.use_case.news.NewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HiveViewModel @Inject constructor(
    application: Application,
    private val databaseRepository: RelicDatabaseRepository,
    private val newsUseCase: NewsUseCase
) : AndroidViewModel(application) {

    /**
     * Indicate the current selected top headline categories tab.
     * */
    private var _currentSelectedTopHeadlineNewsCategories by mutableIntStateOf(0)

    /**
     * The data flow of everything news.
     * */
    private val _everythingNewsDataStateFlow = MutableStateFlow<EverythingNewsDataState>(EverythingNewsDataState.Init)
    val everythingNewsDataStateFlow: StateFlow<EverythingNewsDataState> get() = _everythingNewsDataStateFlow

    /**
     * The data flow of everything news.
     * */
    private val _topHeadlineNewsDataStateFlow = MutableStateFlow<TopHeadlineNewsDataState>(TopHeadlineNewsDataState.Init)
    val topHeadlineNewsDataStateFlow: StateFlow<TopHeadlineNewsDataState> get() = _topHeadlineNewsDataStateFlow

    companion object {
        private const val TAG = "HiveViewModel"
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
        checkShouldRefreshEverythingData()
        checkShouldRefreshTopHeadlineData()
    }

    private fun checkShouldRefreshEverythingData() {
        val lastTimeDuration = readSyncData(KEY_EVERYTHING_TIME_DURATION, DEFAULT_LAST_REFRESH_TIME)
        val lastRefreshStatus = readSyncData(KEY_EVERYTHING_STATUS, Init.typeValue)
        if (getCurrentTimeInMillis() > lastTimeDuration
            || lastRefreshStatus == Init.typeValue
            || lastRefreshStatus == Failed.typeValue
        ) {
            LogUtil.d(TAG, "[Everything News Checker] Refresh data.")
            getTrendingNewsData()
        } else {
            LogUtil.w(TAG, "[Top-Headline News Checker] Read local data.")
            queryLocalEverythingNewsData()
        }
    }

    private fun checkShouldRefreshTopHeadlineData() {
        val lastTimeDuration = readSyncData(KEY_TOP_HEADLINE_TIME_DURATION, DEFAULT_LAST_REFRESH_TIME)
        val lastRefreshStatus = readSyncData(KEY_TOP_HEADLINE_STATUS, Init.typeValue)
        if (getCurrentTimeInMillis() > lastTimeDuration
            || lastRefreshStatus == Init.typeValue
            || lastRefreshStatus == Failed.typeValue
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
    private fun queryLocalEverythingNewsData() {
        viewModelScope.launch {
            LogUtil.w(TAG, "[Handle Everything News Cache] Loading...")
            setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.Fetching)

            // Query the local cache data from the database
            setState(_everythingNewsDataStateFlow, EverythingNewsDataState.Fetching)
            databaseRepository
                .queryAllTrendingNewsData()
                .stateIn(
                    scope = this,
                    started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIMEOUT_MILLIS),
                    initialValue = emptyList()
                )
                .collect { entities ->
                    if (entities.isNotEmpty()) {
                        val localDatasource = entities.first().datasource
                        val modelList = localDatasource.articles?.toNewsArticleModelList()
                        modelList?.also { list ->
                            setState(_everythingNewsDataStateFlow, EverythingNewsDataState.FetchSucceed(list))
                        } ?: {
                            LogUtil.w(TAG, "[Handle Everything News Cache] Succeed without [Articles] data")
                            setState(_everythingNewsDataStateFlow, EverythingNewsDataState.NoNewsData)
                        }
                    } else {
                        LogUtil.e(TAG, "[Handle Everything News Cache] Succeed without data")
                        setState(_everythingNewsDataStateFlow, EverythingNewsDataState.NoNewsData)
                    }
                }
        }
    }

    /**
     * Query the local cache data from the database.
     * */
    private fun queryLocalTopHeadlineNewsData() {
        viewModelScope.launch {
            LogUtil.w(TAG, "[Handle Top-headline News Cache] Loading...")
            setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.Fetching)

            // Query the local cache data from the database
            databaseRepository
                .queryAllTopHeadlineNewsData()
                .stateIn(
                    scope = this,
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
        keyWords: String = NewsUnitConfig.Everything.DEFAULT_SEARCH_KEYWORDS,
        source: String = DEFAULT_NEWS_SOURCE,
        language: NewsLanguageType = DEFAULT_NEWS_LANGUAGE,
        sortBy: NewsSortRule = DEFAULT_NEWS_SORT_RULE,
        pageSize: Int = DEFAULT_INIT_NEWS_PAGE_SIZE,
        page: Int = DEFAULT_INIT_NEWS_PAGE_INDEX
    ): StateFlow<NetworkResult<TrendingNewsDTO>> {
        return newsUseCase
            .getTrendingNewsData(
                keyWords = keyWords,
                source = source,
                language = language,
                sortBy = sortBy,
                pageSize = pageSize,
                page = page
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIMEOUT_MILLIS),
                initialValue = NetworkResult.Loading()
            )
            .also { stateFlow ->
                viewModelScope.launch {
                    stateFlow.collect { result ->
                        handleEverythingNewsData(result)
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
        keyWords: String = NewsUnitConfig.TopHeadline.DEFAULT_SEARCH_KEYWORDS,
        country: NewsCountryType = DEFAULT_NEWS_COUNTRY_TYPE,
        category: NewsCategory = DEFAULT_NEWS_CATEGORY,
        pageSize: Int = DEFAULT_INIT_NEWS_PAGE_SIZE,
        page: Int = DEFAULT_INIT_NEWS_PAGE_INDEX
    ): StateFlow<NetworkResult<TopHeadlinesNewsDTO>> {
        return newsUseCase
            .getTopHeadlineNews(
                keyWords = keyWords,
                country = country,
                category = category,
                pageSize = pageSize,
                page = page
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(DEFAULT_STOP_TIMEOUT_MILLIS),
                initialValue = NetworkResult.Loading()
            )
            .also { stateFlow ->
                viewModelScope.launch {
                    stateFlow.collect { result ->
                        handleTopHeadlineNewsData(result)
                    }
                }
            }
    }

    /**
     * Return the latest index value of tab.
     * */
    fun getSelectedTopHeadlineNewsCategoriesTab(): Int {
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
    private fun handleEverythingNewsData(result: NetworkResult<TrendingNewsDTO>) {
        updateNewsRefreshTime(Everything)
        when (result) {
            is NetworkResult.Loading -> {
                LogUtil.d(TAG, "[Handle Everything News Data] Loading...")
                setState(_everythingNewsDataStateFlow, EverythingNewsDataState.Fetching)
            }

            is NetworkResult.Success -> {
                result.data?.also { dto ->
                    LogUtil.d(TAG, "[Handle Everything News Data] Succeed, data: $dto")
                    dto.articles?.also {
                        updateNewsRefreshStatus(Everything, Success)
                        setState(_everythingNewsDataStateFlow, EverythingNewsDataState.FetchSucceed(it.toNewsArticleModelList()))
                    } ?: {
                        LogUtil.w(TAG, "[Handle Everything News Data] Succeed without [Articles] data")
                        updateNewsRefreshStatus(Everything, SuccessWithoutData)
                        setState(_everythingNewsDataStateFlow, EverythingNewsDataState.NoNewsData)
                    }
                } ?: {
                    LogUtil.w(TAG, "[Handle Everything News Data] Succeed without data")
                    updateNewsRefreshStatus(Everything, SuccessWithoutData)
                    setState(_everythingNewsDataStateFlow, EverythingNewsDataState.NoNewsData)
                }
            }

            is NetworkResult.Failed -> {
                val errorCode = result.code
                val errorMessage = result.message
                LogUtil.e(TAG, "[Handle Everything News Data] Failed, ($errorCode, $errorMessage)")
                updateNewsRefreshStatus(Everything, Failed)
                setState(_everythingNewsDataStateFlow, EverythingNewsDataState.FetchFailed(errorCode, errorMessage))
            }
        }
    }

    /**
     * Handle the original network result of network-layer.
     *
     * @param result        received DTO from the remote-server
     * */
    private fun handleTopHeadlineNewsData(result: NetworkResult<TopHeadlinesNewsDTO>) {
        updateNewsRefreshTime(TopHeadline)
        when (result) {
            is NetworkResult.Loading -> {
                LogUtil.d(TAG, "[Handle Top Headline News Data] Loading...")
                setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.Fetching)
            }

            is NetworkResult.Success -> {
                result.data?.also { dto ->
                    LogUtil.d(TAG, "[Handle Top Headline News Data] Succeed, data: $dto")
                    dto.articles?.also {
                        updateNewsRefreshStatus(TopHeadline, Success)
                        setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.FetchSucceed(it.toNewsArticleModelList()))
                    } ?: {
                        LogUtil.w(TAG, "[Handle Top Headline News Data] Succeed without [Articles] data")
                        updateNewsRefreshStatus(TopHeadline, SuccessWithoutData)
                        setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.NoNewsData)
                    }
                } ?: {
                    LogUtil.w(TAG, "[Handle Top Headline News Data] Succeed without data")
                    updateNewsRefreshStatus(TopHeadline, SuccessWithoutData)
                    setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.NoNewsData)
                }
            }

            is NetworkResult.Failed -> {
                val errorCode = result.code
                val errorMessage = result.message
                LogUtil.e(TAG, "[Handle Top Headline News Data] Failed, ($errorCode, $errorMessage)")
                updateNewsRefreshStatus(TopHeadline, Failed)
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
            Everything -> KEY_EVERYTHING_TIME_DURATION
            TopHeadline -> KEY_TOP_HEADLINE_TIME_DURATION
        }

        writeSyncData(key, getCurrentTimeInMillis())
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
            Everything -> KEY_EVERYTHING_STATUS
            TopHeadline -> KEY_TOP_HEADLINE_STATUS
        }

        writeSyncData(key, status.typeValue)
    }
}