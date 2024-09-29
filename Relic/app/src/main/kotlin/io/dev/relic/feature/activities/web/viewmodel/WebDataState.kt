package io.dev.relic.feature.activities.web.viewmodel

sealed class WebDataState {

    /* Common */

    data object Init : WebDataState()

    data object Empty : WebDataState()

    data object NoWebData : WebDataState()

    /* Loading */

    data class Fetching(
        val latestProgress: Int
    ) : WebDataState()

    /* Succeed */

    data object FetchSucceed : WebDataState()

    /* Failed */

    data class FetchFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : WebDataState()

}