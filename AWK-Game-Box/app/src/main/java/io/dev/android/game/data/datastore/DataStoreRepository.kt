package io.dev.android.game.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.dev.android.game.data.datastore.block_2048.Block2048PreferenceKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val DATASTORE_FILE_NAME = "awk-game-box-ds"

@ActivityRetainedScoped
val Context.dataScore by preferencesDataStore(DATASTORE_FILE_NAME)

class DataStoreRepository @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore: DataStore<Preferences> = context.dataScore

    /* ======================== Block 2048 ======================== */

    suspend fun saveHighestScore(score: Int) {
        dataStore.edit { preference ->
            preference[Block2048PreferenceKey.highestScore] = score
        }
    }

    fun readHighestScore(): Flow<Int> {
        return dataStore.data.map { preference ->
            preference[Block2048PreferenceKey.highestScore] ?: 0
        }
    }
}