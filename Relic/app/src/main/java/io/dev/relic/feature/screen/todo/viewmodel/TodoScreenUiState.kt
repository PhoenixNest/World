package io.dev.relic.feature.screen.todo.viewmodel

import io.dev.relic.domain.model.todo.TodoDataModel

data class TodoScreenUiState(

    val isLoading: Boolean,

    val todoData: List<TodoDataModel>
)