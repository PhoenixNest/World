package io.dev.relic.feature.function.todo.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.core.ui.theme.RelicFontFamily.googleSans
import io.core.ui.widget.CommonInputField
import io.data.entity.todo.TodoEntity
import io.dev.relic.R
import io.dev.relic.feature.function.todo.util.TodoPriority
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TodoForm(
    onSubmit: (entity: TodoEntity) -> Unit,
    modifier: Modifier = Modifier
) {

    var title by remember {
        mutableStateOf("")
    }

    var subtitle by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var priority by remember {
        mutableIntStateOf(0)
    }

    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 24.dp,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.todo_create_form_title),
                fontFamily = googleSans,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            LazyColumn(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.Top
                ),
                horizontalAlignment = Alignment.Start
            ) {
                TodoFormTitleItem(labelResId = R.string.todo_create_title_label)
                TodoFormInputField(
                    content = title,
                    hintResId = R.string.todo_create_title_hint,
                    onValueChange = { title = it }
                )
                TodoFormTitleItem(labelResId = R.string.todo_create_subtitle_label)
                TodoFormInputField(
                    content = subtitle,
                    hintResId = R.string.todo_create_subtitle_hint,
                    onValueChange = { subtitle = it }
                )
                TodoFormTitleItem(labelResId = R.string.todo_create_description_hint)
                TodoFormInputField(
                    content = description,
                    hintResId = R.string.todo_create_description_hint,
                    onValueChange = { description = it },
                    inputFieldHeight = (52 * 2).dp
                )
                TodoFormTitleItem(labelResId = R.string.todo_create_priority_label)
                TodoFormPriorityPicker(
                    currentSelectedIndex = priority,
                    onItemClick = { priority = it.ordinal }
                )
                TodoFormUpdateTimeIndicator()
            }
            TodoFormSubmitButton(
                onClick = {
                    onSubmit.invoke(
                        TodoEntity(
                            title = title,
                            subtitle = subtitle,
                            content = description,
                            priority = priority,
                            isFinish = false
                        )
                    )
                }
            )
        }
    }
}

@Suppress("FunctionName")
private fun LazyListScope.TodoFormTitleItem(@StringRes labelResId: Int) {
    item {
        Text(
            text = stringResource(labelResId),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = googleSans,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Suppress("FunctionName")
private fun LazyListScope.TodoFormInputField(
    content: String,
    @StringRes hintResId: Int,
    onValueChange: (inputString: String) -> Unit,
    inputFieldHeight: Dp = 52.dp
) {
    item {
        CommonInputField(
            content = content,
            hintResId = hintResId,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(inputFieldHeight)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp),
            contentTextStyle = TextStyle(MaterialTheme.colorScheme.onSurface),
            hintTextStyle = TextStyle(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F))
        )
    }
}


@Suppress("FunctionName")
private fun LazyListScope.TodoFormPriorityPicker(
    currentSelectedIndex: Int,
    onItemClick: (priority: TodoPriority) -> Unit
) {
    item {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.Start
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(TodoPriority.entries.toList()) { priority ->
                TodoFormPriorityItem(
                    isSelected = (currentSelectedIndex == priority.ordinal),
                    priority = priority,
                    onClick = { onItemClick.invoke(priority) }
                )
            }
        }
    }
}

@Suppress("FunctionName")
private fun LazyListScope.TodoFormUpdateTimeIndicator() {
    item {
        val currentDateTime = LocalDateTime.now()
        val formatedDataTime = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            .format(currentDateTime) ?: currentDateTime.toString()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = stringResource(R.string.todo_create_last_modified_time, formatedDataTime),
                fontFamily = googleSans,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun TodoFormPriorityItem(
    isSelected: Boolean,
    priority: TodoPriority,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceContainerHighest
    }

    val textColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(32.dp),
        border = if (isSelected) BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary
        ) else null
    ) {
        Row(
            modifier = Modifier
                .clickable { onClick.invoke() }
                .padding(
                    horizontal = 12.dp,
                    vertical = 6.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(
                space = 6.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = priority.emoji)
            Text(
                text = priority.name,
                color = textColor,
                fontFamily = googleSans,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun TodoFormSubmitButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
            disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor
        )
    ) {
        Text(
            text = stringResource(R.string.todo_create_submit_button),
            modifier = Modifier.padding(12.dp),
            fontWeight = FontWeight.Bold,
            fontFamily = googleSans,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun TodoFormPreview() {
    TodoForm(onSubmit = {})
}