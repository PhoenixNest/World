package io.core.database.repository

import io.core.database.RelicDatabase
import io.core.database.dao.FoodRecipesDao
import io.core.database.dao.NewsDao
import io.core.database.dao.PixabayDao
import io.core.database.dao.TodoDao
import io.core.database.dao.WeatherDao
import io.data.entity.food_recipes.FoodRecipesComplexSearchEntity
import io.data.entity.news.TopHeadlineNewsArticleEntity
import io.data.entity.news.TopHeadlinesNewsEntity
import io.data.entity.news.TrendingNewsArticleEntity
import io.data.entity.news.TrendingNewsEntity
import io.data.entity.pixabay.PixabayImagesEntity
import io.data.entity.todo.TodoEntity
import io.data.entity.weather.WeatherEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @see RelicDatabase
 * */
@Singleton
class RelicDatabaseRepository @Inject constructor(
    private val todoDao: TodoDao,
    private val weatherDao: WeatherDao,
    private val foodRecipesDao: FoodRecipesDao,
    private val newsDao: NewsDao,
    private val pixabayDao: PixabayDao
) {

    /* ======================== Todo ======================== */

    fun queryAllTodosData(): Flow<List<TodoEntity>> {
        return todoDao.queryAllTodosData()
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

    fun queryWeatherData(): Flow<List<WeatherEntity>> {
        return weatherDao.queryWeatherData()
    }

    suspend fun insertWeatherData(weatherEntity: WeatherEntity) {
        weatherDao.insertWeatherData(weatherEntity)
    }

    /* ======================== Food Recipes ======================== */

    fun queryAllComplexSearchRecipesData(): Flow<List<FoodRecipesComplexSearchEntity>> {
        return foodRecipesDao.queryAllComplexSearchData()
    }

    suspend fun insertComplexSearchRecipesData(complexSearchEntity: FoodRecipesComplexSearchEntity) {
        foodRecipesDao.insertComplexSearchData(complexSearchEntity)
    }

    suspend fun deleteAllComplexSearchData() {
        foodRecipesDao.deleteAllComplexSearchData()
    }

    /* ======================== News - Trending ======================== */

    fun queryAllTrendingNewsData(): Flow<List<TrendingNewsEntity>> {
        return newsDao.queryAllTrendingNewsData()
    }

    fun queryAllTrendingNewsArticlesData(): Flow<List<TrendingNewsArticleEntity>> {
        return newsDao.queryAllTrendingNewsArticlesData()
    }

    suspend fun insertTrendingNewsData(trendingNewsEntity: TrendingNewsEntity) {
        newsDao.insertTrendingNewsData(trendingNewsEntity)
    }

    suspend fun insertNewsEverythingArticle(articleEntity: TrendingNewsArticleEntity) {
        newsDao.insertTrendingNewsArticle(articleEntity)
    }

    /* ======================== News - Top-Headline ======================== */

    fun queryAllTopHeadlineNewsData(): Flow<List<TopHeadlinesNewsEntity>> {
        return newsDao.queryAllTopHeadlineNewsData()
    }

    fun queryAllTopHeadlineNewsArticlesData(): Flow<List<TopHeadlineNewsArticleEntity>> {
        return newsDao.queryAllTopHeadlineNewsArticlesData()
    }

    suspend fun insertNewsTopHeadlineData(topHeadlinesNewsEntity: TopHeadlinesNewsEntity) {
        newsDao.insertTopHeadlineNewsData(topHeadlinesNewsEntity)
    }

    suspend fun insertTopHeadlineNewsArticle(articleEntity: TopHeadlineNewsArticleEntity) {
        newsDao.insertTopHeadlineNewsArticle(articleEntity)
    }

    /* ======================== Pixaaby ======================== */

    fun queryAllPixabayImagesData(): Flow<List<PixabayImagesEntity>> {
        return pixabayDao.queryAllImagesData()
    }

    suspend fun insertPixabxyImagesData(pixabayImagesEntity: PixabayImagesEntity) {
        pixabayDao.insertImagesData(pixabayImagesEntity)
    }

}