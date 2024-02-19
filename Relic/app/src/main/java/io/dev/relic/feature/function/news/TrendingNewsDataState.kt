package io.dev.relic.feature.function.news

import io.data.model.news.NewsArticleModel

sealed class TrendingNewsDataState {

    /* Common */

    data object Init : TrendingNewsDataState()

    data object Empty : TrendingNewsDataState()

    data object NoNewsData : TrendingNewsDataState()

    /* Loading */

    data object Fetching : TrendingNewsDataState()

    /* Succeed */

    data class FetchSucceed(
        val modelList: List<NewsArticleModel?>?
    ) : TrendingNewsDataState()

    /* Failed */

    data class FetchFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : TrendingNewsDataState()

}
