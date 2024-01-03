package io.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.data.entity.agent.AgentGeminiChatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AgentGeminiDao {

    @Query("SELECT * FROM table_agent_gemini_chat_history GROUP BY window_id = :id")
    fun queryChatWindowById(id: Int): Flow<List<AgentGeminiChatEntity>>

    @Insert(entity = AgentGeminiChatEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(entity: AgentGeminiChatEntity)
}