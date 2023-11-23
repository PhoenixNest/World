package io.module.core.database.repository

import io.data.entity.FoodRecipesComplexSearchEntity
import io.data.entity.TodoEntity
import io.data.entity.WeatherEntity
import io.module.core.database.dao.FoodRecipesDao
import io.module.core.database.dao.TodoDao
import io.module.core.database.dao.WeatherDao
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
    private val foodRecipesDao: FoodRecipesDao,
    private val todoDao: TodoDao,
) {

    /* ======================== TodoList ======================== */

    fun readAllTodos(): Flow<List<TodoEntity>> {
        return todoDao.readAllTodos()
    }

    fun searchTodoById(id: Int): TodoEntity {
        return todoDao.queryTodoById(id)
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

    /* ======================== Weather ======================== */

    fun readWeatherDataCache(): Flow<List<WeatherEntity>> {
        return weatherDao.readWeatherDataCache()
    }

    suspend fun insertWeatherData(weatherEntity: WeatherEntity) {
        weatherDao.insertWeatherData(weatherEntity)
    }

    /* ======================== FoodRecipes ======================== */

    fun readComplexSearchRecipesCache(): Flow<List<FoodRecipesComplexSearchEntity>> {
        return foodRecipesDao.readCacheComplexSearchData()
    }

    suspend fun insertComplexSearchRecipesData(complexSearchEntity: FoodRecipesComplexSearchEntity) {
        foodRecipesDao.insertComplexSearchData(complexSearchEntity)
    }

}