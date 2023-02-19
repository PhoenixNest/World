package io.dev.android.composer.notes.feature.note.domin.user_case

data class NoteUserCases(
    val getNotes: GetNotes,
    val getNoteById: GetNoteById,
    val addNote: AddNote,
    val deleteNote: DeleteNote
)
