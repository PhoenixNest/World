package io.dev.relic.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.dev.relic.core.data.database.convertors.WeatherDataConvertor
import io.dev.relic.core.data.database.dao.TodoDao
import io.dev.relic.core.data.database.dao.WeatherDao
import io.dev.relic.core.data.database.entity.TodoEntity
import io.dev.relic.core.data.database.entity.WeatherEntity

@Database(
    entities = [
        WeatherEntity::class,
        TodoEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(WeatherDataConvertor::class)
abstract class RelicDatabase : RoomDatabase() {

    abstract fun notesDao(): TodoDao

    abstract fun weatherDao(): WeatherDao

}