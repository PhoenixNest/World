package io.agent.gemini

import io.agent.gemini.utils.GeminiChatCellType.HYBRID
import io.agent.gemini.utils.GeminiChatCellType.IMAGE
import io.agent.gemini.utils.GeminiChatCellType.TEXT

object GeminiCellConvertor {

    fun convertCellType(typeInt: Int) {
        when (typeInt) {
            0 -> TEXT
            1 -> IMAGE
            2 -> HYBRID
        }
    }

}