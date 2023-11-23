package io.module.core.database

import androidx.room.BuiltInTypeConverters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.data.convertors.FoodRecipesDataConvertor
import io.data.convertors.WeatherDataConvertor
import io.data.entity.FoodRecipesComplexSearchEntity
import io.data.entity.TodoEntity
import io.data.entity.WeatherEntity
import io.module.core.database.dao.FoodRecipesDao
import io.module.core.database.dao.TodoDao
import io.module.core.database.dao.WeatherDao

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