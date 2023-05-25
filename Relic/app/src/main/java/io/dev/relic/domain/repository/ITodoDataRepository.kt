package io.dev.relic.domain.repository

import io.dev.relic.core.module.data.database.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

/**
 * @see io.dev.relic.core.repository.TodoDataRepositoryImpl
 * */
interface ITodoDataRepository {

    /**
     * Query all the current user's todo-list data from database.
     * */
    fun readAllTodos(): Flow<List<TodoEntity>>

    /**
     * Insert the new todo data to database.
     * */
    suspend fun insertTodo(todoEntity: TodoEntity)

    /**
     * Update the properties for the passed todo data.
     * */
    suspend fun updateTodo(todoEntity: TodoEntity)

    /**
     * Delete the specify todo data from database.
     * */
    suspend fun deleteTodo(todoEntity: TodoEntity)

    /**
     * Delete all the data from database temporary.
     * */
    suspend fun deleteAllTodos()

}