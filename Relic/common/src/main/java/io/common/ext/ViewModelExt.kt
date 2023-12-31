package io.common.ext

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

object ViewModelExt {

    fun <T> AndroidViewModel.setState(
        stateFlow: MutableStateFlow<T>,
        newState: T
    ) {
        viewModelScope.launch {
            stateFlow.emit(newState)
        }
    }
}