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
import io.agent.gemini.model.AbsGeminiCell
import io.agent.gemini.model.GeminiHybridCell
import io.agent.gemini.model.GeminiTextCell
import io.agent.gemini.utils.GeminiChatCellType.HYBRID
import io.agent.gemini.utils.GeminiChatCellType.TEXT
import io.agent.gemini.utils.GeminiChatRole
import io.agent.gemini.utils.GeminiChatRole.AGENT
import io.agent.gemini.utils.GeminiChatRole.USER
import io.agent.gemini.utils.GeminiMessageMode.FULL
import io.agent.gemini.utils.GeminiMessageMode.STREAM
import io.common.ext.ViewModelExt.operationInViewModelScope
import io.common.ext.ViewModelExt.setState
import io.common.util.LogUtil
import io.common.util.ToastUtil
import io.dev.relic.R
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
     * Instance value of the current chat window.
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

        /**
         * Chose the response type of Gemini model.
         * */
        private val GEMINI_GENERATE_MODE = FULL
    }

    fun updateSearchPrompt(newValue: String) {
        agentSearchContent = newValue
    }

    /* ======================== Global Message Sender ======================== */

    fun sendTextMessage(message: String) {
        if (!isAllowUserInput) {
            ToastUtil.showToast(R.string.agent_awaiting_answer_waring)
            return
        }

        if (message == EMPTY_SEARCH_PROMPT) {
            ToastUtil.showToast(R.string.agent_input_waring)
            return
        }

        LogUtil.d(TAG, "[Send Message] message: $message")

        val cell = GeminiTextCell(
            roleId = USER.roleId,
            cellTypeId = HYBRID.typeId,
            isPending = true,
            textContent = message
        )

        operationInViewModelScope {
            updateSearchPrompt(EMPTY_SEARCH_PROMPT)
            insertChatHistoryItem(cell)
            setState(_agentChatDataStateFlow, SendingQuestion(cell))
            when (GEMINI_GENERATE_MODE) {
                FULL -> sendMessage(message)
                STREAM -> sendMessageStream(message)
            }
        }
    }

    fun sendHybridMessage(message: Content) {
        LogUtil.d(TAG, "[Send Content] content: $message")

        val cell = GeminiHybridCell(
            role = USER.roleId,
            cellTypeId = HYBRID.typeId,
            isPending = true,
            hybridContent = message
        )

        operationInViewModelScope {
            insertChatHistoryItem(cell)
            setState(_agentChatDataStateFlow, SendingQuestion(cell))
            when (GEMINI_GENERATE_MODE) {
                FULL -> sendMessage(message)
                STREAM -> sendMessageStream(message)
            }
        }
    }

    /* ======================== Chat Window Controller ======================== */

    private fun toggleInputSwitcher() {
        isAllowUserInput = (!isAllowUserInput)
        LogUtil.d(TAG, "[Input Manager] isAllowUserInput = $isAllowUserInput")
    }

    private fun createNewChatWindow() {
        LogUtil.w(TAG, "[Chat Window] onCreate")
        if (agentChatWindow != null) releaseChatWindow()
        agentChatWindow = GeminiAgent.startChat()
    }

    private fun releaseChatWindow() {
        LogUtil.w(TAG, "[Chat Window] onRelease")
        agentChatWindow = null
    }

    /* ======================== Chat History Controller ======================== */

    private fun insertChatHistoryItem(cell: AbsGeminiCell) {
        _agentChatHistory.add(cell)
    }

    private fun removeChatHistoryItem(): AbsGeminiCell? {
        return _agentChatHistory.removeLastOrNull()
    }

    private fun clearChatHistory() {
        _agentChatHistory.clear()
    }

    /* ======================== Global Gemini data state Handler ======================== */

    /**
     * Control the front state or just change some field value with the Gemini state.
     * */
    private fun handleGeminiState() {
        operationInViewModelScope {
            _agentChatDataStateFlow.onEach {
                when (it) {
                    is Init -> {
                        LogUtil.d(TAG, "[Gemini State] Init")
                        toggleInputSwitcher()
                    }

                    is SendingQuestion -> {
                        LogUtil.d(TAG, "[Gemini State] Sending question")
                        toggleInputSwitcher()
                    }

                    is SuccessReceivedAnswer -> {
                        LogUtil.d(TAG, "[Gemini State] Success received answer")
                        toggleInputSwitcher()
                    }

                    is FailedOrError -> {
                        LogUtil.e(TAG, "[Gemini State] Failed Or Error")
                        toggleInputSwitcher()
                    }
                }
            }.stateIn(viewModelScope)
        }
    }

    /* ======================== Inner Message Sender with Handler ======================== */

    /**
     * Send generic message to Gemini Model by `Sync` and handle the response data.
     *
     * @param message
     * */
    private suspend fun <T> sendMessage(message: T) {
        agentChatWindow?.also { chatWindow ->
            try {
                val messageResponse = GeminiAgent.sendMessage(chatWindow, message)
                handleGeminiResponse(messageResponse)
            } catch (exception: Exception) {
                handleGeminiError(null, exception.localizedMessage)
            }
        }
    }

    /**
     * Send generic message to Gemini Model by `Async` and handle
     * its return data from the response flow.
     *
     * @param message
     * */
    private suspend fun <T> sendMessageStream(message: T) {
        agentChatWindow?.also { chatWindow ->
            val outputMessage = StringBuilder()
            try {
                GeminiAgent.sendMessageStream(
                    chatWindow = chatWindow,
                    message = message
                ).catch { throwable ->
                    handleGeminiError(null, throwable.localizedMessage)
                }.collect { response ->
                    outputMessage.append(response.text)
                    handleGeminiStreamResponse(outputMessage.toString())
                }
            } catch (exception: Exception) {
                handleGeminiError(null, exception.localizedMessage)
            }
        }
    }

    /* ======================== Response Handler ======================== */

    /**
     * Handle the response data from Gemini Model.
     *
     * @param response
     * */
    private fun handleGeminiResponse(response: GenerateContentResponse?) {
        response?.text?.also {
            LogUtil.d(TAG, "[Handle Gemini Response] response: $it")
            val answerCell = GeminiTextCell(
                roleId = AGENT.roleId,
                cellTypeId = TEXT.typeId,
                isPending = false,
                textContent = it
            )

            setState(_agentChatDataStateFlow, SuccessReceivedAnswer(answerCell))
            insertChatHistoryItem(answerCell)
        }
    }

    private fun handleGeminiStreamResponse(outputMassage: String) {
        LogUtil.d(TAG, "[Handle Gemini Response Stream] response: $outputMassage")
        val answerCell = GeminiTextCell(
            roleId = AGENT.roleId,
            cellTypeId = TEXT.typeId,
            isPending = false,
            textContent = outputMassage
        )

        setState(_agentChatDataStateFlow, SuccessReceivedAnswer(answerCell))
        insertChatHistoryItem(answerCell)
    }

    /**
     * Handle the Error status data from the Gemini Model or Android System.
     *
     * @param errorCode
     * @param errorMessage
     * */
    private fun handleGeminiError(
        errorCode: Int?,
        errorMessage: String?
    ) {
        LogUtil.e(TAG, "[Handle Gemini Error] Error, ($errorCode, $errorMessage)")
        val errorCell = GeminiTextCell(
            roleId = GeminiChatRole.ERROR.roleId,
            isPending = false,
            textContent = errorMessage ?: "Unknown error occurred."
        )

        setState(_agentChatDataStateFlow, FailedOrError(errorCode, errorMessage))
        insertChatHistoryItem(errorCell)
    }
}