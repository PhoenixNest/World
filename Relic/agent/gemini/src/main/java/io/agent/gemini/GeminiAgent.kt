package io.agent.gemini

import android.graphics.Bitmap
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.GenerateContentResponse
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

    fun startChat(
        chatHistory: List<Content> = emptyList()
    ): Chat {
        return coreModel().startChat(chatHistory)
    }

    suspend fun <T> sendMessage(
        chatWindow: Chat,
        message: T
    ): GenerateContentResponse? {
        return when (message) {
            is Bitmap -> {
                LogUtil.d(TAG, "[Send Message] Sending [Bitmap] type")
                chatWindow.sendMessage(message)
            }

            is Content -> {
                LogUtil.d(TAG, "[Send Message] Sending [Content] type")
                chatWindow.sendMessage(message)
            }

            is String -> {
                LogUtil.d(TAG, "[Send Message] Sending [String] type, messageContent: $message")
                chatWindow.sendMessage(message)
            }

            else -> {
                LogUtil.w(TAG, "[Send Message] Sending [Unknown] type")
                null
            }
        }
    }

    fun <T> sendMessageStream(
        chatWindow: Chat,
        message: T
    ): Flow<GenerateContentResponse> {
        return when (message) {
            is Bitmap -> {
                LogUtil.d(TAG, "[Send Message] Sending [Bitmap] type")
                chatWindow.sendMessageStream(message)
            }

            is Content -> {
                LogUtil.d(TAG, "[Send Message] Sending [Content] type")
                chatWindow.sendMessageStream(message)
            }

            is String -> {
                LogUtil.d(TAG, "[Send Message] Sending [String] type, messageContent: $message")
                chatWindow.sendMessageStream(message)
            }

            else -> {
                LogUtil.w(TAG, "[Send Message] Sending [Unknown] type")
                emptyFlow()
            }
        }
    }

    private fun coreModel(): GenerativeModel {
        return GenerativeModel(
            modelName = GEMINI_MODEL_TEXT_VISION,
            apiKey = GEMINI_DEV_KEY
        )
    }
}