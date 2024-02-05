package io.agent.gemini.model

class GeminiTextCell(
    role: Int,
    cellType: Int,
    isPending: Boolean,
    val textContent: String
) : AbsGeminiCell(
    role = role,
    cellType = cellType,
    isPending = isPending
)