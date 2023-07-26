package io.dev.relic.feature.screen.main.sub_page.hive

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.dev.relic.core.data.database.entity.TodoEntity
import io.dev.relic.core.data.database.mappers.TodoDataMapper.toTodoDataList
import io.dev.relic.domain.model.todo.TodoDataModel
import io.dev.relic.feature.screen.main.sub_page.hive.viewmodel.HiveViewModel
import io.dev.relic.feature.screen.main.sub_page.hive.widget.HivePageTodoPanel
import io.dev.relic.feature.screen.main.sub_page.hive.widget.HivePageUserPanel

@Composable
fun HivePageRoute(
    onNavigateToMine: () -> Unit,
    onNavigateToTodo: () -> Unit,
    onTodoClick: () -> Unit,
    hiveViewModel: HiveViewModel = hiltViewModel()
) {
    val todoData: List<TodoEntity> by hiveViewModel.todoData.collectAsStateWithLifecycle()

    HivePage(
        todoDataList = todoData.toTodoDataList().filter {
            it.isFinish.not()
        },
        onNavigateToMine = onNavigateToMine,
        onNavigateToTodo = onNavigateToTodo,
        onTodoClick = onTodoClick
    )
}

@Composable
private fun HivePage(
    todoDataList: List<TodoDataModel>,
    onNavigateToMine: () -> Unit,
    onNavigateToTodo: () -> Unit,
    onTodoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        HivePageUserPanel(onNavigateToMine = onNavigateToMine)
        HivePageTodoPanel(
            todoDataList = todoDataList,
            onNavigateToTodo = onNavigateToTodo,
            onTodoClick = onTodoClick
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun HivePagePreview() {
    HivePage(
        todoDataList = TodoDataModel.getTestTodosData(),
        onNavigateToMine = {},
        onNavigateToTodo = {},
        onTodoClick = {}
    )
}