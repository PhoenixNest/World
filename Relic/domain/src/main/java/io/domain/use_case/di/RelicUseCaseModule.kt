package io.domain.use_case.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.core.database.repository.RelicDatabaseRepository
import io.domain.repository.IFoodRecipesDataRepository
import io.domain.repository.INewsDataRepository
import io.domain.repository.ITodoDataRepository
import io.domain.repository.IWeatherDataRepository
import io.domain.use_case.food_receipes.FoodRecipesUseCase
import io.domain.use_case.food_receipes.action.complex_search.CacheComplexSearchData
import io.domain.use_case.food_receipes.action.complex_search.FetchComplexRecipesData
import io.domain.use_case.food_receipes.action.complex_search.ReadCacheComplexRecipesData
import io.domain.use_case.lcoation.LocationUseCase
import io.domain.use_case.lcoation.action.AccessCurrentLocation
import io.domain.use_case.news.NewsUseCase
import io.domain.use_case.news.action.FetchEverythingNews
import io.domain.use_case.news.action.FetchHeadlineNews
import io.domain.use_case.todo.TodoUseCase
import io.domain.use_case.todo.action.AddTodo
import io.domain.use_case.todo.action.DeleteTodo
import io.domain.use_case.todo.action.GetAllTodos
import io.domain.use_case.todo.action.UpdateTodo
import io.domain.use_case.weather.WeatherUseCase
import io.domain.use_case.weather.action.CacheWeatherData
import io.domain.use_case.weather.action.FetchWeatherData
import io.domain.use_case.weather.action.ReadCacheWeatherData
import io.module.map.ILocationTracker
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
    fun provideLocationUnitUseCase(
        locationTracker: ILocationTracker
    ): LocationUseCase {
        return LocationUseCase(
            accessCurrentLocation = AccessCurrentLocation(locationTracker)
        )
    }

    @Singleton
    @Provides
    fun provideWeatherUnitUseCase(
        weatherDataRepository: IWeatherDataRepository,
        databaseRepository: RelicDatabaseRepository
    ): WeatherUseCase {
        return WeatherUseCase(
            fetchWeatherData = FetchWeatherData(weatherDataRepository),
            cacheWeatherData = CacheWeatherData(databaseRepository),
            readCacheWeatherData = ReadCacheWeatherData(databaseRepository)
        )
    }

    @Singleton
    @Provides
    fun provideFoodRecipesUnitUseCase(
        recipesDataRepository: IFoodRecipesDataRepository,
        databaseRepository: RelicDatabaseRepository
    ): FoodRecipesUseCase {
        return FoodRecipesUseCase(
            fetchComplexRecipesData = FetchComplexRecipesData(recipesDataRepository),
            cacheComplexSearchData = CacheComplexSearchData(databaseRepository),
            readCacheComplexRecipesData = ReadCacheComplexRecipesData(databaseRepository)
        )
    }

    @Singleton
    @Provides
    fun provideNewsUnitUseCase(
        newsRepository: INewsDataRepository
    ): NewsUseCase {
        return NewsUseCase(
            fetchEverythingNews = FetchEverythingNews(newsRepository),
            fetchTopHeadlineNews = FetchHeadlineNews(newsRepository)
        )
    }

}