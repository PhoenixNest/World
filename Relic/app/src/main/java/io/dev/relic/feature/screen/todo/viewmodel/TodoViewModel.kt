package io.dev.relic.feature.screen.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev.relic.core.data.database.entity.TodoEntity
import io.dev.relic.domain.use_case.todo.TodoUnitUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    application: Application,
    private val todoUnitUseCase: TodoUnitUseCase
) : AndroidViewModel(application) {

    /**
     * Read all todo info data according from database.
     * */
    val todoStateFlow: StateFlow<List<TodoEntity>> = todoUnitUseCase.getAllTodos.invoke().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5 * 1000L),
        initialValue = emptyList()
    )

    companion object {
        private const val TAG = "TodoViewModel"
    }

    /* ======================== Local ======================== */

    fun sortTodoDataList() {

    }

}