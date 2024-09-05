package io.agent.gemini.model

import java.util.Calendar
import java.util.UUID

abstract class AbsGeminiCell(
    val id: String = UUID.randomUUID().toString(),
    val roleId: Int,
    val cellType: Int,
    val isPending: Boolean,
    val chatCellTimeMinus: Long = Calendar.getInstance().timeInMillis
)