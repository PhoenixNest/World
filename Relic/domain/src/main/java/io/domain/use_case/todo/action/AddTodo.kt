package io.domain.use_case.todo.action

import io.common.util.LogUtil
import io.core.database.util.InvalidTodoException
import io.data.entity.todo.TodoEntity
import io.domain.repository.ITodoDataRepository

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