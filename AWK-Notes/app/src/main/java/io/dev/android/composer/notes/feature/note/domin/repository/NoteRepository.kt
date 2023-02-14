package io.dev.android.composer.notes.feature.note.domin.repository

import io.dev.android.composer.notes.feature.note.domin.model.NoteModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<NoteModel>>

    suspend fun getNoteById(id: Int): NoteModel?

    suspend fun insertNote(noteModel: NoteModel)

    suspend fun deleteNote(noteModel: NoteModel)

}