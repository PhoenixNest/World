package io.dev.relic.feature.screen.todo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.dev.relic.R
import io.dev.relic.core.data.database.entity.TodoEntity
import io.dev.relic.core.data.database.mappers.TodoDataMapper.toTodoDataList
import io.dev.relic.domain.model.todo.TodoDataModel
import io.dev.relic.feature.screen.todo.viewmodel.TodoViewModel
import io.dev.relic.feature.screen.todo.widget.TodoListCard
import io.dev.relic.global.RelicConstants.ComposeUi.DEFAULT_DESC
import io.dev.relic.global.widget.CommonTopBar
import io.dev.relic.ui.theme.RelicFontFamily

private const val MAX_ITEM_PER_ROW = 2

@Composable
fun TodoScreenRoute(
    onBackClick: () -> Unit,
    onItemClick: (TodoDataModel) -> Unit,
    onCreateClick: () -> Unit,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val todoData: List<TodoEntity> by viewModel.todoStateFlow.collectAsStateWithLifecycle()

    TodoScreen(
        dataList = todoData.toTodoDataList(),
        onBackClick = onBackClick,
        onItemClick = onItemClick,
        onSortClick = viewModel::sortTodoDataList,
        onCreateClick = onCreateClick
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TodoScreen(
    dataList: List<TodoDataModel>,
    onBackClick: () -> Unit,
    onItemClick: (TodoDataModel) -> Unit,
    onSortClick: () -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(
                        text = stringResource(id = R.string.todo_ext_fab_label),
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = RelicFontFamily.ubuntu
                        )
                    )
                },
                onClick = onCreateClick,
                modifier = modifier,
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.EditNote,
                        contentDescription = DEFAULT_DESC,
                        tint = Color.White
                    )
                },
                backgroundColor = Color.DarkGray
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues: PaddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CommonTopBar(
                onBackClick = onBackClick,
                hasTitle = true,
                titleResId = R.string.todo_title,
                tailContent = {
                    IconButton(onClick = onSortClick) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = DEFAULT_DESC
                        )
                    }
                }
            )
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(MAX_ITEM_PER_ROW),
                modifier = modifier.fillMaxSize(),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.Start
                )
            ) {
                items(dataList) { dataModel: TodoDataModel ->
                    TodoListCard(
                        todoDataModel = dataModel,
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun TodoScreenPreview() {
    TodoScreen(
        dataList = TodoDataModel.testTodoDataList(),
        onBackClick = {},
        onItemClick = {},
        onSortClick = {},
        onCreateClick = {}
    )
}