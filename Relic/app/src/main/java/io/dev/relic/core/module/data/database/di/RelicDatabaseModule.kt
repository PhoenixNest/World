package io.dev.relic.core.module.data.database.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.dev.relic.core.module.data.database.RelicDatabase
import io.dev.relic.core.module.data.database.dao.TodoDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RelicDatabaseModule {

    companion object {
        private const val DATABASE_NAME: String = "relic_database"
    }

    @Provides
    @Singleton
    fun provideRelicDatabase(
        application: Application
    ): RelicDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = RelicDatabase::class.java,
            name = DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoDao(database: RelicDatabase): TodoDao {
        return database.notesDao()
    }
}