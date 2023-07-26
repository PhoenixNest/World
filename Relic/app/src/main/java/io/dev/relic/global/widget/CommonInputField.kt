package io.dev.relic.global.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.dev.relic.R
import io.dev.relic.ui.theme.RelicFontFamily

@Composable
fun CommonInputField(
    content: String,
    @StringRes hintResId: Int,
    onValueChange: (inputValue: String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    isEnabled: Boolean = true,
    maxLines: Int = Int.MAX_VALUE
) {
    BasicTextField(
        value = content,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = isEnabled,
        textStyle = textStyle.copy(
            color = Color.DarkGray,
            fontFamily = RelicFontFamily.ubuntu,
            textAlign = TextAlign.Start
        ),
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        decorationBox = { innerTextField: @Composable () -> Unit ->
            if (content.isEmpty() || content.isBlank()) {
                Text(
                    text = stringResource(id = hintResId),
                    style = textStyle.copy(
                        color = Color.LightGray,
                        fontFamily = RelicFontFamily.ubuntu,
                        textAlign = TextAlign.Start
                    )
                )
            }
            innerTextField()
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun CommonInputFieldNoContentPreview() {
    Box(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CommonInputField(
            content = "",
            hintResId = R.string.todo_create_title_hint,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.LightGray.copy(alpha = 0.3F),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(20.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun CommonInputFieldPreview() {
    Box(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CommonInputField(
            content = stringResource(id = R.string.app_name),
            hintResId = R.string.todo_create_title_hint,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.LightGray.copy(alpha = 0.3F),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(20.dp)
        )
    }
}