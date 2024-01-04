package io.dev.relic.feature.function.todo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.util.TimeUtil
import io.core.ui.CommonNoDataComponent
import io.core.ui.dialog.CommonItemDivider
import io.core.ui.theme.RelicFontFamily.newsReader
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainBackgroundColor
import io.core.ui.theme.mainTextColor
import io.data.model.todo.TodoDataModel

@Composable
fun TodoCardPanel(
    modelList: List<TodoDataModel?>?,
    onCardClick: (data: TodoDataModel) -> Unit,
    lazyListState: LazyListState
) {
    if (modelList.isNullOrEmpty()) {
        CommonNoDataComponent()
    } else {
        TodoCardList(
            modelList = modelList,
            onCardClick = onCardClick,
            lazyListState = lazyListState
        )
    }
}

@Composable
private fun TodoCardList(
    modelList: List<TodoDataModel?>,
    onCardClick: (data: TodoDataModel) -> Unit,
    lazyListState: LazyListState
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.Top
        ),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        itemsIndexed(items = modelList) { index, data ->
            if (data == null) {
                //
            } else {
                val itemDecorationModifier = Modifier.padding(
                    top = if (index == 0) 16.dp else 0.dp,
                    bottom = if (index == modelList.size - 1) 120.dp else 0.dp
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
fun TodoCardItem(
    data: TodoDataModel,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    data.apply {
        Surface(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(240.dp),
            shape = RoundedCornerShape(16.dp),
            color = mainBackgroundColor.copy(alpha = 0.3F)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onCardClick.invoke() }
                    .padding(20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                TodoIntro(
                    title = title,
                    priority = priority,
                    colorHex = color,
                    updateTime = updateTime
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = subtitle,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        color = mainTextColor,
                        fontSize = 16.sp,
                        fontFamily = ubuntu,
                        fontWeight = FontWeight.Bold
                    )
                )
                CommonItemDivider(horizontalMargin = 0.dp)
                Text(
                    text = content,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        color = mainTextColor,
                        fontFamily = ubuntu,
                        lineHeight = TextUnit(
                            value = 1.6F,
                            type = TextUnitType.Em
                        )
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun TodoIntro(
    title: String,
    priority: Int,
    colorHex: Long,
    updateTime: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = Color(colorHex),
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = updateTime,
                maxLines = 1,
                style = TextStyle(
                    color = mainTextColor,
                    fontFamily = ubuntu
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    color = mainTextColor,
                    fontSize = 24.sp,
                    fontFamily = newsReader
                )
            )
        }
    }
}

@Composable
@Preview
private fun TodoCardPreview() {
    TodoCardItem(
        data = TodoDataModel(
            title = "Todo Task Title",
            subtitle = "Subtitle",
            content = "Material 3 is the latest version of Google’s open-source design system. Design and build beautiful, usable products with Material 3.",
            priority = 0,
            color = 0xFF000000,
            updateTime = TimeUtil.getCurrentTime().toString(),
            isFinish = true
        ),
        onCardClick = {}
    )
}