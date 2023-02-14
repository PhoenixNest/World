package io.dev.android.composer.notes.feature.note.data.repository

import io.dev.android.composer.notes.feature.note.data.data_source.NoteDao
import io.dev.android.composer.notes.feature.note.domin.model.NoteModel
import io.dev.android.composer.notes.feature.note.domin.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<NoteModel>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): NoteModel? {
        return dao.getNotesById(id)
    }

    override suspend fun insertNote(noteModel: NoteModel) {
        dao.insertNote(noteModel)
    }

    override suspend fun deleteNote(noteModel: NoteModel) {
        dao.deleteNote(noteModel)
    }
}