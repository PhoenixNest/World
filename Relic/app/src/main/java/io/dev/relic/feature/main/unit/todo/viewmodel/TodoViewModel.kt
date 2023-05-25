package io.dev.relic.feature.main.unit.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "TodoViewModel"
    }

}