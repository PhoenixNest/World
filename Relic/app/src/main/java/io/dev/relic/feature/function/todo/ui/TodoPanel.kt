package io.dev.relic.feature.function.todo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.common.util.TimeUtil
import io.data.model.todo.TodoDataModel

@Composable
fun TodoPanel(
    modelList: List<TodoDataModel?>,
    onItemClick: (data: TodoDataModel) -> Unit,
    lazyListState: LazyListState
) {
    TodoCardList(
        modelList = modelList,
        onCardClick = onItemClick,
        lazyListState = lazyListState
    )
}

@Composable
private fun TodoCardList(
    modelList: List<TodoDataModel?>,
    onCardClick: (data: TodoDataModel) -> Unit,
    lazyListState: LazyListState
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        state = lazyListState,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.Start
        )
    ) {
        itemsIndexed(items = modelList) { index, data ->
            if (data != null) {
                val itemDecorationModifier = Modifier.padding(
                    start = if (index == 0) 16.dp else 0.dp,
                    end = if (index == modelList.size - 1) 16.dp else 0.dp
                )
                TodoCardItem(
                    data = data,
                    onCardClick = { onCardClick.invoke(data) },
                    modifier = itemDecorationModifier
                )
            }
        }
    }
}

@Composable
@Preview
private fun TodoPanelPreview() {
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

    TodoPanel(
        modelList = tempList,
        onItemClick = {},
        lazyListState = rememberLazyListState()
    )
}