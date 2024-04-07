package io.dev.relic.feature.function.todo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import io.core.ui.dialog.CommonItemDivider
import io.core.ui.theme.RelicFontFamily
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColorLight
import io.data.model.todo.TodoDataModel

@Composable
fun TodoCardItem(
    data: TodoDataModel,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    data.apply {
        Surface(
            modifier = modifier
                .width(320.dp)
                .height(240.dp),
            shape = RoundedCornerShape(16.dp),
            color = mainThemeColorLight.copy(alpha = 0.1F)
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
                        fontFamily = RelicFontFamily.ubuntu,
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
                        fontFamily = RelicFontFamily.ubuntu,
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
                    fontFamily = RelicFontFamily.ubuntu
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
                    fontFamily = RelicFontFamily.newsReader
                )
            )
        }
    }
}

@Composable
@Preview
private fun TodoCardItemPreview() {
    TodoCardItem(
        data = TodoDataModel(
            title = "Todo Task Title",
            subtitle = "Subtitle",
            content = "Material 3 is the latest version of Google’s open-source design system. Design and build beautiful, usable products with Material 3.",
            priority = 0,
            color = 0xFFDEB654,
            updateTime = TimeUtil.getCurrentTime().toString(),
            isFinish = true
        ),
        onCardClick = {}
    )
}