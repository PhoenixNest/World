package io.dev.relic.feature.main.unit.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import io.dev.relic.domain.model.todo.TodoDataModel
import io.dev.relic.feature.main.unit.todo.viewmodel.TodoViewModel
import io.dev.relic.feature.main.unit.todo.widget.TodoExtFAB
import io.dev.relic.feature.main.unit.todo.widget.TodoListCard
import io.dev.relic.feature.main.unit.todo.widget.TodoPageTopBar

@Composable
fun TodoPageRoute(
    onBackClick: () -> Unit,
    onItemClick: () -> Unit,
    onCreateClick: () -> Unit,
    viewModel: TodoViewModel = hiltViewModel()
) {
    TodoPage(
        onBackClick = onBackClick,
        onItemClick = {
            // TODO
        },
        onSortClick = {
            // TODO
        },
        onCreateClick = onCreateClick
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TodoPage(
    onBackClick: () -> Unit,
    onItemClick: (TodoDataModel) -> Unit,
    onSortClick: () -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TodoPageTopBar(
                onBackClick = onBackClick,
                onSortClick = onSortClick
            )
        },
        floatingActionButton = {
            TodoExtFAB(onClick = onCreateClick)
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues: PaddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                FlowRow(
                    modifier = modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    maxItemsInEachRow = 2
                ) {
                    TodoDataModel.getTestTodosData().forEach {
                        TodoListCard(
                            todoDataModel = it,
                            onItemClick = {
                                onItemClick.invoke(it)
                            },
                            modifier = modifier.weight(1F)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun TodoPagePreview() {
    TodoPage(
        onBackClick = {},
        onItemClick = {},
        onSortClick = {},
        onCreateClick = {}
    )
}