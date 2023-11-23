package io.dev.relic.core.repository

import io.data.entity.TodoEntity
import io.dev.relic.domain.repository.ITodoDataRepository
import io.module.core.database.repository.RelicDatabaseRepository
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
     * Query todo task by id.
     * */
    override fun searchTodoTaskById(id: Int): TodoEntity {
        return databaseRepository.searchTodoById(id)
    }

    /**
     * Insert the new todo data to database.
     * */
    override suspend fun insertTodoTask(entity: TodoEntity) {
        databaseRepository.insertTodo(entity)
    }

    /**
     * Update the properties for the passed todo data.
     * */
    override suspend fun updateTodoTask(entity: TodoEntity) {
        databaseRepository.updateTodo(entity)
    }

    /**
     * Delete the specified todo data from database.
     * */
    override suspend fun deleteTodoTask(entity: TodoEntity) {
        databaseRepository.deleteTodo(entity)
    }

    /**
     * Delete all the data from database temporary.
     * */
    override suspend fun deleteAllTodoTasks() {
        databaseRepository.deleteAllTodos()
    }

}