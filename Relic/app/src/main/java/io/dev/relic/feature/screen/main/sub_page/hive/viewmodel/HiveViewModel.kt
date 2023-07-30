package io.dev.relic.feature.screen.main.sub_page.hive.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev.relic.core.data.database.entity.TodoEntity
import io.dev.relic.domain.repository.ITodoDataRepository
import io.dev.relic.global.utils.LogUtil
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HiveViewModel @Inject constructor(
    application: Application,
    private val todoRepository: ITodoDataRepository
) : AndroidViewModel(application) {

    val todoData: StateFlow<List<TodoEntity>> = todoRepository.readAllTodos().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5 * 1000),
        initialValue = emptyList()
    )

    companion object {
        private const val TAG = "HiveViewModel"
    }

    fun createTodoTask(todoEntity: TodoEntity) {
        viewModelScope.launch {
            LogUtil.debug(TAG, "[Create Todo Task]: $todoEntity")
            todoRepository.insertTodoTask(entity = todoEntity)
        }
    }

    fun updateTodoTask(todoEntity: TodoEntity) {
        viewModelScope.launch {
            todoRepository.updateTodoTask(todoEntity)
        }
    }

    fun deleteTodoTask(todoEntity: TodoEntity) {
        viewModelScope.launch {
            LogUtil.debug(TAG, "[Delete Todo Task]: $todoEntity")
            todoRepository.deleteTodoTask(todoEntity)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            todoRepository.deleteAllTodoTasks()
        }
    }
}