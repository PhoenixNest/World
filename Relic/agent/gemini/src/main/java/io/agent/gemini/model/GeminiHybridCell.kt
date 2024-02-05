package io.agent.gemini.model

import com.google.ai.client.generativeai.type.Content

class GeminiHybridCell(
    role: Int,
    cellType: Int,
    isPending: Boolean,
    hybridContent: Content
) : AbsGeminiCell(
    role = role,
    cellType = cellType,
    isPending = isPending
)