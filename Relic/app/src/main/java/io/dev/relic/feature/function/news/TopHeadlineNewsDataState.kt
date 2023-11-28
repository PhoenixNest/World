package io.dev.relic.feature.function.news

import io.data.model.news.NewsArticleModel

sealed class TopHeadlineNewsDataState {

    /* Common */

    data object Init : TopHeadlineNewsDataState()

    data object Empty : TopHeadlineNewsDataState()

    data object NoNewsData : TopHeadlineNewsDataState()

    /* Loading */

    data object Fetching : TopHeadlineNewsDataState()

    /* Succeed */

    data class FetchSucceed(
        val modelList: List<NewsArticleModel?>?
    ) : TopHeadlineNewsDataState()

    /* Failed */

    data class FetchFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : TopHeadlineNewsDataState()

}
