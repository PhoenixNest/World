package io.dev.relic.feature.function.todo

import io.data.model.todo.TodoDataModel

sealed interface TodoDataState {

    /* Common */

    data object Init : TodoDataState

    data object Empty : TodoDataState

    data object NoTodoData : TodoDataState

    /* Loading */

    data object Querying : TodoDataState

    /* Succeed */

    data class QuerySucceed(
        val modelList: List<TodoDataModel?>?
    ) : TodoDataState

    /* Failed */

    data class QueryFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : TodoDataState

}