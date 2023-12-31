package io.agent.gemini

import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import io.common.RelicResCenter

/**
 * [Quickstart: Get started with the Gemini API in Android apps (client SDK)](https://ai.google.dev/tutorials/android_quickstart?hl=en)
 * */
object GeminiAgent {

    private const val TAG = "GeminiAgent"

    /**
     * [Gemini models](https://ai.google.dev/models/gemini?hl=en)
     * */
    private const val GEMINI_MODEL_TEXT_ONLY = "gemini-pro-vision"
    private const val GEMINI_MODEL_TEXT_VISION = "gemini-pro-vision"

    /**
     * [Gemini API Key](https://makersuite.google.com/app/apikey)
     * */
    private val GEMINI_DEV_KEY: String = RelicResCenter.getString(R.string.agent_gemini_dev_key)

    fun startChat(chatHistory: List<Content>) {
        val chat: Chat = coreModel().startChat(chatHistory)
    }

    private fun coreModel(): GenerativeModel {
        return GenerativeModel(
            modelName = GEMINI_MODEL_TEXT_VISION,
            apiKey = GEMINI_DEV_KEY
        )
    }
}