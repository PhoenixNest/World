package io.dev.android.composer.notes.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.dev.android.composer.notes.feature.note.data.data_source.NoteDatabase
import io.dev.android.composer.notes.feature.note.data.repository.NoteRepositoryImpl
import io.dev.android.composer.notes.feature.note.domin.repository.NoteRepository
import io.dev.android.composer.notes.feature.note.domin.user_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(application: Application): NoteDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NoteDatabase::class.java,
            name = NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNotesRepository(database: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(database.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUserCases(repository: NoteRepository): NoteUserCases {
        return NoteUserCases(
            getNotes = GetNotes(repository),
            getNoteById = GetNoteById(repository),
            addNote = AddNote(repository),
            deleteNote = DeleteNote(repository)
        )
    }
}