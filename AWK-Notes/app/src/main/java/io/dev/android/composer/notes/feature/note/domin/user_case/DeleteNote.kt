package io.dev.android.composer.notes.feature.note.domin.user_case

import io.dev.android.composer.notes.feature.note.domin.model.NoteModel
import io.dev.android.composer.notes.feature.note.domin.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(noteModel: NoteModel) {
        repository.deleteNote(noteModel)
    }

}