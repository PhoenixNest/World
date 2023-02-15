package io.dev.android.composer.notes.feature.note.presentation.add_edit_note.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev.android.composer.notes.feature.note.domin.model.InvalidNoteException
import io.dev.android.composer.notes.feature.note.domin.model.NoteModel
import io.dev.android.composer.notes.feature.note.domin.user_case.NoteUserCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    application: Application,
    private val noteUserCases: NoteUserCases,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val _noteTitle = mutableStateOf(
        NoteEditTextState(
            hint = "Enter title..."
        )
    )
    val noteTitle: State<NoteEditTextState> = _noteTitle

    private val _noteContent = mutableStateOf(
        NoteEditTextState(
            hint = "Enter some content..."
        )
    )
    val noteContent: State<NoteEditTextState> = _noteContent

    private val _noteColor = mutableStateOf(NoteModel.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    /**
     * SnackBar Ui Event
     * */
    sealed class UiEvent {

        data class ShowSnackBar(val message: String) : UiEvent()

        object SaveNote : UiEvent()

    }

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            // Normal navigate usage from exist note
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUserCases.getNoteById(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUserCases.addNote(
                            NoteModel(
                                id = currentNoteId,
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = Calendar.getInstance().timeInMillis,
                                color = noteColor.value
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Can't save note"
                            )
                        )
                    }
                }
            }
        }
    }
}