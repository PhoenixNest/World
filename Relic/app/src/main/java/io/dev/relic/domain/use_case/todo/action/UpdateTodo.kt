package io.dev.relic.domain.use_case.todo.action

import io.dev.relic.core.data.database.mappers.TodoDataMapper.toTodoEntity
import io.dev.relic.domain.model.todo.TodoDataModel
import io.dev.relic.domain.repository.ITodoDataRepository
import io.dev.relic.global.utils.LogUtil

class UpdateTodo(private val todoRepository: ITodoDataRepository) {

    companion object {
        private const val TAG = "UpdateTodo"
    }

    suspend operator fun invoke(todoDataModel: TodoDataModel) {
        todoRepository.updateTodo(
            todoDataModel.toTodoEntity()
        ).also {
            LogUtil.debug(TAG, "[Update Todo] todoDataModel: $todoDataModel")
        }
    }

}