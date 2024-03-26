package io.agent.gemini.model

import com.google.ai.client.generativeai.type.Content
import io.agent.gemini.utils.GeminiChatCellType

class GeminiHybridCell(
    role: Int,
    isPending: Boolean,
    val hybridContent: Content,
    cellTypeId: Int = GeminiChatCellType.HYBRID.typeId
) : AbsGeminiCell(
    roleId = role,
    cellType = cellTypeId,
    isPending = isPending
)