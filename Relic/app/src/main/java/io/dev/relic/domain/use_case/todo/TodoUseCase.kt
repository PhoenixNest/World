package io.dev.relic.domain.use_case.todo

data class TodoUseCase(
    val addTodo: AddTodo,
    val deleteTodo: DeleteTodo,
    val getAllTodos: GetAllTodos,
    val updateTodo: UpdateTodo
)
