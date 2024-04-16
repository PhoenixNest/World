package io.dev.relic.feature.pages.agent

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.agent.gemini.model.AbsGeminiCell
import io.agent.gemini.model.GeminiTextCell
import io.agent.gemini.utils.GeminiChatRole
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.agent.gemini.GeminiAgentDataState
import io.dev.relic.feature.function.agent.gemini.ui.GeminiIntroDialog
import io.dev.relic.feature.function.agent.gemini.viewmodel.GeminiAgentViewModel
import io.dev.relic.feature.pages.agent.ui.AgentChatPageContent
import io.dev.relic.feature.screens.main.MainScreenState

@Composable
fun AgentChatPageRoute(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    geminiAgentViewModel: GeminiAgentViewModel,
    onBackClick: () -> Unit
) {

    /* ======================== Common ======================== */

    val coroutineScope = mainScreenState.coroutineScope

    /* ======================== Field ======================== */

    val agentDataState by geminiAgentViewModel.agentChatDataStateFlow
        .collectAsStateWithLifecycle()

    val inputMessage = geminiAgentViewModel.agentSearchContent

    val chatHistory = geminiAgentViewModel.agentChatHistory

    val isEnableSendButton = geminiAgentViewModel.isAllowUserInput
            && inputMessage.isNotEmpty()
            && inputMessage.isNotBlank()

    var isShowHelpDialog by remember {
        mutableStateOf(false)
    }

    /* ======================== Ui ======================== */

    AgentChatPage(
        inputMessage = inputMessage,
        isEnableSendButton = isEnableSendButton,
        agentDataState = agentDataState,
        agentChatList = chatHistory,
        onBackClick = onBackClick,
        onMessageValueChange = {
            geminiAgentViewModel.updateSearchPrompt(it)
        },
        onSendMessage = {
            geminiAgentViewModel.apply {
                sendTextMessage(agentSearchContent)
            }
        },
        onCopyTextClick = {
            //
        },
        onInfoClick = {
            isShowHelpDialog = true
        }
    )

    if (isShowHelpDialog) {
        GeminiIntroDialog(
            onCloseClick = { isShowHelpDialog = false },
            onStartChatClick = { isShowHelpDialog = false },
            onDismiss = {}
        )
    }
}

@Composable
private fun AgentChatPage(
    inputMessage: String,
    isEnableSendButton: Boolean,
    agentDataState: GeminiAgentDataState,
    agentChatList: List<AbsGeminiCell>,
    onMessageValueChange: (message: String) -> Unit,
    onSendMessage: () -> Unit,
    onCopyTextClick: (copyText: String) -> Unit,
    onBackClick: () -> Unit,
    onInfoClick: () -> Unit
) {
    val isAwaitingAnswer = when (agentDataState) {
        is GeminiAgentDataState.Init,
        is GeminiAgentDataState.SuccessReceivedAnswer,
        is GeminiAgentDataState.FailedOrError -> false

        is GeminiAgentDataState.SendingQuestion -> true
    }

    AgentChatPageContent(
        chatLazyListState = rememberLazyListState(),
        inputMessage = inputMessage,
        isEnableSendButton = isEnableSendButton,
        isAwaitingAnswer = isAwaitingAnswer,
        chatHistory = agentChatList,
        onMessageValueChange = onMessageValueChange,
        onSendMessage = onSendMessage,
        onCopyTextClick = onCopyTextClick,
        onBackClick = onBackClick,
        onInfoClick = onInfoClick
    )
}

@Composable
@Preview
private fun AgentChatPagePreview() {
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
    AgentChatPage(
        inputMessage = "",
        isEnableSendButton = false,
        agentDataState = GeminiAgentDataState.Init,
        agentChatList = chatHistory,
        onMessageValueChange = {},
        onSendMessage = {},
        onCopyTextClick = {},
        onBackClick = {},
        onInfoClick = {}
    )
}