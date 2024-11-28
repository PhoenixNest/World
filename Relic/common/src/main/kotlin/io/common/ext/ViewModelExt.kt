package io.common.ext

import kotlinx.coroutines.flow.MutableStateFlow

object ViewModelExt {
    fun <T> setState(
        stateFlow: MutableStateFlow<T>,
        newState: T
    ) {
        stateFlow.tryEmit(newState)
    }
}