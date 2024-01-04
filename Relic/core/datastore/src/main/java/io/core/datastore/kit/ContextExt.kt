package io.core.datastore.kit

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.scopes.ActivityRetainedScoped

object ContextExt {

    /**
     * Access the global datastore file with Context.
     * */
    @ActivityRetainedScoped
    val Context.dataStore by preferencesDataStore("relic_datastore")

}