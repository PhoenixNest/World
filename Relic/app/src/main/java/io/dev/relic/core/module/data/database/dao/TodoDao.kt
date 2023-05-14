package io.dev.relic.core.module.data.database.dao

import androidx.room.*
import io.dev.relic.core.module.data.database.entity.TodoEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TodoDao {

    @Query("SELECT * FROM table_todo")
    fun queryAllTodos(): Flow<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todoEntity: TodoEntity)

    @Update
    suspend fun updateTodo(todoEntity: TodoEntity)

    @Delete
    suspend fun deleteTodo(todoEntity: TodoEntity)

    @Query("DELETE FROM table_todo")
    suspend fun deleteAll()

}