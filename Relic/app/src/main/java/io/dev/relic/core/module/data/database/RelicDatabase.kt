package io.dev.relic.core.module.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.dev.relic.core.module.data.database.dao.TodoDao
import io.dev.relic.core.module.data.database.dao.WeatherDao
import io.dev.relic.core.module.data.database.entity.TodoEntity

@Database(
    entities = [TodoEntity::class],
    version = 1,
    exportSchema = true
)
abstract class RelicDatabase : RoomDatabase() {

    abstract fun notesDao(): TodoDao

    abstract fun weatherDao(): WeatherDao

}