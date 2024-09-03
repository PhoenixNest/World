package io.dev.relic.feature.pages.studio

import androidx.compose.foundation.lazy.LazyListState
import io.data.model.todo.TodoDataModel
import io.dev.relic.feature.function.todo.TodoDataState

/**
 * According to the page ui, the order of list state will be:
 *
 * @param todoListState      For todo list (horizontal).
 * */
data class StudioListState(
    val todoListState: LazyListState
)

/* ======================== Todo ======================== */

/**
 * Ui and Data state for Todo unit in studio page.
 *
 * @param dataState      For todo list (horizontal).
 * @param action         Ui action handler for Todo unit in studio page.
 * @param listState      Ui state of list.
 *
 * @see StudioListState
 * */
data class StudioTotoState(
    val dataState: TodoDataState,
    val action: StudioTodoAction,
    val listState: StudioListState
)

/**
 * According to the page ui, the class is use for handle the ui action in Todo unit.
 *
 * @param onCheckTodoClick  Navigate to the main page of todo function.
 * @param onAddClick        Click the add button to add new todo task.
 * @param onItemClick       Click the todo item to read the detail or modify the todo task.
 * @param onTailClick       Click the tail "add" card to add new todo task.
 * */
data class StudioTodoAction(
    val onCheckTodoClick: () -> Unit,
    val onAddClick: () -> Unit,
    val onItemClick: (data: TodoDataModel) -> Unit,
    val onTailClick: () -> Unit
)

/* ======================== Agent ======================== */

/**
 * Ui and Data state for Agent unit in studio page.
 *
 * @param action         Ui action handler for Agent unit in studio page.
 * */
data class StudioAgentState(
    val action: StudioAgentAction
)

/**
 * According to the page ui, the class is use for handle the ui action in Todo unit.
 *
 * @param onStartChatClick       Click the tail "Start Chat" card to add new todo task.
 * */
data class StudioAgentAction(
    val onStartChatClick: () -> Unit
)

/* ======================== Gallery ======================== */

data class StudioGalleryState(
    val action: StudioGalleryAction
)

data class StudioGalleryAction(
    val onStartPreviewClick: () -> Unit
)

/* ======================== Map ======================== */

data class StudioMapState(
    val action: StudioMapAction
)

data class StudioMapAction(
    val onStartExploreClick: () -> Unit
)