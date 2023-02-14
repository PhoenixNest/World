package io.dev.android.composer.notes.feature.note.presentation.notes

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev.android.composer.notes.feature.note.domin.model.NoteModel
import io.dev.android.composer.notes.feature.note.domin.user_case.NoteUserCases
import io.dev.android.composer.notes.feature.note.domin.util.NoteOrder
import io.dev.android.composer.notes.feature.note.domin.util.OrderType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    application: Application,
    private val noteUserCases: NoteUserCases
) : AndroidViewModel(application) {

    private val _notesState = mutableStateOf(NotesState())
    val notesState: State<NotesState> = _notesState

    private var recentDeleteNote: NoteModel? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (notesState.value.notesOrder::class == event.noteOrder::class
                    && notesState.value.notesOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }

                getNotes(event.noteOrder)
            }

            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUserCases.deleteNote(event.noteModel)
                    recentDeleteNote = event.noteModel
                }
            }

            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUserCases.addNote(recentDeleteNote ?: return@launch)
                    recentDeleteNote = null
                }
            }

            is NotesEvent.ToggleOrderSelection -> {
                _notesState.value = notesState.value.copy(
                    isOrderSelectionVisible = !notesState.value.isOrderSelectionVisible
                )
            }

        }
    }


    private fun getNotes(notesOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUserCases.getNotes(notesOrder)
            .onEach { notesList ->
                _notesState.value = notesState.value.copy(
                    notesList = notesList,
                    notesOrder = notesOrder
                )
            }.launchIn(viewModelScope)
    }
}