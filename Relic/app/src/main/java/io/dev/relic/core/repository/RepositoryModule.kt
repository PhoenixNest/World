package io.dev.relic.core.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.dev.relic.domain.repository.IWeatherDataRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherDataRepository(
        weatherDataRepositoryImpl: WeatherDataRepositoryImpl
    ): IWeatherDataRepository

}