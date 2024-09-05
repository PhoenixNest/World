package io.domain.use_case.todo

import io.domain.use_case.todo.action.AddTodo
import io.domain.use_case.todo.action.DeleteTodo
import io.domain.use_case.todo.action.GetAllTodos
import io.domain.use_case.todo.action.UpdateTodo

internal const val TAG = "TodoUnitUseCase"

data class TodoUseCase(
    val addTodo: AddTodo,
    val deleteTodo: DeleteTodo,
    val getAllTodos: GetAllTodos,
    val updateTodo: UpdateTodo
)
