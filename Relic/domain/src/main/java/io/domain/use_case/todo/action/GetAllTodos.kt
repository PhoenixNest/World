package io.domain.use_case.todo.action

import io.data.entity.todo.TodoEntity
import io.domain.repository.ITodoDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetAllTodos(private val todoRepository: ITodoDataRepository) {

    operator fun invoke(): Flow<List<TodoEntity>> {
        return todoRepository.readAllTodos()
            .catch {

            }
            .map { todoEntities: List<TodoEntity> ->
                todoEntities.sortedBy { todoEntity: TodoEntity ->
                    todoEntity.title.lowercase()
                }
            }
    }

}