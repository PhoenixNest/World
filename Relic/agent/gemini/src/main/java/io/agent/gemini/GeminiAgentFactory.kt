package io.agent.gemini

import com.google.ai.client.generativeai.Chat

object GeminiAgentFactory {

    fun provideNewChatWindow(): Chat {
        return GeminiAgent.startChat()
    }

}