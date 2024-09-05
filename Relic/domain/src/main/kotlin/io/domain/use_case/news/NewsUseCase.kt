package io.domain.use_case.news

import io.domain.use_case.news.action.everything.CacheTrendingNewsData
import io.domain.use_case.news.action.everything.GetTrendingNewsData
import io.domain.use_case.news.action.everything.QueryAllTrendingNewsData
import io.domain.use_case.news.action.top_headline.CacheTopHeadlineNewsData
import io.domain.use_case.news.action.top_headline.GetTopHeadlineNewsData
import io.domain.use_case.news.action.top_headline.QueryAllTopHeadlineNewsData

internal const val TAG = "NewsUseCase"

data class NewsUseCase(
    val getTrendingNewsData: GetTrendingNewsData,
    val getTopHeadlineNews: GetTopHeadlineNewsData,
    val queryAllTrendingNewsData: QueryAllTrendingNewsData,
    val queryAllTopHeadlineNewsData: QueryAllTopHeadlineNewsData,
    val cacheTrendingNewsData: CacheTrendingNewsData,
    val cacheTopHeadlineNewsData: CacheTopHeadlineNewsData
)