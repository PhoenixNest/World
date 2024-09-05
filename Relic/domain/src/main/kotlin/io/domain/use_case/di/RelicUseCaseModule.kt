package io.domain.use_case.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.core.database.repository.RelicDatabaseRepository
import io.domain.repository.IFoodRecipesDataRepository
import io.domain.repository.IMaximDataRepository
import io.domain.repository.INewsDataRepository
import io.domain.repository.ITodoDataRepository
import io.domain.repository.IWallpaperDataRepository
import io.domain.repository.IWeatherDataRepository
import io.domain.use_case.food_receipes.FoodRecipesUseCase
import io.domain.use_case.food_receipes.action.complex_search.CacheComplexSearchData
import io.domain.use_case.food_receipes.action.complex_search.GetComplexRecipesData
import io.domain.use_case.food_receipes.action.complex_search.QueryCachedComplexRecipesData
import io.domain.use_case.food_receipes.action.complex_search.RemoveComplexSearchData
import io.domain.use_case.food_receipes.action.information.GetFoodRecipeInformationById
import io.domain.use_case.maxim.MaximUseCase
import io.domain.use_case.maxim.action.GetRandomMaxim
import io.domain.use_case.news.NewsUseCase
import io.domain.use_case.news.action.everything.CacheTrendingNewsData
import io.domain.use_case.news.action.everything.GetTrendingNewsData
import io.domain.use_case.news.action.everything.QueryAllTrendingNewsData
import io.domain.use_case.news.action.top_headline.CacheTopHeadlineNewsData
import io.domain.use_case.news.action.top_headline.GetTopHeadlineNewsData
import io.domain.use_case.news.action.top_headline.QueryAllTopHeadlineNewsData
import io.domain.use_case.todo.TodoUseCase
import io.domain.use_case.todo.action.AddTodo
import io.domain.use_case.todo.action.DeleteTodo
import io.domain.use_case.todo.action.GetAllTodos
import io.domain.use_case.todo.action.UpdateTodo
import io.domain.use_case.wallpaper.WallpaperUseCase
import io.domain.use_case.wallpaper.action.SearchImages
import io.domain.use_case.weather.WeatherUseCase
import io.domain.use_case.weather.action.CacheWeatherData
import io.domain.use_case.weather.action.FetchWeatherData
import io.domain.use_case.weather.action.QueryWeatherData
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RelicUseCaseModule {

    @Singleton
    @Provides
    fun provideTotoUnitUseCase(
        todoRepository: ITodoDataRepository
    ): TodoUseCase {
        return TodoUseCase(
            addTodo = AddTodo(todoRepository),
            deleteTodo = DeleteTodo(todoRepository),
            getAllTodos = GetAllTodos(todoRepository),
            updateTodo = UpdateTodo(todoRepository)
        )
    }

    @Singleton
    @Provides
    fun provideWeatherUnitUseCase(
        weatherDataRepository: IWeatherDataRepository,
        databaseRepository: RelicDatabaseRepository
    ): WeatherUseCase {
        return WeatherUseCase(
            getWeatherData = FetchWeatherData(weatherDataRepository),
            cacheWeatherData = CacheWeatherData(databaseRepository),
            queryWeatherData = QueryWeatherData(databaseRepository)
        )
    }

    @Singleton
    @Provides
    fun provideFoodRecipesUnitUseCase(
        recipesDataRepository: IFoodRecipesDataRepository,
        databaseRepository: RelicDatabaseRepository
    ): FoodRecipesUseCase {
        return FoodRecipesUseCase(
            getComplexRecipesData = GetComplexRecipesData(recipesDataRepository),
            cacheComplexSearchData = CacheComplexSearchData(databaseRepository),
            deleteAllComplexSearchData = RemoveComplexSearchData(databaseRepository),
            queryCachedComplexRecipesData = QueryCachedComplexRecipesData(databaseRepository),
            getRecipeInformationById = GetFoodRecipeInformationById(recipesDataRepository)
        )
    }

    @Singleton
    @Provides
    fun provideNewsUnitUseCase(
        newsRepository: INewsDataRepository,
        databaseRepository: RelicDatabaseRepository
    ): NewsUseCase {
        return NewsUseCase(
            getTrendingNewsData = GetTrendingNewsData(newsRepository),
            getTopHeadlineNews = GetTopHeadlineNewsData(newsRepository),
            queryAllTrendingNewsData = QueryAllTrendingNewsData(databaseRepository),
            queryAllTopHeadlineNewsData = QueryAllTopHeadlineNewsData(databaseRepository),
            cacheTrendingNewsData = CacheTrendingNewsData(databaseRepository),
            cacheTopHeadlineNewsData = CacheTopHeadlineNewsData(databaseRepository)
        )
    }

    @Singleton
    @Provides
    fun provideWallpaperUnitUseCase(
        wallpaperRepository: IWallpaperDataRepository,
        databaseRepository: RelicDatabaseRepository
    ): WallpaperUseCase {
        return WallpaperUseCase(
            searchImages = SearchImages(wallpaperRepository)
        )
    }

    @Singleton
    @Provides
    fun provideMaximUnitUseCase(
        maximDataRepository: IMaximDataRepository,
        databaseRepository: RelicDatabaseRepository
    ): MaximUseCase {
        return MaximUseCase(
            getRandomMaxim = GetRandomMaxim(maximDataRepository)
        )
    }
}