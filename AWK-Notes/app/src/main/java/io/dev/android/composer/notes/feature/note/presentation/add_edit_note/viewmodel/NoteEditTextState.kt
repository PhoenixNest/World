package io.dev.android.composer.notes.feature.note.presentation.add_edit_note.viewmodel

data class NoteEditTextState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)