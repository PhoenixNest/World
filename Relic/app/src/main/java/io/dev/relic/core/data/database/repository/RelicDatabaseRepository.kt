package io.dev.relic.core.data.database.repository

import io.dev.relic.core.data.database.dao.TodoDao
import io.dev.relic.core.data.database.dao.WeatherDao
import io.dev.relic.core.data.database.entity.TodoEntity
import io.dev.relic.core.data.database.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow
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

    fun readAllTodos(): Flow<List<TodoEntity>> {
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