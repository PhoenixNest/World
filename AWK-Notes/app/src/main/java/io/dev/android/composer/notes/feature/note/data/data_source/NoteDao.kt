package io.dev.android.composer.notes.feature.note.data.data_source

import androidx.room.*
import io.dev.android.composer.notes.feature.note.domin.model.NoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("select * from note_table")
    fun getNotes(): Flow<List<NoteModel>>

    @Query("select * from note_table where id = :id")
    suspend fun getNotesById(id: Int): NoteModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteModel: NoteModel)

    @Delete
    suspend fun deleteNote(noteModel: NoteModel)

}