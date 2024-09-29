package io.common.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

object ViewModelExt {

    fun ViewModel.operationInViewModelScope(
        dispatcherContext: CoroutineContext = Dispatchers.Default,
        operation: suspend (viewModelScope: CoroutineScope) -> Unit
    ) {
        viewModelScope.launch(dispatcherContext) {
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