package io.dev.relic.core.device.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.dev.relic.core.device.location.LocationTrackerImpl
import io.dev.relic.domain.location.ILocationTracker
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface LocationModule {

    @Binds
    @Singleton
    fun bindsLocationTracker(
        locationTrackerImpl: LocationTrackerImpl
    ): ILocationTracker

}