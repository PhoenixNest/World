package io.dev.relic.domain.use_case.todo

import io.dev.relic.domain.use_case.todo.action.AddTodo
import io.dev.relic.domain.use_case.todo.action.DeleteTodo
import io.dev.relic.domain.use_case.todo.action.GetAllTodos
import io.dev.relic.domain.use_case.todo.action.UpdateTodo

internal const val TAG = "TodoUnitUseCase"

data class TodoUnitUseCase(
    val addTodo: AddTodo,
    val deleteTodo: DeleteTodo,
    val getAllTodos: GetAllTodos,
    val updateTodo: UpdateTodo
)
