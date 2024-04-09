package io.dev.relic.feature.pages.studio.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.theme.mainThemeColorAccent
import io.core.ui.theme.mainThemeColorLight
import io.data.model.todo.TodoDataModel
import io.dev.relic.feature.function.todo.TodoDataState
import io.dev.relic.feature.pages.studio.StudioTodoAction
import io.dev.relic.feature.pages.studio.StudioTodoListState
import io.dev.relic.feature.pages.studio.StudioTotoState
import io.dev.relic.feature.pages.studio.ui.widget.StudioTabBar
import io.dev.relic.feature.pages.studio.ui.widget.StudioTodoPanel

@Composable
fun StudioPageContent(
    onUserClick: () -> Unit,
    todoState: StudioTotoState,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mainThemeColorAccent
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            StudioTabBar(onUserClick = onUserClick)
            StudioPageContent(
                todoState = todoState,
                onAddClick = todoState.action.onAddClick,
                onItemClick = todoState.action.onItemClick,
                onTailClick = todoState.action.onTailClick
            )
        }
    }
}

@Composable
private fun StudioPageContent(
    todoState: StudioTotoState,
    onAddClick: () -> Unit,
    onItemClick: (data: TodoDataModel) -> Unit,
    onTailClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = mainThemeColorLight.copy(alpha = 0.1F),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        item { Spacer(modifier = Modifier.height(16.dp)) }
        StudioTodoPanel(
            todoState = todoState,
            onTodoCreateClick = onAddClick,
            onTodoItemClick = onItemClick,
            onTodoTailClick = onTailClick
        )
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
@Preview
private fun StudioPageContentPreview() {
    StudioPageContent(
        onUserClick = {},
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
        )
    )
}