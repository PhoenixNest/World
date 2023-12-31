package io.core.database

import androidx.room.BuiltInTypeConverters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.core.database.dao.FoodRecipesDao
import io.core.database.dao.NewsDao
import io.core.database.dao.TodoDao
import io.core.database.dao.WeatherDao
import io.data.convertors.FoodRecipesDataConvertor
import io.data.convertors.NewsDataConvertor
import io.data.convertors.WeatherDataConvertor
import io.data.entity.FoodRecipesComplexSearchEntity
import io.data.entity.NewsEverythingArticleEntity
import io.data.entity.NewsEverythingEntity
import io.data.entity.NewsTopHeadlineArticleEntity
import io.data.entity.NewsTopHeadlinesEntity
import io.data.entity.TodoEntity
import io.data.entity.WeatherEntity

@Database(
    entities = [
        TodoEntity::class,
        WeatherEntity::class,
        FoodRecipesComplexSearchEntity::class,
        NewsEverythingEntity::class,
        NewsTopHeadlinesEntity::class,
        NewsEverythingArticleEntity::class,
        NewsTopHeadlineArticleEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    value = [
        WeatherDataConvertor::class,
        FoodRecipesDataConvertor::class,
        NewsDataConvertor::class
    ],
    builtInTypeConverters = BuiltInTypeConverters()
)
abstract class RelicDatabase : RoomDatabase() {

    abstract fun notesDao(): TodoDao

    abstract fun weatherDao(): WeatherDao

    abstract fun foodRecipesDao(): FoodRecipesDao

    abstract fun newsDao(): NewsDao

}