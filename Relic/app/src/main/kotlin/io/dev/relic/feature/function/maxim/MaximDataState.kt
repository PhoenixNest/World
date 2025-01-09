package io.dev.relic.feature.function.maxim

import io.data.model.maxim.MaximModel

sealed interface MaximDataState {

    /* Common */

    data object Init : MaximDataState

    data object Empty : MaximDataState

    data object NoMaximData : MaximDataState

    /* Loading */

    data object Fetching : MaximDataState

    /* Succeed */

    data class FetchSucceed(
        val data: MaximModel
    ) : MaximDataState

    /* Failed */

    data class FetchFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : MaximDataState
}