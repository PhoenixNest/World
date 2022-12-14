package io.dev.android.game.ui.one_line_finish.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OneLineFinishViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {
}