package io.core.database.repository

import io.core.database.dao.FoodRecipesDao
import io.core.database.dao.NewsDao
import io.core.database.dao.TodoDao
import io.core.database.dao.WeatherDao
import io.data.entity.FoodRecipesComplexSearchEntity
import io.data.entity.NewsEverythingArticleEntity
import io.data.entity.NewsEverythingEntity
import io.data.entity.NewsTopHeadlineArticleEntity
import io.data.entity.NewsTopHeadlinesEntity
import io.data.entity.TodoEntity
import io.data.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @see io.core.database.RelicDatabase
 * */
@Singleton
class RelicDatabaseRepository @Inject constructor(
    private val todoDao: TodoDao,
    private val weatherDao: WeatherDao,
    private val foodRecipesDao: FoodRecipesDao,
    private val newsDao: NewsDao
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

    /* ======================== News ======================== */

    fun readNewsEverythingCache(): Flow<List<NewsEverythingEntity>> {
        return newsDao.readAllNewsEverythingData()
    }

    suspend fun insertNewsEverythingData(newsEverythingEntity: NewsEverythingEntity) {
        newsDao.insertNewsEverythingData(newsEverythingEntity)
    }

    suspend fun insertNewsEverythingArticle(articleEntity: NewsEverythingArticleEntity) {
        newsDao.insertNewsEverythingArticle(articleEntity)
    }

    fun readNewsTopHeadlineCache(): Flow<List<NewsTopHeadlinesEntity>> {
        return newsDao.readAllNewsTopHeadlineData()
    }

    suspend fun insertNewsTopHeadlineData(newsTopHeadlinesEntity: NewsTopHeadlinesEntity) {
        newsDao.insertNewsTopHeadlineData(newsTopHeadlinesEntity)
    }

    suspend fun insertNewsTopHeadlineArticle(articleEntity: NewsTopHeadlineArticleEntity) {
        newsDao.insertNewsTopHeadlineArticle(articleEntity)
    }

}