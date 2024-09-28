package io.core.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainTextColorDark

@Composable
fun CommonInputField(
    content: String,
    @StringRes hintResId: Int,
    onValueChange: (inputValue: String) -> Unit,
    modifier: Modifier = Modifier,
    contentTextStyle: TextStyle = TextStyle(),
    hintTextStyle: TextStyle = TextStyle(),
    isEnabled: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    // Keyboard Action
    onDone: () -> Unit = {},
    onGo: () -> Unit = {},
    onNext: () -> Unit = {},
    onPrevious: () -> Unit = {},
    onSearch: () -> Unit = {},
    onSend: () -> Unit = {}
) {
    BasicTextField(
        value = content,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = isEnabled,
        textStyle = contentTextStyle.copy(
            fontFamily = ubuntu,
            textAlign = TextAlign.Start
        ),
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone.invoke() },
            onGo = { onGo.invoke() },
            onNext = { onNext.invoke() },
            onPrevious = { onPrevious.invoke() },
            onSearch = { onSearch.invoke() },
            onSend = { onSend.invoke() }
        ),
        decorationBox = { innerTextField ->
            if (content.isEmpty() || content.isBlank()) {
                Text(
                    text = stringResource(id = hintResId),
                    style = hintTextStyle.copy(
                        fontFamily = ubuntu,
                        textAlign = TextAlign.Start
                    )
                )
            }
            innerTextField()
        }
    )
}

@Composable
fun CommonInputField(
    content: TextFieldValue,
    @StringRes hintResId: Int,
    onValueChange: (inputValue: TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    contentTextStyle: TextStyle = TextStyle(),
    hintTextStyle: TextStyle = TextStyle(),
    isEnabled: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    // Keyboard Action
    onDone: () -> Unit = {},
    onGo: () -> Unit = {},
    onNext: () -> Unit = {},
    onPrevious: () -> Unit = {},
    onSearch: () -> Unit = {},
    onSend: () -> Unit = {}
) {
    BasicTextField(
        value = content,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = isEnabled,
        textStyle = contentTextStyle.copy(
            fontFamily = ubuntu,
            textAlign = TextAlign.Start
        ),
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { onDone.invoke() },
            onGo = { onGo.invoke() },
            onNext = { onNext.invoke() },
            onPrevious = { onPrevious.invoke() },
            onSearch = { onSearch.invoke() },
            onSend = { onSend.invoke() }
        ),
        decorationBox = { innerTextField ->
            if (content.text.isEmpty() || content.text.isBlank()) {
                Text(
                    text = stringResource(id = hintResId),
                    style = hintTextStyle.copy(
                        fontFamily = ubuntu,
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
            hintResId = R.string.common_input_field_hint,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.LightGray.copy(alpha = 0.3F),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(20.dp),
            contentTextStyle = TextStyle(
                color = mainTextColorDark
            ),
            hintTextStyle = TextStyle(
                color = mainTextColorDark.copy(alpha = 0.3F)
            )
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
            hintResId = R.string.common_input_field_hint,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.LightGray.copy(alpha = 0.3F),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(20.dp),
            contentTextStyle = TextStyle(
                color = mainTextColorDark
            ),
            hintTextStyle = TextStyle(
                color = mainTextColorDark.copy(alpha = 0.3F)
            )
        )
    }
}