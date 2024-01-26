package io.domain.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.domain.repository.IFoodRecipesDataRepository
import io.domain.repository.INewsDataRepository
import io.domain.repository.ITodoDataRepository
import io.domain.repository.IWallpaperDataRepository
import io.domain.repository.IWeatherDataRepository
import io.domain.repository.impl.FoodRecipesDataRepositoryImpl
import io.domain.repository.impl.NewsDataRepositoryImpl
import io.domain.repository.impl.TodoDataRepositoryImpl
import io.domain.repository.impl.WallpaperDataRepositoryImpl
import io.domain.repository.impl.WeatherDataRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindWeatherDataRepository(
        weatherDataRepositoryImpl: WeatherDataRepositoryImpl
    ): IWeatherDataRepository

    @Binds
    fun bindFoodRecipesRepository(
        foodRecipesDataRepositoryImpl: FoodRecipesDataRepositoryImpl
    ): IFoodRecipesDataRepository

    @Binds
    fun bindTodoDataRepository(
        todoDataRepositoryImpl: TodoDataRepositoryImpl
    ): ITodoDataRepository

    @Binds
    fun bindNewsDataRepository(
        newsDataRepositoryImpl: NewsDataRepositoryImpl
    ): INewsDataRepository

    @Binds
    fun bindWallpaperRepository(
        wallpaperRepository: WallpaperDataRepositoryImpl
    ): IWallpaperDataRepository

}