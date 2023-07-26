package io.dev.relic.core.data.database

import androidx.room.BuiltInTypeConverters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.dev.relic.core.data.database.convertors.FoodRecipesDataConvertor
import io.dev.relic.core.data.database.convertors.WeatherDataConvertor
import io.dev.relic.core.data.database.dao.FoodRecipesDao
import io.dev.relic.core.data.database.dao.TodoDao
import io.dev.relic.core.data.database.dao.WeatherDao
import io.dev.relic.core.data.database.entity.FoodRecipesComplexSearchEntity
import io.dev.relic.core.data.database.entity.TodoEntity
import io.dev.relic.core.data.database.entity.WeatherEntity

@Database(
    entities = [
        TodoEntity::class,
        WeatherEntity::class,
        FoodRecipesComplexSearchEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    value = [
        WeatherDataConvertor::class,
        FoodRecipesDataConvertor::class,
    ],
    builtInTypeConverters = BuiltInTypeConverters()
)
abstract class RelicDatabase : RoomDatabase() {

    abstract fun notesDao(): TodoDao

    abstract fun weatherDao(): WeatherDao

    abstract fun foodRecipesDao(): FoodRecipesDao

}