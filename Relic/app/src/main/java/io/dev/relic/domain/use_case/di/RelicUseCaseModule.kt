package io.dev.relic.domain.use_case.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.dev.relic.domain.repository.IFoodRecipesDataRepository
import io.dev.relic.domain.repository.ITodoDataRepository
import io.dev.relic.domain.repository.IWeatherDataRepository
import io.dev.relic.domain.use_case.food_receipes.FoodRecipesUseCase
import io.dev.relic.domain.use_case.food_receipes.action.complex_search.CacheComplexSearchData
import io.dev.relic.domain.use_case.food_receipes.action.complex_search.FetchComplexRecipesData
import io.dev.relic.domain.use_case.food_receipes.action.complex_search.ReadCacheComplexRecipesData
import io.dev.relic.domain.use_case.lcoation.LocationUseCase
import io.dev.relic.domain.use_case.lcoation.action.AccessCurrentLocation
import io.dev.relic.domain.use_case.todo.TodoUseCase
import io.dev.relic.domain.use_case.todo.action.AddTodo
import io.dev.relic.domain.use_case.todo.action.DeleteTodo
import io.dev.relic.domain.use_case.todo.action.GetAllTodos
import io.dev.relic.domain.use_case.todo.action.UpdateTodo
import io.dev.relic.domain.use_case.weather.WeatherUseCase
import io.dev.relic.domain.use_case.weather.action.CacheWeatherData
import io.dev.relic.domain.use_case.weather.action.FetchWeatherData
import io.dev.relic.domain.use_case.weather.action.ReadCacheWeatherData
import io.module.core.database.repository.RelicDatabaseRepository
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