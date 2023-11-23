package io.dev.relic.domain.use_case.todo.action

import io.data.entity.TodoEntity
import io.dev.relic.domain.repository.ITodoDataRepository
import io.dev.relic.global.utils.LogUtil

class UpdateTodo(private val todoRepository: ITodoDataRepository) {

    companion object {
        private const val TAG = "UpdateTodo"
    }

    suspend operator fun invoke(entity: TodoEntity) {
        todoRepository.updateTodoTask(
            entity = entity
        ).also {
            LogUtil.debug(TAG, "[Update Todo] todoDataModel: $entity")
        }
    }

}