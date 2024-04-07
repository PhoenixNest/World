package io.dev.relic.feature.function.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.ext.ViewModelExt.operationInViewModelScope
import io.common.ext.ViewModelExt.setState
import io.common.util.LogUtil
import io.data.entity.todo.TodoEntity
import io.data.mappers.TodoDataMapper.toModelList
import io.dev.relic.feature.function.todo.TodoDataState
import io.domain.use_case.todo.TodoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    application: Application,
    private val todoUseCase: TodoUseCase
) : AndroidViewModel(application) {

    /**
     * The todo data flow.
     * */
    private val _todoDataStateFlow = MutableStateFlow<TodoDataState>(TodoDataState.Init)
    val todoDataStateFlow: StateFlow<TodoDataState> get() = _todoDataStateFlow

    companion object {
        private const val TAG = "TodoViewModel"
    }

    init {
        queryTodoData()
    }

    fun createTodo(entity: TodoEntity) {
        operationInViewModelScope {
            todoUseCase.addTodo.invoke(entity)
        }
    }

    fun updateTodo(entity: TodoEntity) {
        operationInViewModelScope {
            todoUseCase.updateTodo.invoke(entity)
        }
    }

    fun removeTodo(entity: TodoEntity) {
        operationInViewModelScope {
            todoUseCase.deleteTodo.invoke(entity)
        }
    }

    private fun queryTodoData() {
        operationInViewModelScope { scope ->
            todoUseCase.getAllTodos()
                .stateIn(scope)
                .collect {
                    handleTodoData(it)
                }
        }
    }

    private fun handleTodoData(todoEntities: List<TodoEntity>) {
        if (todoEntities.isNotEmpty()) {
            LogUtil.d(TAG, "[Handle Todo Data] Succeed, data: $todoEntities")
            val todoModelList = todoEntities.toModelList()
            setState(_todoDataStateFlow, TodoDataState.QuerySucceed(todoModelList))
        } else {
            LogUtil.w(TAG, "[Handle Todo Data] No Data.")
            setState(_todoDataStateFlow, TodoDataState.NoTodoData)
        }
    }

}