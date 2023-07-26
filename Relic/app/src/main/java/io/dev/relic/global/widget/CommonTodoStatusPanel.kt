package io.dev.relic.global.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.dev.relic.domain.model.todo.TodoDataModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CommonTodoStatusPanel(
    dataList: List<TodoDataModel>,
    onItemClick: (TodoDataModel) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.Start
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        dataList.forEach {
            CommonTodoStatusItem(
                dataModel = it,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun CommonTodoStatusItem(
    dataModel: TodoDataModel,
    onItemClick: (TodoDataModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        dataModel.run {
            //
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CommonTodoStatusPanelPreview() {
    CommonTodoStatusPanel(
        dataList = emptyList(),
        onItemClick = {}
    )
}