package io.dev.relic.core.repository

import io.dev.relic.core.module.data.database.RelicDatabaseRepository
import io.dev.relic.core.module.data.database.entity.TodoEntity
import io.dev.relic.domain.repository.ITodoDataRepository
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

/**
 * @see io.dev.relic.domain.repository.ITodoDataRepository
 * */
class TodoDataRepositoryImpl @Inject constructor(
    private val databaseRepository: RelicDatabaseRepository
) : ITodoDataRepository {

    /**
     * Query all the current user's todo-list data from database.
     * */
    override fun readAllTodos(): SharedFlow<List<TodoEntity>> {
        return databaseRepository.readAllTodos()
    }

    /**
     * Insert the new todo data to database.
     * */
    override suspend fun insertTodo(todoEntity: TodoEntity) {
        databaseRepository.insertTodo(todoEntity)
    }

    /**
     * Update the properties for the passed todo data.
     * */
    override suspend fun updateTodo(todoEntity: TodoEntity) {
        databaseRepository.updateTodo(todoEntity)
    }

    /**
     * Delete the specify todo data from database.
     * */
    override suspend fun deleteTodo(todoEntity: TodoEntity) {
        databaseRepository.deleteTodo(todoEntity)
    }

    /**
     * Delete all the data from database temporary.
     * */
    override suspend fun deleteAllTodos() {
        databaseRepository.deleteAllTodos()
    }

}