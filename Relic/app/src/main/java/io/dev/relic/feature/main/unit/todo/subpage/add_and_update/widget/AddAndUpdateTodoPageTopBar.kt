package io.dev.relic.feature.main.unit.todo.subpage.add_and_update.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.dev.relic.global.RelicConstants.ComposeUi.DEFAULT_DESC
import io.dev.relic.ui.theme.RelicFontFamily

@Composable
fun AddAndUpdateTodoPageTopBar(
    onBackClick: () -> Unit,
    onFinishClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = modifier.weight(1F)
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = DEFAULT_DESC
            )
        }

        Text(
            text = title,
            modifier = modifier.weight(2F),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = TextStyle(
                color = Color.DarkGray,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = RelicFontFamily.ubuntu,
                textAlign = TextAlign.Center
            )
        )

        IconButton(
            onClick = onFinishClick,
            modifier = modifier.weight(1F)
        ) {
            Icon(
                imageVector = Icons.Rounded.Done,
                contentDescription = DEFAULT_DESC
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CreateTodoPageTopBarPreview() {
    AddAndUpdateTodoPageTopBar(
        onBackClick = {},
        onFinishClick = {},
        title = "Hello World"
    )
}