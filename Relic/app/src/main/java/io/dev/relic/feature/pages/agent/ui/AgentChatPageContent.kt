package io.dev.relic.feature.pages.agent.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.agent.gemini.model.AbsGeminiCell
import io.core.ui.CommonTopBar
import io.core.ui.theme.mainIconColorLight
import io.core.ui.theme.mainThemeColor
import io.dev.relic.feature.function.agent.gemini.ui.AgentChatHistory
import io.dev.relic.feature.function.agent.gemini.ui.AgentInputField

@Composable
fun AgentChatPageContent(
    chatLazyListState: LazyListState,
    inputMessage: String,
    isEnableSendButton: Boolean,
    isAwaitingAnswer: Boolean,
    chatHistory: List<AbsGeminiCell>,
    onMessageValueChange: (message: String) -> Unit,
    onSendMessage: () -> Unit,
    onCopyTextClick: (copyText: String) -> Unit,
    onBackClick: () -> Unit
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
                hasTitle = true,
                title = "Greeting",
                iconColor = mainIconColorLight
            )
            AgentChatPageArea(
                chatLazyListState = chatLazyListState,
                inputMessage = inputMessage,
                isEnableSendButton = isEnableSendButton,
                isAwaitingAnswer = isAwaitingAnswer,
                chatHistory = chatHistory,
                onMessageValueChange = onMessageValueChange,
                onSendMessage = onSendMessage,
                onCopyTextClick = onCopyTextClick
            )
        }
    }
}

@Composable
private fun AgentChatPageArea(
    chatLazyListState: LazyListState,
    inputMessage: String,
    isEnableSendButton: Boolean,
    isAwaitingAnswer: Boolean,
    chatHistory: List<AbsGeminiCell>,
    onMessageValueChange: (message: String) -> Unit,
    onSendMessage: () -> Unit,
    onCopyTextClick: (copyText: String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AgentChatHistory(
            chatHistory = chatHistory,
            onCopyTextClick = onCopyTextClick,
            lazyListState = chatLazyListState
        )
        AgentInputField(
            inputMessage = inputMessage,
            isEnableSend = isEnableSendButton,
            isAwaitingAnswer = isAwaitingAnswer,
            onValueChange = onMessageValueChange,
            onSendMessage = onSendMessage,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}