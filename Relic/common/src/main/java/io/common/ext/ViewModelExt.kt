package io.common.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

object ViewModelExt {

    fun <T> ViewModel.setState(
        stateFlow: MutableStateFlow<T>,
        newState: T
    ) {
        viewModelScope.launch {
            stateFlow.emit(newState)
        }
    }

    fun ViewModel.operationInViewModelScope(operation: suspend () -> Unit) {
        viewModelScope.launch {
            operation.invoke()
        }
    }
}