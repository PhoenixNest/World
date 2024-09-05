package io.dev.relic.feature.pages.agent.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.agent.gemini.model.AbsGeminiCell
import io.agent.gemini.model.GeminiTextCell
import io.agent.gemini.utils.GeminiChatRole
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.CommonTopBar
import io.core.ui.theme.mainIconColorLight
import io.core.ui.theme.mainThemeColor
import io.dev.relic.R
import io.dev.relic.feature.pages.agent.ui.widget.AgentChatArea

@Composable
fun AgentChatPageContent(
    chatLazyListState: LazyListState,
    inputMessage: String,
    isEnableSendButton: Boolean,
    isAwaitingAnswer: Boolean,
    chatHistory: List<AbsGeminiCell>,
    onMessageValueChange: (message: String) -> Unit,
    onSendMessage: () -> Unit,
    onBackClick: () -> Unit,
    onInfoClick: () -> Unit
) {
    LaunchedEffect(chatHistory) {
        chatLazyListState.animateScrollToItem(chatHistory.size)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mainThemeColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            CommonTopBar(
                onBackClick = onBackClick,
                hasTitle = false,
                title = stringResource(R.string.title_greeting),
                iconColor = mainIconColorLight,
                tailContent = {
                    IconButton(onClick = onInfoClick) {
                        Icon(
                            painter = painterResource(id = io.core.ui.R.drawable.ic_info),
                            contentDescription = DEFAULT_DESC,
                            tint = mainIconColorLight
                        )
                    }
                }
            )
            AgentChatArea(
                chatLazyListState = chatLazyListState,
                inputMessage = inputMessage,
                isEnableSendButton = isEnableSendButton,
                isAwaitingAnswer = isAwaitingAnswer,
                chatHistory = chatHistory,
                onMessageValueChange = onMessageValueChange,
                onSendMessage = onSendMessage,
            )
        }
    }
}

@Composable
@Preview
private fun AgentChatPageContentPreview() {
    val chatHistory = listOf<AbsGeminiCell>(
        GeminiTextCell(
            roleId = GeminiChatRole.AGENT.roleId,
            isPending = false,
            textContent = "I'm Gemini, you personal Ai assistant."
        ),
        GeminiTextCell(
            roleId = GeminiChatRole.USER.roleId,
            isPending = false,
            textContent = "Hello, i am Peter."
        ),
        GeminiTextCell(
            roleId = GeminiChatRole.ERROR.roleId,
            isPending = false,
            textContent = "This is an test error message."
        )
    )
    AgentChatPageContent(
        chatLazyListState = rememberLazyListState(),
        inputMessage = "Hello World",
        isEnableSendButton = true,
        isAwaitingAnswer = false,
        chatHistory = chatHistory,
        onMessageValueChange = {},
        onSendMessage = {},
        onBackClick = {},
        onInfoClick = {}
    )
}