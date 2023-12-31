package io.dev.relic.feature.function.news

import io.data.model.news.NewsArticleModel

sealed class EverythingNewsDataState {

    /* Common */

    data object Init : EverythingNewsDataState()

    data object Empty : EverythingNewsDataState()

    data object NoNewsData : EverythingNewsDataState()

    /* Loading */

    data object Fetching : EverythingNewsDataState()

    /* Succeed */

    data class FetchSucceed(
        val modelList: List<NewsArticleModel?>?
    ) : EverythingNewsDataState()

    /* Failed */

    data class FetchFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : EverythingNewsDataState()

}
