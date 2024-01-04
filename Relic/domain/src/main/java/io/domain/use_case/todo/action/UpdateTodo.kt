package io.domain.use_case.todo.action

import io.common.util.LogUtil
import io.data.entity.todo.TodoEntity
import io.domain.repository.ITodoDataRepository

class UpdateTodo(private val todoRepository: ITodoDataRepository) {

    companion object {
        private const val TAG = "UpdateTodo"
    }

    suspend operator fun invoke(entity: TodoEntity) {
        todoRepository.updateTodoTask(
            entity = entity
        ).also {
            LogUtil.d(TAG, "[Update Todo] todoDataModel: $entity")
        }
    }

}