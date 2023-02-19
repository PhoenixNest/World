package io.dev.android.composer.notes.feature.note.domin.user_case

import io.dev.android.composer.notes.feature.note.domin.model.NoteModel
import io.dev.android.composer.notes.feature.note.domin.repository.NoteRepository

class GetNoteById(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): NoteModel? {
        return repository.getNoteById(id)
    }

}