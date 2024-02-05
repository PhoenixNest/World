package io.agent.gemini.util

enum class GeminiChatRole(val roleId: Int) {
    ERROR(-1),
    USER(0),
    AGENT(1)
}