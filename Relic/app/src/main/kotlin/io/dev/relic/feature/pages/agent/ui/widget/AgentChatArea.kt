package io.dev.relic.feature.pages.agent.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.agent.gemini.model.AbsGeminiCell
import io.dev.relic.feature.function.agent.ui.AgentChatList
import io.dev.relic.feature.function.agent.ui.AgentInputField

@Composable
fun AgentChatArea(
    chatLazyListState: LazyListState,
    inputMessage: String,
    isEnableSendButton: Boolean,
    isAwaitingAnswer: Boolean,
    chatHistory: List<AbsGeminiCell>,
    onMessageValueChange: (message: String) -> Unit,
    onSendMessage: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AgentChatList(
            chatHistory = chatHistory,
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