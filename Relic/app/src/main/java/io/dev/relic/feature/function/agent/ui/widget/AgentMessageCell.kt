package io.dev.relic.feature.function.agent.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.ai.client.generativeai.type.Part
import com.google.ai.client.generativeai.type.asTextOrNull
import io.agent.gemini.model.AbsGeminiCell
import io.agent.gemini.model.GeminiHybridCell
import io.agent.gemini.model.GeminiTextCell
import io.agent.gemini.util.GeminiChatRole.AGENT
import io.agent.gemini.util.GeminiChatRole.ERROR
import io.agent.gemini.util.GeminiChatRole.USER
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.errorColorAccent
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColor
import io.core.ui.theme.mainThemeColorAccent
import io.core.ui.theme.mainThemeColorLight
import io.dev.relic.R

private val startShape = RoundedCornerShape(
    topEnd = 16.dp,
    bottomStart = 16.dp,
    bottomEnd = 16.dp
)

private val endShape = RoundedCornerShape(
    topStart = 16.dp,
    bottomStart = 16.dp,
    bottomEnd = 16.dp
)

@Composable
fun AgentMessageCell(
    geminiCellContent: AbsGeminiCell,
    modifier: Modifier = Modifier
) {
    val cellGravity = when (geminiCellContent.roleId) {
        USER.roleId -> Alignment.CenterEnd
        ERROR.roleId, AGENT.roleId -> Alignment.CenterStart
        else -> Alignment.CenterStart
    }

    val cellShape = when (geminiCellContent.roleId) {
        USER.roleId -> endShape
        ERROR.roleId, AGENT.roleId -> startShape
        else -> startShape
    }

    val cellBackgroundColor = when (geminiCellContent.roleId) {
        USER.roleId -> mainThemeColorAccent
        ERROR.roleId -> errorColorAccent
        AGENT.roleId -> mainThemeColorLight
        else -> errorColorAccent
    }

    val cellContent = when (geminiCellContent) {
        is GeminiTextCell -> geminiCellContent.textContent
        is GeminiHybridCell -> {
            val textContent = StringBuilder()
            for (part: Part in geminiCellContent.hybridContent.parts) {
                textContent.append(part.asTextOrNull())
            }
            textContent.toString()
        }

        else -> "Unknown Cell Type"
    }

    val cellTextColor = when (geminiCellContent.roleId) {
        USER.roleId,
        AGENT.roleId -> mainThemeColor

        ERROR.roleId -> mainTextColor
        else -> errorColorAccent
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(cellGravity)
                .background(
                    color = cellBackgroundColor,
                    shape = cellShape
                )
                .padding(16.dp)
        ) {
            // if (geminiCellContent.roleId == USER.roleId) {
            //     UserCellIntroBar(onCopyClick = {})
            //     Spacer(modifier = Modifier.height(16.dp))
            // }
            Text(
                text = cellContent,
                style = TextStyle(
                    color = cellTextColor,
                    fontFamily = ubuntu
                )
            )
            // if (geminiCellContent.roleId == AGENT.roleId) {
            //     Spacer(modifier = Modifier.height(16.dp))
            //     AgentCellFunctionBar()
            // }
        }
    }
}

@Composable
private fun UserCellIntroBar(
    userName: String = "User",
    onCopyClick: () -> Unit
) {
    Row(
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = userName,
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )
        IconButton(onClick = onCopyClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_copy),
                contentDescription = DEFAULT_DESC
            )
        }
    }
}

@Composable
private fun AgentCellFunctionBar() {
    Row(
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //
    }
}

@Composable
@Preview
private fun AgentMessageCellPreview() {
    val chatHistory = listOf<AbsGeminiCell>(
        GeminiTextCell(
            roleId = AGENT.roleId,
            isPending = false,
            textContent = "I'm Gemini, you personal Ai assistant."
        ),
        GeminiTextCell(
            roleId = USER.roleId,
            isPending = false,
            textContent = "Hello, i am Peter."
        ),
        GeminiTextCell(
            roleId = ERROR.roleId,
            isPending = false,
            textContent = "This is an test error message."
        )
    )

    LazyColumn {
        itemsIndexed(chatHistory) { index, cell ->
            val itemDecorationModifier = Modifier.padding(
                top = if (index == 0) 0.dp else 16.dp,
                bottom = if (index == chatHistory.size - 1) 72.dp else 0.dp
            )
            AgentMessageCell(
                geminiCellContent = cell,
                modifier = itemDecorationModifier
            )
        }
    }
}