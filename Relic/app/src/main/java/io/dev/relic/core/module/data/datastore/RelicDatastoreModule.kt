package io.dev.relic.core.module.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.dev.relic.global.util.ext.ContextExt.dataStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RelicDatastoreModule {

    @Provides
    @Singleton
    fun provideDatastore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.dataStore
    }

}