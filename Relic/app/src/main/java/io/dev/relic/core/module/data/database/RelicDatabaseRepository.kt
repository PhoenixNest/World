package io.dev.relic.core.module.data.database

import io.dev.relic.core.module.data.database.dao.TodoDao
import io.dev.relic.core.module.data.database.dao.WeatherDao
import io.dev.relic.core.module.data.database.entity.TodoEntity
import io.dev.relic.core.module.data.database.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @see RelicDatabase
 *
 * @see WeatherDao
 * @see WeatherEntity
 *
 * @see TodoDao
 * @see TodoEntity
 * */
@Singleton
class RelicDatabaseRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val todoDao: TodoDao,
) {

    /* ======================== Weather ======================== */

    fun readWeatherDataCache(): Flow<List<WeatherEntity>> {
        return weatherDao.readWeatherDataCache()
    }

    suspend fun insertWeatherData(weatherEntity: WeatherEntity) {
        weatherDao.insertWeatherData(weatherEntity)
    }

    /* ======================== TodoList ======================== */

    fun readAllTodos(): SharedFlow<List<TodoEntity>> {
        return todoDao.readAllTodos()
    }

    suspend fun insertTodo(todoEntity: TodoEntity) {
        todoDao.insertTodo(todoEntity)
    }

    suspend fun updateTodo(todoEntity: TodoEntity) {
        todoDao.updateTodo(todoEntity)
    }

    suspend fun deleteTodo(todoEntity: TodoEntity) {
        todoDao.deleteTodo(todoEntity)
    }

    suspend fun deleteAllTodos() {
        todoDao.deleteAll()
    }

}