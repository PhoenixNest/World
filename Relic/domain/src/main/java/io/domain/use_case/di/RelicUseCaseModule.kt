package io.domain.use_case.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.domain.repository.IFoodRecipesDataRepository
import io.domain.repository.ITodoDataRepository
import io.domain.repository.IWeatherDataRepository
import io.domain.use_case.food_receipes.FoodRecipesUseCase
import io.domain.use_case.food_receipes.action.complex_search.CacheComplexSearchData
import io.domain.use_case.food_receipes.action.complex_search.FetchComplexRecipesData
import io.domain.use_case.food_receipes.action.complex_search.ReadCacheComplexRecipesData
import io.domain.use_case.lcoation.LocationUseCase
import io.domain.use_case.lcoation.action.AccessCurrentLocation
import io.domain.use_case.todo.TodoUseCase
import io.domain.use_case.todo.action.AddTodo
import io.domain.use_case.todo.action.DeleteTodo
import io.domain.use_case.todo.action.GetAllTodos
import io.domain.use_case.todo.action.UpdateTodo
import io.domain.use_case.weather.WeatherUseCase
import io.domain.use_case.weather.action.CacheWeatherData
import io.domain.use_case.weather.action.FetchWeatherData
import io.domain.use_case.weather.action.ReadCacheWeatherData
import io.core.database.repository.RelicDatabaseRepository
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
            addTodo = AddTodo(todoRepository = todoRepository),
            deleteTodo = DeleteTodo(todoRepository = todoRepository),
            getAllTodos = GetAllTodos(todoRepository = todoRepository),
            updateTodo = UpdateTodo(todoRepository = todoRepository)
        )
    }

    @Singleton
    @Provides
    fun provideLocationUnitUseCase(
        locationTracker: ILocationTracker
    ): LocationUseCase {
        return LocationUseCase(
            accessCurrentLocation = AccessCurrentLocation(locationTracker = locationTracker)
        )
    }

    @Singleton
    @Provides
    fun provideWeatherUnitUseCase(
        weatherDataRepository: IWeatherDataRepository,
        databaseRepository: RelicDatabaseRepository
    ): WeatherUseCase {
        return WeatherUseCase(
            fetchWeatherData = FetchWeatherData(weatherDataRepository = weatherDataRepository),
            cacheWeatherData = CacheWeatherData(databaseRepository = databaseRepository),
            readCacheWeatherData = ReadCacheWeatherData(databaseRepository = databaseRepository)
        )
    }

    @Singleton
    @Provides
    fun provideFoodRecipesUnitUseCase(
        recipesDataRepository: IFoodRecipesDataRepository,
        databaseRepository: RelicDatabaseRepository
    ): FoodRecipesUseCase {
        return FoodRecipesUseCase(
            fetchComplexRecipesData = FetchComplexRecipesData(foodRecipesDataRepository = recipesDataRepository),
            cacheComplexSearchData = CacheComplexSearchData(databaseRepository = databaseRepository),
            readCacheComplexRecipesData = ReadCacheComplexRecipesData(databaseRepository = databaseRepository)
        )
    }

}