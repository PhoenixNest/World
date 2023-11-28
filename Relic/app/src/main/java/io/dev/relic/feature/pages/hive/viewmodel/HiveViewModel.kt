package io.dev.relic.feature.pages.hive.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.ext.ViewModelExt.setState
import io.common.util.LogUtil
import io.core.database.repository.RelicDatabaseRepository
import io.data.dto.news.everything.NewsEverythingDTO
import io.data.dto.news.top_headlines.NewsTopHeadlinesDTO
import io.data.mappers.NewsDataMapper.toNewsArticleModelList
import io.data.model.NetworkResult
import io.data.util.NewsCategory
import io.data.util.NewsSortRule
import io.dev.relic.feature.function.news.EverythingNewsDataState
import io.dev.relic.feature.function.news.TopHeadlineNewsDataState
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
     * The data flow of everything news.
     * */
    private val _everythingNewsDataStateFlow: MutableStateFlow<EverythingNewsDataState> = MutableStateFlow(EverythingNewsDataState.Init)
    val everythingNewsDataStateFlow: StateFlow<EverythingNewsDataState> get() = _everythingNewsDataStateFlow

    /**
     * The data flow of everything news.
     * */
    private val _topHeadlineNewsDataStateFlow: MutableStateFlow<TopHeadlineNewsDataState> = MutableStateFlow(TopHeadlineNewsDataState.Init)
    val topHeadlineNewsDataStateFlow: StateFlow<TopHeadlineNewsDataState> get() = _topHeadlineNewsDataStateFlow

    companion object {
        private const val TAG = "HiveViewModel"
    }

    fun fetchEverythingNewsData(
        keyWords: String,
        source: String,
        language: String,
        sortBy: NewsSortRule,
        pageSize: Int,
        page: Int
    ): StateFlow<NetworkResult<NewsEverythingDTO>> {
        return newsUseCase
            .fetchEverythingNews(
                keyWords = keyWords,
                source = source,
                language = language,
                sortBy = sortBy,
                pageSize = pageSize,
                page = page
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5 * 1000L),
                initialValue = NetworkResult.Loading()
            )
            .also { stateFlow: StateFlow<NetworkResult<NewsEverythingDTO>> ->
                viewModelScope.launch {
                    stateFlow.collect { result: NetworkResult<NewsEverythingDTO> ->
                        handleEverythingNewsData(result)
                    }
                }
            }
    }

    fun fetchTopHeadlineNewsData(
        keyWords: String,
        country: String,
        category: NewsCategory,
        pageSize: Int,
        page: Int
    ): StateFlow<NetworkResult<NewsTopHeadlinesDTO>> {
        return newsUseCase
            .fetchTopHeadlineNews(
                keyWords = keyWords,
                country = country,
                category = category,
                pageSize = pageSize,
                page = page
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5 * 1000L),
                initialValue = NetworkResult.Loading()
            )
            .also { stateFlow: StateFlow<NetworkResult<NewsTopHeadlinesDTO>> ->
                viewModelScope.launch {
                    stateFlow.collect { result: NetworkResult<NewsTopHeadlinesDTO> ->
                        handleTopHeadlineNewsData(result)
                    }
                }
            }
    }

    private fun handleEverythingNewsData(result: NetworkResult<NewsEverythingDTO>) {
        when (result) {
            is NetworkResult.Loading -> {
                LogUtil.debug(TAG, "[Handle Everything News Data] Loading...")
                setState(_everythingNewsDataStateFlow, EverythingNewsDataState.Fetching)
            }

            is NetworkResult.Success -> {
                result.data?.also {
                    LogUtil.debug(TAG, "[Handle Everything News Data] Succeed, data: $it")
                    setState(_everythingNewsDataStateFlow, EverythingNewsDataState.FetchSucceed(it.articles?.toNewsArticleModelList()))
                } ?: {
                    LogUtil.warning(TAG, "[Handle Everything News Data] Succeed without data")
                    setState(_everythingNewsDataStateFlow, EverythingNewsDataState.NoNewsData)
                }
            }

            is NetworkResult.Failed -> {
                val errorCode: Int? = result.code
                val errorMessage: String? = result.message
                LogUtil.error(TAG, "[Handle Everything News Data] Failed, ($errorCode, $errorMessage)")
                setState(_everythingNewsDataStateFlow, EverythingNewsDataState.FetchFailed(errorCode, errorMessage))
            }
        }
    }

    private fun handleTopHeadlineNewsData(result: NetworkResult<NewsTopHeadlinesDTO>) {
        when (result) {
            is NetworkResult.Loading -> {
                LogUtil.debug(TAG, "[Handle Top Headline News Data] Loading...")
                setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.Fetching)
            }

            is NetworkResult.Success -> {
                result.data?.also {
                    LogUtil.debug(TAG, "[Handle Top Headline News Data] Succeed, data: $it")
                    setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.FetchSucceed(it.articles?.toNewsArticleModelList()))
                } ?: {
                    LogUtil.warning(TAG, "[Handle Top Headline News Data] Succeed without data")
                    setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.NoNewsData)
                }
            }

            is NetworkResult.Failed -> {
                val errorCode: Int? = result.code
                val errorMessage: String? = result.message
                LogUtil.error(TAG, "[Handle Top Headline News Data] Failed, ($errorCode, $errorMessage)")
                setState(_topHeadlineNewsDataStateFlow, TopHeadlineNewsDataState.FetchFailed(errorCode, errorMessage))
            }
        }
    }
}