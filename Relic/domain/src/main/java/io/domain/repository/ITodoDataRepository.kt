package io.domain.repository

import io.data.entity.todo.TodoEntity
import kotlinx.coroutines.flow.Flow

/**
 * @see io.domain.repository.impl.TodoDataRepositoryImpl
 * */
interface ITodoDataRepository {

    /**
     * Query all the current user's todo-list data from database.
     * */
    fun readAllTodos(): Flow<List<TodoEntity>>

    /**
     * Query todo task by id.
     * */
    fun searchTodoTaskById(id: Int): TodoEntity

    /**
     * Insert the new todo data to database.
     * */
    suspend fun insertTodoTask(entity: TodoEntity)

    /**
     * Update the properties for the passed todo data.
     * */
    suspend fun updateTodoTask(entity: TodoEntity)

    /**
     * Delete the specified todo data from database.
     * */
    suspend fun deleteTodoTask(entity: TodoEntity)

    /**
     * Delete all the data from database temporary.
     * */
    suspend fun deleteAllTodoTasks()

}