package io.dev.relic.domain.use_case.todo.action

import io.dev.relic.core.data.database.entity.TodoEntity
import io.dev.relic.core.data.database.util.InvalidTodoException
import io.dev.relic.domain.repository.ITodoDataRepository
import io.dev.relic.global.utils.LogUtil

class AddTodo(private val todoRepository: ITodoDataRepository) {

    companion object {
        private const val TAG = "AddTodo"
    }

    suspend operator fun invoke(entity: TodoEntity) {
        if (entity.title.isBlank()) {
            throw InvalidTodoException(message = "The title of Todo task can't be empty.")
        }

        if (entity.content.isBlank()) {
            throw InvalidTodoException(message = "The content of Todo task can't be empty.")
        }

        todoRepository.insertTodoTask(
            entity = entity
        ).also {
            LogUtil.debug(TAG, "[Add Todo] todoDataModel: $entity")
        }
    }

}