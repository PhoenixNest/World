package io.core.database.dao.agent

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.data.entity.agent.AgentChatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AgentDao {

    @Query("SELECT * FROM table_agent_chat_history WHERE agent_type = :agentType")
    fun queryChatWindowById(agentType: Int): Flow<List<AgentChatEntity>>

    @Insert(entity = AgentChatEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatWindow(entity: AgentChatEntity)

    @Delete(entity = AgentChatEntity::class)
    suspend fun deleteChatWindow(entity: AgentChatEntity)

}