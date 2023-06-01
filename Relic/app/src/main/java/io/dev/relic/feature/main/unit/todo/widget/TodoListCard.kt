package io.dev.relic.feature.main.unit.todo.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.dev.relic.R
import io.dev.relic.domain.model.todo.TodoDataModel
import io.dev.relic.global.utils.TimeUtil.getCurrentTimeInMillis
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainTextColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoListCard(
    todoDataModel: TodoDataModel,
    onItemClick: (TodoDataModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = {
            onItemClick.invoke(todoDataModel)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 2.dp
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            TodoListCardInfoBar(
                levelColor = todoDataModel.color
            )
            Spacer(modifier = modifier.height(8.dp))
            TodoListCardTitleBar(
                title = todoDataModel.title,
                subTitle = todoDataModel.subTitle
            )
            Divider(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = Color.DarkGray
            )
            TodoListCardContent(
                content = todoDataModel.content
            )
        }
    }
}

@Composable
private fun TodoListCardInfoBar(
    levelColor: Long,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = modifier.size(32.dp)) {
            drawCircle(
                color = Color(color = levelColor),
                radius = 32F
            )
        }
    }
}

@Composable
private fun TodoListCardTitleBar(
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            modifier = modifier.fillMaxWidth(),
            style = TextStyle(
                color = mainTextColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = RelicFontFamily.ubuntu
            )
        )
        Spacer(modifier = modifier.height(6.dp))
        Text(
            text = subTitle,
            modifier = modifier.fillMaxWidth(),
            style = TextStyle(
                color = mainTextColor,
                fontSize = 18.sp,
                fontFamily = RelicFontFamily.ubuntu
            )
        )
    }
}

@Composable
private fun TodoListCardContent(
    content: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = content,
        modifier = modifier.fillMaxWidth(),
        lineHeight = 16.sp,
        overflow = TextOverflow.Ellipsis,
        maxLines = 5,
        style = TextStyle(
            color = mainTextColor,
            fontFamily = RelicFontFamily.ubuntu
        )
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF444444)
private fun TodoListCardPreview() {
    TodoListCard(
        todoDataModel = TodoDataModel(
            title = "Title",
            subTitle = "Sub-Title",
            content = stringResource(id = R.string.open_source_license),
            priority = -1,
            color = 0xFF21313A,
            timeStamp = getCurrentTimeInMillis(),
            isFinish = false
        ),
        onItemClick = {
            //
        }
    )
}