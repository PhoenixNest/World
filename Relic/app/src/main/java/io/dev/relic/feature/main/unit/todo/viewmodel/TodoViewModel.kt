package io.dev.relic.feature.main.unit.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev.relic.core.data.database.entity.TodoEntity
import io.dev.relic.domain.repository.ITodoDataRepository
import io.dev.relic.global.utils.LogUtil
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    application: Application,
    private val todoDataRepository: ITodoDataRepository
) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "TodoViewModel"
    }

    /* ======================== Local ======================== */

    /**
     * Read all todo info data according from database.
     * */
    private fun loadTodoDataFromDatabase() {
        LogUtil.verbose(TAG, "[Todo] Start loading data from the database.")

        viewModelScope.launch {
            // Query todo data from database.
            todoDataRepository.readAllTodos().onEach { todoEntityList: List<TodoEntity> ->
                // Check the loading status of read action.
                if (todoEntityList.isNotEmpty()) {
                    LogUtil.debug(TAG, "[Todo] Data loaded successfully.")
                } else {
                    LogUtil.warning(TAG, "[Todo] No data.")
                }
            }.launchIn(this)
        }
    }

}