package io.core.database.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.core.database.RelicDatabase
import io.core.database.dao.FoodRecipesDao
import io.core.database.dao.NewsDao
import io.core.database.dao.TodoDao
import io.core.database.dao.WeatherDao
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

    @Provides
    @Singleton
    fun provideWeatherDao(database: RelicDatabase): WeatherDao {
        return database.weatherDao()
    }

    @Provides
    @Singleton
    fun provideFoodRecipesDao(database: RelicDatabase): FoodRecipesDao {
        return database.foodRecipesDao()
    }

    @Provides
    @Singleton
    fun provideNewsDao(database: RelicDatabase): NewsDao {
        return database.newsDao()
    }
}