package io.dev.relic.feature.pages.studio.ui.widget

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.common.util.TimeUtil
import io.data.model.todo.TodoDataModel
import io.dev.relic.feature.function.todo.TodoDataState
import io.dev.relic.feature.function.todo.ui.TodoCommonComponent
import io.dev.relic.feature.function.todo.ui.TodoNoDataComponent
import io.dev.relic.feature.pages.studio.StudioTodoAction
import io.dev.relic.feature.pages.studio.StudioTodoListState
import io.dev.relic.feature.pages.studio.StudioTotoState

@Suppress("FunctionName")
fun LazyListScope.StudioTodoPanel(
    todoState: StudioTotoState,
    onAddClick: () -> Unit,
    onItemClick: (data: TodoDataModel) -> Unit,
    onTailClick: () -> Unit
) {
    item {
        StudioTodoPanelContent(
            todoState = todoState,
            onTodoCreateClick = onAddClick,
            onTodoItemClick = onItemClick,
            onTodoTailClick = onTailClick
        )
    }
}

@Composable
private fun StudioTodoPanelContent(
    todoState: StudioTotoState,
    onTodoCreateClick: () -> Unit,
    onTodoItemClick: (data: TodoDataModel) -> Unit,
    onTodoTailClick: () -> Unit
) {
    when (val state = todoState.dataState) {
        is TodoDataState.Init,
        is TodoDataState.Querying -> {
            //
        }

        is TodoDataState.NoTodoData,
        is TodoDataState.Empty -> {
            TodoNoDataComponent(onCreateClick = onTodoCreateClick)
        }

        is TodoDataState.QuerySucceed -> {
            TodoCommonComponent(
                modelList = state.modelList,
                onItemClick = onTodoItemClick,
                onTailClick = onTodoTailClick,
                lazyListState = todoState.listState.todoListState
            )
        }

        is TodoDataState.QueryFailed -> {
            //
        }
    }
}

@Composable
@Preview
private fun StudioTodoPanelWithoutDataPreview() {
    StudioTodoPanelContent(
        todoState = StudioTotoState(
            dataState = TodoDataState.NoTodoData,
            action = StudioTodoAction(
                onAddClick = {},
                onItemClick = {},
                onTailClick = {}
            ),
            listState = StudioTodoListState(
                todoListState = rememberLazyListState()
            )
        ),
        onTodoCreateClick = {},
        onTodoItemClick = {},
        onTodoTailClick = {}
    )
}

@Composable
@Preview
private fun StudioTodoPanelWithDataPreview() {
    val tempList = mutableListOf<TodoDataModel>()
    repeat(10) {
        tempList.add(
            TodoDataModel(
                title = "Todo Task Title",
                subtitle = "Subtitle",
                content = "Material 3 is the latest version of Google’s open-source design system. Design and build beautiful, usable products with Material 3.",
                priority = 0,
                color = 0xFFDEB654,
                updateTime = TimeUtil.getCurrentTime().toString(),
                isFinish = true
            )
        )
    }

    StudioTodoPanelContent(
        todoState = StudioTotoState(
            dataState = TodoDataState.QuerySucceed(modelList = tempList),
            action = StudioTodoAction(
                onAddClick = {},
                onItemClick = {},
                onTailClick = {}
            ),
            listState = StudioTodoListState(
                todoListState = rememberLazyListState()
            )
        ),
        onTodoCreateClick = {},
        onTodoItemClick = {},
        onTodoTailClick = {}
    )
}