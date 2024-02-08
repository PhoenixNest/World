package io.agent.gemini

import com.google.ai.client.generativeai.Chat

object GeminiAgentFactory {

    fun provideNewChatWindow(isVisionModel: Boolean = false): Chat {
        return GeminiAgent.startChat(isVisionModel)
    }

}