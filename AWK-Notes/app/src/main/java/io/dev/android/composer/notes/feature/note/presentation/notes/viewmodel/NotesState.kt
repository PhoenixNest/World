package io.dev.android.composer.notes.feature.note.presentation.notes.viewmodel

import io.dev.android.composer.notes.feature.note.domin.model.NoteModel
import io.dev.android.composer.notes.feature.note.domin.util.NoteOrder
import io.dev.android.composer.notes.feature.note.domin.util.OrderType

data class NotesState(
    val notesList: List<NoteModel> = emptyList(),
    val notesOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSelectionVisible: Boolean = false
)