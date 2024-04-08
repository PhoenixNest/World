package io.dev.relic.feature.function.todo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.util.TimeUtil
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainTextColor
import io.data.model.todo.TodoDataModel

@Composable
fun TodoCommonComponent(
    modelList: List<TodoDataModel?>,
    onItemClick: (data: TodoDataModel) -> Unit,
    onTailClick: () -> Unit,
    lazyListState: LazyListState
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        TodoComponentTitle()
        Spacer(modifier = Modifier.height(16.dp))
        TodoComponentRowList(
            modelList = modelList,
            onItemClick = onItemClick,
            onTailClick = onTailClick,
            lazyListState = lazyListState
        )
    }
}

@Composable
private fun TodoComponentTitle(title: String = "🚀 My Task") {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.align(Alignment.CenterStart),
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
private fun TodoComponentRowList(
    modelList: List<TodoDataModel?>,
    onItemClick: (data: TodoDataModel) -> Unit,
    onTailClick: () -> Unit,
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
                val isReachTheEnd = (index == modelList.size - 1)
                val itemDecorationModifier = Modifier.padding(
                    start = if (index == 0) 16.dp else 0.dp
                )
                TodoRowItem(
                    data = data,
                    onCardClick = { onItemClick.invoke(data) },
                    modifier = itemDecorationModifier
                )
                if (isReachTheEnd) {
                    TodoTailItem(
                        onItemClick = onTailClick,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun TodoCommonComponentPreview() {
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

    TodoCommonComponent(
        modelList = tempList,
        onItemClick = {},
        onTailClick = {},
        lazyListState = rememberLazyListState()
    )
}