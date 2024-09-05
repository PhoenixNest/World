package io.domain.use_case.todo.action

import io.data.entity.todo.TodoEntity
import io.domain.repository.ITodoDataRepository
import kotlinx.coroutines.flow.Flow

class GetAllTodos(private val todoRepository: ITodoDataRepository) {

    operator fun invoke(): Flow<List<TodoEntity>> {
        return todoRepository.queryAllTodos()
    }
}