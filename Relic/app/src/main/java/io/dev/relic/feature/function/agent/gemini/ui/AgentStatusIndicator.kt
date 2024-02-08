package io.dev.relic.feature.function.agent.gemini.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainTextColor
import io.dev.relic.R
import io.dev.relic.feature.function.agent.gemini.ui.widget.AgentAwaitAnswerCell

@Composable
fun AgentStatusIndicator(isAwaitingAnswer: Boolean) {
    val indicatorText = if (isAwaitingAnswer) {
        stringResource(id = R.string.agent_awaiting_answer_label)
    } else {
        stringResource(id = R.string.agent_ask_next)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
                bottom = 24.dp
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = isAwaitingAnswer) {
            AgentAwaitAnswerCell()
            Spacer(modifier = Modifier.height(8.dp))
        }
        Text(
            text = indicatorText,
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )
    }
}

@Composable
@Preview
private fun AgentAwaitingAnswerIndicatorPreview() {
    AgentStatusIndicator(true)
}

@Composable
@Preview
private fun AgentReceivedAnswerIndicatorPreview() {
    AgentStatusIndicator(false)
}