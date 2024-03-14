package io.agent.gemini

import android.graphics.Bitmap
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.ai.client.generativeai.type.content
import io.agent.gemini.util.GeminiChatRole
import io.agent.gemini.util.GeminiChatRole.USER
import io.common.RelicResCenter
import io.common.util.LogUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * [Quickstart: Get started with the Gemini API in Android apps (client SDK)](https://ai.google.dev/tutorials/android_quickstart?hl=en)
 * */
object GeminiAgent {

    private const val TAG = "GeminiAgent"

    /**
     * [Gemini models](https://ai.google.dev/models/gemini?hl=en)
     * */
    private const val GEMINI_MODEL_TEXT_ONLY = "gemini-pro"
    private const val GEMINI_MODEL_TEXT_VISION = "gemini-pro-vision"

    /**
     * [Gemini API Key](https://makersuite.google.com/app/apikey)
     * */
    private val GEMINI_DEV_KEY = RelicResCenter.getString(R.string.agent_gemini_dev_key)

    /**
     * Indicate the current chosen model of Gemini.
     * */
    private var chosenCoreModel: GenerativeModel? = null

    fun startChat(
        isVisionModel: Boolean,
        chatHistory: List<Content> = emptyList()
    ): Chat {
        chosenCoreModel = chooseGeminiModel(isVisionModel)
        LogUtil.d(TAG, "[Gemini Model] Chosen model: ${chosenCoreModel!!.modelName}")
        return chosenCoreModel!!.startChat(chatHistory)
    }

    /* ======================== Sender ======================== */

    /**
     * Send the message to chat window and gets the full answer.
     *
     * @param chatWindow    The current open chat window.
     * @param message       The message will be auto translate to the Content type.
     *
     * @see Content
     * */
    suspend fun <T> sendMessage(
        chatWindow: Chat,
        message: T,
        chatRole: GeminiChatRole = USER
    ): GenerateContentResponse? {
        val realRole = chatRole.name.lowercase().trim()
        LogUtil.w(TAG, "[Message Sender] Locked chat role to: $realRole")

        return when (message) {
            is Bitmap -> {
                LogUtil.d(TAG, "[Message Sender] Send [Bitmap] type")
                val content = content {
                    role = chatRole.name.lowercase().trim()
                    image(message)
                }
                chatWindow.sendMessage(content)
            }

            is String -> {
                LogUtil.d(TAG, "[Message Sender] Send [String] type, messageContent: $message")
                val content = content {
                    role = chatRole.name.lowercase().trim()
                    text(message)
                }
                chatWindow.sendMessage(content)
            }

            is Content -> {
                // TODO: This type needs to specify the chat role such as "user"
                LogUtil.d(TAG, "[Message Sender] Send [Content] type")
                chatWindow.sendMessage(message)
            }

            else -> {
                LogUtil.w(TAG, "[Message Sender] Send [Unknown] type")
                null
            }
        }
    }

    /**
     * Send the message to chat window and gets the flow type answer.
     *
     * @param chatWindow    The current open chat window.
     * @param message       The message will be auto translate to the Content type.
     *
     * @see Content
     * */
    fun <T> sendMessageStream(
        chatWindow: Chat,
        message: T,
        chatRole: GeminiChatRole = USER
    ): Flow<GenerateContentResponse> {
        val realRole = chatRole.name.lowercase().trim()
        LogUtil.w(TAG, "[Message Sender] Locked chat role to: $realRole")

        return when (message) {
            is Bitmap -> {
                LogUtil.d(TAG, "[Message Sender] Send [Bitmap] type")
                val content = content {
                    role = realRole
                    image(message)
                }
                chatWindow.sendMessageStream(content)
            }

            is String -> {
                LogUtil.d(TAG, "[Message Sender] Send [String] type, messageContent: $message")
                val content = content {
                    role = realRole
                    text(message)
                }
                chatWindow.sendMessageStream(content)
            }

            is Content -> {
                // TODO: This type needs to specify the chat role such as "user"
                LogUtil.d(TAG, "[Message Sender] Send [Content] type")
                chatWindow.sendMessageStream(message)
            }

            else -> {
                LogUtil.w(TAG, "[Message Sender] Send [Unknown] type")
                emptyFlow()
            }
        }
    }

    /* ======================== Generator ======================== */

    suspend fun <T> generateMessage(
        message: T,
        chatRole: GeminiChatRole = USER
    ): GenerateContentResponse? {
        val realRole = chatRole.name.lowercase().trim()
        LogUtil.w(TAG, "[Message Generator] Locked chat role to: $realRole")

        val currentChosenModel = chosenCoreModel
            ?: return null

        return when (message) {
            is Bitmap -> {
                LogUtil.d(TAG, "[Message Generator] Generate [Bitmap] type")
                currentChosenModel.generateContent(message)
            }

            is String -> {
                LogUtil.d(TAG, "[Message Generator] Generate [String] type, messageContent: $message")
                currentChosenModel.generateContent(message)
            }

            is Content -> {
                // TODO: This type needs to specify the chat role such as "user"
                LogUtil.d(TAG, "[Message Generator] Generate [Content] type")
                currentChosenModel.generateContent(message)
            }

            else -> {
                LogUtil.w(TAG, "[Message Generator] Generate [Unknown] type")
                null
            }
        }
    }

    fun <T> generateMessageStream(
        message: T,
        chatRole: GeminiChatRole = USER
    ): Flow<GenerateContentResponse> {
        val realRole = chatRole.name.lowercase().trim()
        LogUtil.w(TAG, "[Message Generator] Locked chat role to: $realRole")

        val currentChosenModel = chosenCoreModel
            ?: return emptyFlow()

        return when (message) {
            is Bitmap -> {
                LogUtil.d(TAG, "[Generate Message] Sending [Bitmap] type")
                val content = content {
                    role = realRole
                    image(message)
                }
                currentChosenModel.generateContentStream(content)
            }

            is String -> {
                LogUtil.d(TAG, "[Generate Message] Sending [String] type, messageContent: $message")
                val content = content {
                    role = realRole
                    text(message)
                }
                currentChosenModel.generateContentStream(content)
            }

            is Content -> {
                // TODO: This type needs to specify the chat role such as "user"
                LogUtil.d(TAG, "[Generate Message] Sending [Content] type")
                currentChosenModel.generateContentStream(message)
            }

            else -> {
                LogUtil.w(TAG, "[Generate Message] Sending [Unknown] type")
                emptyFlow()
            }
        }
    }

    /* ======================== Gemini Model ======================== */

    private fun coreTextModel(): GenerativeModel {
        return GenerativeModel(
            modelName = GEMINI_MODEL_TEXT_ONLY,
            apiKey = GEMINI_DEV_KEY
        )
    }

    private fun coreHybridModel(): GenerativeModel {
        return GenerativeModel(
            modelName = GEMINI_MODEL_TEXT_VISION,
            apiKey = GEMINI_DEV_KEY
        )
    }

    private fun chooseGeminiModel(isVisionModel: Boolean = false): GenerativeModel {
        return if (isVisionModel) {
            coreHybridModel()
        } else {
            coreTextModel()
        }
    }
}