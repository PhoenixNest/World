package io.module.core.datastore.kit

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.scopes.ActivityRetainedScoped

object ContextExt {

    /**
     * Access the global datastore file with Context.
     * */
    @ActivityRetainedScoped
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore("relic_datastore")

}