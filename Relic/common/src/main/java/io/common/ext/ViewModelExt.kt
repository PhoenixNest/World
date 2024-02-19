package io.common.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

object ViewModelExt {

    fun ViewModel.operationInViewModelScope(
        operation: suspend (viewModelScope: CoroutineScope) -> Unit
    ) {
        viewModelScope.launch {
            operation.invoke(this)
        }
    }

    fun <T> ViewModel.setState(
        stateFlow: MutableStateFlow<T>,
        newState: T
    ) {
        operationInViewModelScope {
            stateFlow.emit(newState)
        }
    }
}