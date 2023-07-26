package io.dev.relic.core.repository

import io.dev.relic.core.data.database.entity.TodoEntity
import io.dev.relic.core.data.database.repository.RelicDatabaseRepository
import io.dev.relic.domain.repository.ITodoDataRepository
import kotlinx.coroutines.flow.Flow
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
    override fun readAllTodos(): Flow<List<TodoEntity>> {
        return databaseRepository.readAllTodos()
    }

    /**
     * Insert the new todo data to database.
     * */
    override suspend fun insertTodoTask(todoEntity: TodoEntity) {
        databaseRepository.insertTodo(todoEntity)
    }

    /**
     * Update the properties for the passed todo data.
     * */
    override suspend fun updateTodoTask(todoEntity: TodoEntity) {
        databaseRepository.updateTodo(todoEntity)
    }

    /**
     * Delete the specify todo data from database.
     * */
    override suspend fun deleteTodoTask(todoEntity: TodoEntity) {
        databaseRepository.deleteTodo(todoEntity)
    }

    /**
     * Delete all the data from database temporary.
     * */
    override suspend fun deleteAllTodoTasks() {
        databaseRepository.deleteAllTodos()
    }

}