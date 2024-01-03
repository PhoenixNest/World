package io.domain.use_case.todo.action

import io.common.util.LogUtil
import io.data.entity.todo.TodoEntity
import io.domain.repository.ITodoDataRepository

class DeleteTodo(private val todoRepository: ITodoDataRepository) {

    companion object {
        private const val TAG = "DeleteTodo"
    }

    suspend operator fun invoke(entity: TodoEntity) {
        todoRepository.deleteTodoTask(
            entity = entity
        ).also {
            LogUtil.debug(TAG, "[Delete Todo] todoDataModel: $entity")
        }
    }

}