package io.dev.relic.feature.function.agent.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.CommonInputField
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColor
import io.dev.relic.R

@Composable
fun AgentSearchBar(
    contentValue: String,
    onValueChange: (newValue: String) -> Unit,
    onAgentStartChat: () -> Unit
) {
    CommonInputField(
        content = contentValue,
        hintResId = R.string.agent_input_area_hint,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = mainThemeColor.copy(alpha = 0.3F),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(20.dp),
        contentTextStyle = TextStyle(mainTextColor),
        hintTextStyle = TextStyle(mainTextColor.copy(alpha = 0.3F)),
        maxLines = 1,
        onDone = onAgentStartChat
    )
}

@Composable
@Preview
private fun AgentSearchBarPreview() {
    AgentSearchBar(
        contentValue = "",
        onValueChange = {},
        onAgentStartChat = {}
    )
}