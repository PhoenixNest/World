package io.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.data.entity.todo.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM table_todo")
    fun readAllTodos(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM table_todo WHERE uid = :id")
    fun queryTodoById(id: Int): TodoEntity

    @Insert(entity = TodoEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todoEntity: TodoEntity)

    @Update(entity = TodoEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTodo(todoEntity: TodoEntity)

    @Delete(entity = TodoEntity::class)
    suspend fun deleteTodo(todoEntity: TodoEntity)

    @Query("DELETE FROM table_todo")
    suspend fun deleteAll()

}