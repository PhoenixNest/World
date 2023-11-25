package io.domain.use_case.todo.action

import io.data.entity.TodoEntity
import io.domain.repository.ITodoDataRepository
import io.common.util.LogUtil

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