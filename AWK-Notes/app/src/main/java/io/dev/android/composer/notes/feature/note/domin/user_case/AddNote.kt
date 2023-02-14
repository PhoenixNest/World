package io.dev.android.composer.notes.feature.note.domin.user_case

import io.dev.android.composer.notes.feature.note.domin.model.InvalidNoteException
import io.dev.android.composer.notes.feature.note.domin.model.NoteModel
import io.dev.android.composer.notes.feature.note.domin.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(noteModel: NoteModel) {

        if (noteModel.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty.")
        }

        if (noteModel.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty.")
        }

        repository.insertNote(noteModel)
    }

}