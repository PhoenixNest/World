package io.dev.relic.feature.function.agent.ui.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.ai.client.generativeai.type.asTextOrNull
import dev.jeziellago.compose.markdowntext.MarkdownText
import io.agent.gemini.model.AbsGeminiCell
import io.agent.gemini.model.GeminiHybridCell
import io.agent.gemini.model.GeminiTextCell
import io.agent.gemini.utils.GeminiChatRole.AGENT
import io.agent.gemini.utils.GeminiChatRole.ERROR
import io.agent.gemini.utils.GeminiChatRole.USER
import io.common.util.ToastUtil
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AgentMessageCell(
    geminiCellContent: AbsGeminiCell,
    modifier: Modifier = Modifier
) {

    val clipboardManager = LocalClipboardManager.current

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
            for (part in geminiCellContent.hybridContent.parts) {
                textContent.append(part.asTextOrNull())
            }
            textContent.toString()
        }

        else -> stringResource(id = R.string.agent_unknown_cell_type_warning)
    }

    val cellContentColor = when (geminiCellContent.roleId) {
        USER.roleId -> mainTextColor
        AGENT.roleId -> mainThemeColor
        ERROR.roleId -> mainTextColor
        else -> errorColorAccent
    }

    Box(modifier = modifier.fillMaxWidth()) {
        MarkdownText(
            markdown = cellContent,
            fontResource = io.core.ui.R.font.ubuntu_regular,
            modifier = Modifier
                .align(cellGravity)
                .background(
                    color = cellBackgroundColor,
                    shape = cellShape
                )
                .combinedClickable(
                    onLongClick = {
                        clipboardManager.setText(AnnotatedString(cellContent))
                        ToastUtil.showToast("Copied to clipboard")
                    },
                    onClick = {
                        //
                    }
                )
                .padding(16.dp),
            style = TextStyle(
                color = cellContentColor
            )
        )
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