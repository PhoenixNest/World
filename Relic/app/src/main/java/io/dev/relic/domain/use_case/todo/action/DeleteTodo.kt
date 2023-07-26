package io.dev.relic.domain.use_case.todo.action

import io.dev.relic.core.data.database.mappers.TodoDataMapper.toTodoEntity
import io.dev.relic.domain.model.todo.TodoDataModel
import io.dev.relic.domain.repository.ITodoDataRepository
import io.dev.relic.global.utils.LogUtil

class DeleteTodo(private val todoRepository: ITodoDataRepository) {

    companion object {
        private const val TAG = "DeleteTodo"
    }

    suspend operator fun invoke(todoDataModel: TodoDataModel) {
        todoRepository.deleteTodoTask(
            todoDataModel.toTodoEntity()
        ).also {
            LogUtil.debug(TAG, "[Delete Todo] todoDataModel: $todoDataModel")
        }
    }

}