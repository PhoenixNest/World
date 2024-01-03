package io.data.entity.agent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_agent_chat_history")
data class AgentChatEntity(
    @ColumnInfo(name = "agent_type")
    val type: Int
) {
    @ColumnInfo(name = "uid", index = true)
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}