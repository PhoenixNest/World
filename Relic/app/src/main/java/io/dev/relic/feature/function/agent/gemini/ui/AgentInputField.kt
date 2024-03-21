package io.dev.relic.feature.function.agent.gemini.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.CommonInputField
import io.core.ui.theme.mainIconColorLight
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainTextColor30
import io.core.ui.theme.mainThemeColor
import io.core.ui.theme.mainThemeColorAccent
import io.core.ui.theme.mainThemeColorLight
import io.dev.relic.R

@Composable
fun AgentInputField(
    inputMessage: String,
    isEnableSend: Boolean,
    isAwaitingAnswer: Boolean,
    onValueChange: (newValue: String) -> Unit,
    onSendMessage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .imePadding(),
        color = mainThemeColor,
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(mainThemeColorLight.copy(alpha = 0.1F))
        ) {
            AgentStatusIndicator(isAwaitingAnswer = isAwaitingAnswer)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CommonInputField(
                    content = inputMessage,
                    hintResId = R.string.agent_input_area_hint,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4F)
                        .background(
                            color = mainThemeColorLight.copy(alpha = 0.1F),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp),
                    contentTextStyle = TextStyle(mainTextColor),
                    hintTextStyle = TextStyle(mainTextColor30),
                    onDone = onSendMessage
                )
                Spacer(modifier = Modifier.width(20.dp))
                Surface(
                    color = if (isEnableSend) {
                        mainThemeColorAccent
                    } else {
                        mainIconColorLight.copy(alpha = 0.3F)
                    },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    IconButton(
                        onClick = onSendMessage,
                        modifier = Modifier.weight(1F)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_send),
                            contentDescription = DEFAULT_DESC,
                            tint = if (isEnableSend) {
                                mainIconColorLight
                            } else {
                                mainIconColorLight.copy(alpha = 0.3F)
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
@Preview
private fun AgentInputBarNoContentPreview() {
    AgentInputField(
        inputMessage = "",
        isEnableSend = false,
        isAwaitingAnswer = false,
        onValueChange = {},
        onSendMessage = {}
    )
}

@Composable
@Preview
private fun AgentInputBarPreview() {
    AgentInputField(
        inputMessage = "Temp input value",
        isEnableSend = true,
        isAwaitingAnswer = true,
        onValueChange = {},
        onSendMessage = {}
    )
}

@Composable
@Preview
private fun AgentInputBarLargeContentPreview() {
    val stringBuilder = StringBuilder()
    repeat(10) {
        stringBuilder.append("Temp input value ")
    }
    AgentInputField(
        inputMessage = stringBuilder.toString(),
        isEnableSend = true,
        isAwaitingAnswer = true,
        onValueChange = {},
        onSendMessage = {}
    )
}
