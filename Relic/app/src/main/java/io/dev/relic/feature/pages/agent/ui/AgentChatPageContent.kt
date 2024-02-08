package io.dev.relic.feature.pages.agent.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.agent.gemini.model.AbsGeminiCell
import io.core.ui.theme.mainThemeColor
import io.dev.relic.feature.function.agent.ui.AgentInputField
import io.dev.relic.feature.pages.agent.ui.widget.AgentChatHistory

@Composable
fun AgentChatPageContent(
    inputMessage: String,
    isEnableSendButton: Boolean,
    chatHistory: List<AbsGeminiCell>,
    onMessageValueChange: (message: String) -> Unit,
    onSendMessage: () -> Unit,
    chatLazyListState: LazyListState
) {
    LaunchedEffect(chatHistory) {
        chatLazyListState.animateScrollToItem(chatHistory.size)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mainThemeColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            AgentChatHistory(
                chatHistory = chatHistory,
                lazyListState = chatLazyListState
            )
            AgentInputField(
                inputMessage = inputMessage,
                isEnableSend = isEnableSendButton,
                onValueChange = onMessageValueChange,
                onSendMessage = onSendMessage,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}