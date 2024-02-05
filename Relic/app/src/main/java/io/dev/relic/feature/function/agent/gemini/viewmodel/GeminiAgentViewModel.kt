package io.dev.relic.feature.function.agent.gemini.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.GenerateContentResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.agent.gemini.GeminiAgent
import io.agent.gemini.GeminiAgentFactory
import io.agent.gemini.model.AbsGeminiCell
import io.agent.gemini.model.GeminiHybridCell
import io.agent.gemini.model.GeminiTextCell
import io.agent.gemini.util.GeminiChatCellType
import io.agent.gemini.util.GeminiChatRole
import io.common.ext.ViewModelExt.operationInViewModelScope
import io.common.ext.ViewModelExt.setState
import io.common.util.LogUtil
import io.dev.relic.feature.function.agent.gemini.GeminiAgentDataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GeminiAgentViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    /**
     * Record the [String] type content of user input.
     * */
    var agentSearchContent by mutableStateOf("")

    /**
     * Current chat window instance.
     * */
    private var agentChatWindow: Chat? = null

    /**
     * The chat message data flow by using gemini.
     * */
    private val _agentChatDataStateFlow = MutableStateFlow<GeminiAgentDataState>(GeminiAgentDataState.Init)
    val agentChatDataStateFlow: StateFlow<GeminiAgentDataState> get() = _agentChatDataStateFlow

    init {
        createNewChatWindow()
    }

    override fun onCleared() {
        super.onCleared()
        releaseChatWindow()
    }

    companion object {
        private const val TAG = "AgentViewModel"
    }

    fun updateSearchPrompt(newValue: String) {
        agentSearchContent = newValue
    }

    fun sendTextMessage(message: String) {
        operationInViewModelScope {
            emitSendingState(
                GeminiTextCell(
                    role = GeminiChatRole.USER.roleId,
                    cellType = GeminiChatCellType.HYBRID.typeId,
                    isPending = true,
                    textContent = message
                )
            )
            sendMessage(message)
        }
    }

    fun sendHybridMessage(message: Content) {
        operationInViewModelScope {
            emitSendingState(
                GeminiHybridCell(
                    role = GeminiChatRole.USER.roleId,
                    cellType = GeminiChatCellType.HYBRID.typeId,
                    isPending = true,
                    hybridContent = message
                )
            )
            sendMessage(message)
        }
    }

    private fun createNewChatWindow() {
        releaseChatWindow()
        agentChatWindow = GeminiAgentFactory.provideNewChatWindow()
    }

    private fun releaseChatWindow() {
        agentChatWindow = null
    }

    private fun emitSendingState(questionCell: AbsGeminiCell) {
        setState(
            stateFlow = _agentChatDataStateFlow,
            newState = GeminiAgentDataState.SendingQuestion(questionCell)
        )
    }

    private fun emitErrorState(
        errorCode: Int? = null,
        errorMessage: String? = null
    ) {
        setState(
            stateFlow = _agentChatDataStateFlow,
            newState = GeminiAgentDataState.FailedOrError(
                errorCode = errorCode,
                errorMessage = errorMessage
            )
        )
    }

    private suspend fun <T> sendMessage(message: T) {
        agentChatWindow?.also { chatWindow ->
            try {
                val messageStream = GeminiAgent.sendMessageStream(chatWindow, message)
                messageStream.onEach { response ->
                    handleGeminiResponse(response)
                }.stateIn(viewModelScope)
            } catch (exception: Exception) {
                val errorMessage = exception.localizedMessage
                emitErrorState(
                    errorCode = null,
                    errorMessage = errorMessage
                ).also {
                    LogUtil.e(TAG, "[Send Message] Error, (null, $errorMessage)")
                }
            }
        }
    }

    private fun handleGeminiResponse(response: GenerateContentResponse) {
        response.text?.also {
            val newState = GeminiAgentDataState.SuccessReceivedAnswer(
                GeminiTextCell(
                    role = GeminiChatRole.AGENT.roleId,
                    cellType = GeminiChatCellType.TEXT.typeId,
                    isPending = false,
                    textContent = it
                )
            )
            setState(_agentChatDataStateFlow, newState)
        }
    }
}