package io.agent.gemini.model

import io.agent.gemini.util.GeminiChatCellType

class GeminiTextCell(
    roleId: Int,
    isPending: Boolean,
    val textContent: String,
    cellTypeId: Int = GeminiChatCellType.TEXT.typeId
) : AbsGeminiCell(
    roleId = roleId,
    cellType = cellTypeId,
    isPending = isPending
)