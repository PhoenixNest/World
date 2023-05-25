package io.dev.relic.core.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.dev.relic.core.repository.TodoDataRepositoryImpl
import io.dev.relic.core.repository.WeatherDataRepositoryImpl
import io.dev.relic.domain.repository.ITodoDataRepository
import io.dev.relic.domain.repository.IWeatherDataRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindWeatherDataRepository(
        weatherDataRepositoryImpl: WeatherDataRepositoryImpl
    ): IWeatherDataRepository

    @Binds
    fun bindTodoDataRepository(
        todoDataRepositoryImpl: TodoDataRepositoryImpl
    ): ITodoDataRepository

}