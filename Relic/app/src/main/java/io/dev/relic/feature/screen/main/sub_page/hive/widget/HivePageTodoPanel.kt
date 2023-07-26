package io.dev.relic.feature.screen.main.sub_page.hive.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.dev.relic.domain.model.todo.TodoDataModel
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainTextColor

private const val MAX_TODO_PANEL_LIST_CONTENT_SIZE = 6

@Composable
fun HivePageTodoPanel(
    todoDataList: List<TodoDataModel>,
    onNavigateToTodo: () -> Unit,
    onTodoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.Start
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            HivePageTodoPanelIntro(todoDataListSize = todoDataList.size)
        }
        items(
            items = todoDataList.take(MAX_TODO_PANEL_LIST_CONTENT_SIZE),
        ) { todoData: TodoDataModel ->
            HivePageTodoPanelCard(todoData = todoData)
        }
    }
}

@Composable
private fun HivePageTodoPanelIntro(
    todoDataListSize: Int,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val cardWidth: Dp = (configuration.screenWidthDp - 8 / 3).dp

    Card(
        modifier = modifier
            .width(cardWidth)
            .height(72.dp),
        backgroundColor = Color.LightGray,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Unfinished Todo",
                style = TextStyle(
                    color = mainTextColor,
                    fontSize = 24.sp,
                    fontFamily = RelicFontFamily.fasthand
                )
            )
        }
    }
}

@Composable
private fun HivePageTodoPanelCard(
    todoData: TodoDataModel,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val cardWidth: Dp = (configuration.screenWidthDp - 8 / 3).dp
    todoData.run {
        Card(
            modifier = modifier
                .width(cardWidth)
                .height(72.dp)
        ) {

        }
    }
}

@Composable
@Preview
private fun HivePageTodoPanelPreview() {
    HivePageTodoPanel(
        todoDataList = TodoDataModel.testTodoDataList(),
        onNavigateToTodo = {},
        onTodoClick = {}
    )
}