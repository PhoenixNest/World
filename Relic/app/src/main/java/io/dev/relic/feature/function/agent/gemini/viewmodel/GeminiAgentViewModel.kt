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
import io.dev.relic.feature.function.agent.gemini.GeminiAgentDataState.FailedOrError
import io.dev.relic.feature.function.agent.gemini.GeminiAgentDataState.Init
import io.dev.relic.feature.function.agent.gemini.GeminiAgentDataState.SendingQuestion
import io.dev.relic.feature.function.agent.gemini.GeminiAgentDataState.SuccessReceivedAnswer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
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
     * When user has sending a message to agent, set this value to false.
     * */
    var isAllowUserInput by mutableStateOf(false)

    /**
     * Current chat window instance.
     * */
    private var agentChatWindow: Chat? = null

    /**
     * The chat message data flow by using gemini.
     * */
    private val _agentChatDataStateFlow = MutableStateFlow<GeminiAgentDataState>(Init)
    val agentChatDataStateFlow: StateFlow<GeminiAgentDataState> get() = _agentChatDataStateFlow

    /**
     * Record the latest chat history.
     * */
    private var _agentChatHistory: MutableList<AbsGeminiCell> = mutableListOf()
    val agentChatHistory get() = _agentChatHistory.toList()

    init {
        createNewChatWindow()
        handleGeminiState()
    }

    override fun onCleared() {
        super.onCleared()
        releaseChatWindow()
    }

    companion object {
        private const val TAG = "GeminiAgentViewModel"
        private const val EMPTY_SEARCH_PROMPT = ""
    }

    fun updateSearchPrompt(newValue: String) {
        agentSearchContent = newValue
    }

    fun sendTextMessage(message: String) {
        LogUtil.d(TAG, "[Send Message] message: $message")
        val cell = GeminiTextCell(
            roleId = GeminiChatRole.USER.roleId,
            cellTypeId = GeminiChatCellType.HYBRID.typeId,
            isPending = true,
            textContent = message
        )

        operationInViewModelScope {
            updateSearchPrompt(EMPTY_SEARCH_PROMPT)
            insertChatHistoryItem(cell)
            setState(_agentChatDataStateFlow, SendingQuestion(cell))
            sendMessage(message)
        }
    }

    fun sendHybridMessage(message: Content) {
        LogUtil.d(TAG, "[Send Content] content: $message")
        val cell = GeminiHybridCell(
            role = GeminiChatRole.USER.roleId,
            cellTypeId = GeminiChatCellType.HYBRID.typeId,
            isPending = true,
            hybridContent = message
        )

        operationInViewModelScope {
            insertChatHistoryItem(cell)
            setState(_agentChatDataStateFlow, SendingQuestion(cell))
            sendMessage(message)
        }
    }

    private fun toggleInputSwitcher() {
        isAllowUserInput = (!isAllowUserInput)
        LogUtil.d(TAG, "[Input Manager] isAllowUserInput = $isAllowUserInput")
    }

    private fun createNewChatWindow() {
        LogUtil.w(TAG, "[Chat Window] onCreate")
        if (agentChatWindow != null) releaseChatWindow()
        agentChatWindow = GeminiAgentFactory.provideNewChatWindow()
    }

    private fun releaseChatWindow() {
        LogUtil.w(TAG, "[Chat Window] onRelease")
        agentChatWindow = null
    }

    private fun insertChatHistoryItem(cell: AbsGeminiCell) {
        _agentChatHistory.add(cell)
    }

    private fun removeChatHistoryItem(): AbsGeminiCell? {
        return _agentChatHistory.removeLastOrNull()
    }

    private fun clearChatHistory() {
        _agentChatHistory.clear()
    }

    private fun handleGeminiState() {
        operationInViewModelScope {
            _agentChatDataStateFlow.onEach {
                when (it) {
                    is Init -> toggleInputSwitcher()
                    is SendingQuestion -> toggleInputSwitcher()
                    is SuccessReceivedAnswer -> toggleInputSwitcher()
                    is FailedOrError -> toggleInputSwitcher()
                }
            }.stateIn(viewModelScope)
        }
    }

    private suspend fun <T> sendMessage(message: T) {
        agentChatWindow?.also { chatWindow ->
            try {
                val messageStream = GeminiAgent.sendMessageStream(chatWindow, message)
                messageStream.catch { throwable ->
                    handleGeminiError(null, throwable.localizedMessage)
                }.collect { response ->
                    handleGeminiResponse(response)
                }
            } catch (exception: Exception) {
                handleGeminiError(null, exception.localizedMessage)
            }
        }
    }

    private fun handleGeminiResponse(response: GenerateContentResponse) {
        operationInViewModelScope {
            response.text?.also {
                LogUtil.d(TAG, "[Handle Response] response: $it")
                val answerCell = GeminiTextCell(
                    roleId = GeminiChatRole.AGENT.roleId,
                    cellTypeId = GeminiChatCellType.TEXT.typeId,
                    isPending = false,
                    textContent = it
                )

                setState(_agentChatDataStateFlow, SuccessReceivedAnswer(answerCell))
                insertChatHistoryItem(answerCell)
            }
        }
    }

    private fun handleGeminiError(
        errorCode: Int?,
        errorMessage: String?
    ) {
        LogUtil.e(TAG, "[Handle Error] Error, ($errorCode, $errorMessage)")
        val errorCell = GeminiTextCell(
            roleId = GeminiChatRole.ERROR.roleId,
            isPending = false,
            textContent = errorMessage ?: "Unknown error occurred."
        )

        operationInViewModelScope {
            insertChatHistoryItem(errorCell)
            setState(_agentChatDataStateFlow, FailedOrError(errorCode, errorMessage))
        }
    }
}