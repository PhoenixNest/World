package io.module.location.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.module.location.ILocationTracker
import io.module.location.impl.LocationTrackerImpl
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