package io.dev.android.composer.notes.feature.note.presentation.notes

import io.dev.android.composer.notes.feature.note.domin.model.NoteModel
import io.dev.android.composer.notes.feature.note.domin.util.NoteOrder

sealed class NotesEvent {

    data class Order(val noteOrder: NoteOrder) : NotesEvent()

    data class DeleteNote(val noteModel: NoteModel) : NotesEvent()

    object RestoreNote : NotesEvent()

    object ToggleOrderSelection : NotesEvent()

}