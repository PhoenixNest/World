package io.dev.android.composer.notes.feature.note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import io.dev.android.composer.notes.feature.note.domin.model.NoteModel

@Database(
    entities = [NoteModel::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "db_notes"
    }
}