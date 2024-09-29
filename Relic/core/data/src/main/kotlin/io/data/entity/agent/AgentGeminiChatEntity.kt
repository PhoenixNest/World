package io.data.entity.agent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_agent_gemini_chat_history")
data class AgentGeminiChatEntity(
    @ColumnInfo(name = "window_id")
    val windowId: Int,
    @ColumnInfo(name = "is_user")
    val isUser: Boolean,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "date")
    val chatDate: String
) {
    @ColumnInfo(name = "uid", index = true)
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}