package io.dev.relic.domain.use_case.todo

import io.dev.relic.core.data.database.entity.TodoEntity
import io.dev.relic.domain.repository.ITodoDataRepository
import kotlinx.coroutines.flow.map

class GetAllTodos(private val todoRepository: ITodoDataRepository) {

    operator fun invoke() {
        todoRepository.readAllTodos().map { todoEntities: List<TodoEntity> ->
            todoEntities.sortedBy { todoEntity: TodoEntity ->
                todoEntity.title.lowercase()
            }
        }
    }

}