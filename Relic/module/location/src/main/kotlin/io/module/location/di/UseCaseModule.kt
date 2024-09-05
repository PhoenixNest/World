package io.module.location.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.module.location.ILocationTracker
import io.module.location.use_case.LocationUseCase
import io.module.location.use_case.action.GetCurrentLocation
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideLocationUnitUseCase(
        locationTracker: ILocationTracker
    ): LocationUseCase {
        return LocationUseCase(
            getCurrentLocation = GetCurrentLocation(
                locationTracker
            )
        )
    }
}