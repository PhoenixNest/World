package io.dev.relic.feature.function.agent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.CommonInputField
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColor
import io.dev.relic.R

@Composable
fun AgentSearchBar() {

    var content by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        CommonInputField(
            content = content,
            hintResId = R.string.agent_input_area_hint,
            onValueChange = {
                content = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = mainThemeColor.copy(alpha = 0.3F),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(20.dp),
            contentTextStyle = TextStyle(
                color = mainTextColor
            ),
            hintTextStyle = TextStyle(
                color = mainTextColor.copy(alpha = 0.3F)
            )
        )
    }
}

@Composable
@Preview
private fun AgentSearchBarPreview() {
    AgentSearchBar()
}