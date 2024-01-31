package io.core.database

import androidx.room.BuiltInTypeConverters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.core.database.DatabaseParameters.dataBaseVersion
import io.core.database.DatabaseParameters.exportSchema
import io.core.database.dao.agent.AgentDao
import io.core.database.dao.agent.AgentGeminiDao
import io.core.database.dao.FoodRecipesDao
import io.core.database.dao.NewsDao
import io.core.database.dao.TodoDao
import io.core.database.dao.WallpaperDao
import io.core.database.dao.WeatherDao
import io.data.convertors.FoodRecipesDataConvertor
import io.data.convertors.NewsDataConvertor
import io.data.convertors.WallpaperDataConvertor
import io.data.convertors.WeatherDataConvertor
import io.data.entity.agent.AgentChatEntity
import io.data.entity.agent.AgentGeminiChatEntity
import io.data.entity.food_recipes.FoodRecipesComplexSearchEntity
import io.data.entity.news.TrendingNewsArticleEntity
import io.data.entity.news.TrendingNewsEntity
import io.data.entity.news.TopHeadlineNewsArticleEntity
import io.data.entity.news.TopHeadlinesNewsEntity
import io.data.entity.todo.TodoEntity
import io.data.entity.wallpaper.WallpaperImagesEntity
import io.data.entity.weather.WeatherEntity

@Database(
    entities = [
        // Feature: Home
        WeatherEntity::class,
        FoodRecipesComplexSearchEntity::class,
        WallpaperImagesEntity::class,
        // Feature: Hive
        TodoEntity::class,
        TrendingNewsEntity::class,
        TopHeadlinesNewsEntity::class,
        TrendingNewsArticleEntity::class,
        TopHeadlineNewsArticleEntity::class,
        // Feature: Ai
        AgentChatEntity::class,
        AgentGeminiChatEntity::class
    ],
    version = dataBaseVersion,
    exportSchema = exportSchema
)
@TypeConverters(
    value = [
        WeatherDataConvertor::class,
        FoodRecipesDataConvertor::class,
        NewsDataConvertor::class,
        WallpaperDataConvertor::class
    ],
    builtInTypeConverters = BuiltInTypeConverters()
)
abstract class RelicDatabase : RoomDatabase() {

    abstract fun notesDao(): TodoDao

    abstract fun weatherDao(): WeatherDao

    abstract fun foodRecipesDao(): FoodRecipesDao

    abstract fun newsDao(): NewsDao

    abstract fun wallpaperDao(): WallpaperDao

    abstract fun agentDao(): AgentDao

    abstract fun agentGeminiDao(): AgentGeminiDao

}