package io.dev.relic.feature.function.todo.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.ext.ViewModelExt.setState
import io.common.util.LogUtil
import io.data.entity.todo.TodoEntity
import io.data.mappers.TodoDataMapper.toModelList
import io.dev.relic.feature.function.todo.TodoDataState
import io.domain.use_case.todo.TodoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    application: Application,
    private val todoUseCase: TodoUseCase
) : AndroidViewModel(application) {

    /**
     * The todo data flow.
     * */
    private val todoDataStateFlow = MutableStateFlow<TodoDataState>(TodoDataState.Init)

    companion object {
        private const val TAG = "TodoViewModel"
    }

    init {
        queryTodoData()
    }

    fun getTodoDataStateFlow(): StateFlow<TodoDataState> {
        return todoDataStateFlow
    }

    fun createTodo(entity: TodoEntity) {
        viewModelScope.launch {
            todoUseCase.addTodo.invoke(entity)
        }
    }

    fun updateTodo(entity: TodoEntity) {
        viewModelScope.launch {
            todoUseCase.updateTodo.invoke(entity)
        }
    }

    fun removeTodo(entity: TodoEntity) {
        viewModelScope.launch {
            todoUseCase.deleteTodo.invoke(entity)
        }
    }

    private fun queryTodoData() {
        viewModelScope.launch {
            todoUseCase.getAllTodos()
                .stateIn(this)
                .collect {
                    handleTodoData(it)
                }
        }
    }

    private fun handleTodoData(todoEntities: List<TodoEntity>) {
        if (todoEntities.isNotEmpty()) {
            LogUtil.d(TAG, "[Handle Todo Data] Succeed, data: $todoEntities")
            val todoModelList = todoEntities.toModelList()
            setState(todoDataStateFlow, TodoDataState.QuerySucceed(todoModelList))
        } else {
            LogUtil.w(TAG, "[Handle Todo Data] No Data.")
            setState(todoDataStateFlow, TodoDataState.NoTodoData)
        }
    }

}