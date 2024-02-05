package io.agent.gemini.util

import io.agent.gemini.util.GeminiChatCellType.HYBRID
import io.agent.gemini.util.GeminiChatCellType.IMAGE
import io.agent.gemini.util.GeminiChatCellType.TEXT

object GeminiChatConvertor {

    fun convertCellType(typeInt: Int) {
        when (typeInt) {
            0 -> TEXT
            1 -> IMAGE
            2 -> HYBRID
        }
    }

}