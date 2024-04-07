package io.dev.relic.feature.pages.studio

import androidx.compose.foundation.lazy.LazyListState
import io.dev.relic.feature.function.todo.TodoDataState

/**
 * Data state for home page.
 *
 * @param todoDataState      For todo list (horizontal).
 * @param listState          Ui state of list.
 *
 * @see StudioTodoListState
 * */
data class StudioTotoState(
    val todoDataState: TodoDataState,
    val listState: StudioTodoListState
)

/**
 * According to the page ui, the order of list state will be:
 *
 * @param todoListState      For todo list (horizontal).
 * */
data class StudioTodoListState(
    val todoListState: LazyListState
)