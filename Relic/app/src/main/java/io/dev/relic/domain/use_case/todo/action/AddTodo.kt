package io.dev.relic.domain.use_case.todo.action

import io.dev.relic.core.data.database.mappers.TodoDataMapper.toTodoEntity
import io.dev.relic.domain.model.todo.InvalidTodoException
import io.dev.relic.domain.model.todo.TodoDataModel
import io.dev.relic.domain.repository.ITodoDataRepository
import io.dev.relic.global.utils.LogUtil

class AddTodo(private val todoRepository: ITodoDataRepository) {

    companion object {
        private const val TAG = "AddTodo"
    }

    suspend operator fun invoke(todoDataModel: TodoDataModel) {
        if (todoDataModel.title.isBlank()) {
            throw InvalidTodoException(message = "The title of Todo task can't be empty.")
        }

        if (todoDataModel.content.isBlank()) {
            throw InvalidTodoException(message = "The content of Todo task can't be empty.")
        }

        todoRepository.insertTodoTask(
            todoEntity = todoDataModel.toTodoEntity()
        ).also {
            LogUtil.debug(TAG, "[Add Todo] todoDataModel: $todoDataModel")
        }
    }

}