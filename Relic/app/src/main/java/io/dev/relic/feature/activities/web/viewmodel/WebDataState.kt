package io.dev.relic.feature.activities.web.viewmodel

sealed class WebDataState {

    /* Common */

    data object Init : WebDataState()

    data object Empty : WebDataState()

    data object NoWebData : WebDataState()

    /* Loading */

    data object Fetching : WebDataState()

    /* Succeed */

    data object FetchSucceed : WebDataState()

    /* Failed */

    data object FetchFailed : WebDataState()

}