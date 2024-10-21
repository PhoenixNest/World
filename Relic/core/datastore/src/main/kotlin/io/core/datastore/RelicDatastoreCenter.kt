package io.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.common.app.BaseApplication.Companion.getApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException

/**
 * Access the global datastore file with Context.
 * */
@ActivityRetainedScoped
val Context.dataStore by preferencesDataStore("relic_datastore")

object RelicDatastoreCenter {

    private val dataStore = getApplicationContext().dataStore

    /* ======================== Sync ======================== */

    @Suppress("UNCHECKED_CAST")
    fun <T> readSyncData(key: String, default: T): T {
        return when (default) {
            is Long -> readLongData(key, default)
            is String -> readStringData(key, default)
            is Int -> readIntData(key, default)
            is Boolean -> readBooleanData(key, default)
            is Float -> readFloatData(key, default)
            else -> throw IllegalArgumentException("This type can't be saved into DataStore")
        } as T
    }

    fun <T> writeSyncData(key: String, value: T) {
        when (value) {
            is Long -> saveSyncLongData(key, value)
            is String -> saveSyncStringData(key, value)
            is Int -> saveSyncIntData(key, value)
            is Boolean -> saveSyncBooleanData(key, value)
            is Float -> saveSyncFloatData(key, value)
            else -> throw IllegalArgumentException("This type can't be saved into DataStore")
        }
    }

    /* ======================== Async ======================== */

    @Suppress("UNCHECKED_CAST")
    fun <T> readAsyncData(key: String, default: T): Flow<T> {
        return when (default) {
            is Long -> readLongFlow(key, default)
            is String -> readStringFlow(key, default)
            is Int -> readIntFlow(key, default)
            is Boolean -> readBooleanFlow(key, default)
            is Float -> readFloatFlow(key, default)
            else -> throw IllegalArgumentException("This type can't be saved into DataStore")
        } as Flow<T>
    }

    suspend fun <T> writeAsyncData(key: String, value: T) {
        when (value) {
            is Long -> saveAsyncLongData(key, value)
            is String -> saveAsyncStringData(key, value)
            is Int -> saveAsyncIntData(key, value)
            is Boolean -> saveAsyncBooleanData(key, value)
            is Float -> saveAsyncFloatData(key, value)
            else -> throw IllegalArgumentException("This type can't be saved into DataStore")
        }
    }

    /* ======================== Clear ======================== */

    fun clearSyncData() {
        runBlocking {
            dataStore.edit {
                it.clear()
            }
        }
    }

    suspend fun clearAsyncData() {
        dataStore.edit {
            it.clear()
        }
    }

    /* ======================== Read ======================== */

    private fun readBooleanData(
        key: String,
        default: Boolean = false
    ): Boolean {
        var value = false
        runBlocking {
            dataStore.data.first {
                value = it[booleanPreferencesKey(key)] ?: default
                true
            }
        }
        return value
    }

    private fun readBooleanFlow(
        key: String,
        default: Boolean = false
    ): Flow<Boolean> {
        return dataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                it[booleanPreferencesKey(key)] ?: default
            }
    }

    private fun readIntData(
        key: String,
        default: Int = 0
    ): Int {
        var value = 0
        runBlocking {
            dataStore.data.first {
                value = it[intPreferencesKey(key)] ?: default
                true
            }
        }
        return value
    }

    private fun readIntFlow(
        key: String,
        default: Int = 0
    ): Flow<Int> {
        return dataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                it[intPreferencesKey(key)] ?: default
            }
    }

    private fun readStringData(
        key: String,
        default: String = ""
    ): String {
        var value = ""
        runBlocking {
            dataStore.data.first {
                value = it[stringPreferencesKey(key)] ?: default
                true
            }
        }
        return value
    }

    private fun readStringFlow(
        key: String,
        default: String = ""
    ): Flow<String> {
        return dataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                it[stringPreferencesKey(key)] ?: default
            }
    }

    private fun readFloatData(
        key: String,
        default: Float = 0f
    ): Float {
        var value = 0f
        runBlocking {
            dataStore.data.first {
                value = it[floatPreferencesKey(key)] ?: default
                true
            }
        }
        return value
    }

    private fun readFloatFlow(
        key: String,
        default: Float = 0f
    ): Flow<Float> {
        return dataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                it[floatPreferencesKey(key)] ?: default
            }
    }

    private fun readLongData(
        key: String,
        default: Long = 0L
    ): Long {
        var value = 0L
        runBlocking {
            dataStore.data.first {
                value = it[longPreferencesKey(key)] ?: default
                true
            }
        }
        return value
    }

    private fun readLongFlow(
        key: String,
        default: Long = 0L
    ): Flow<Long> {
        return dataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                it[longPreferencesKey(key)] ?: default
            }
    }

    /* ======================== Write ======================== */

    private fun saveSyncBooleanData(
        key: String,
        value: Boolean
    ) {
        return runBlocking {
            saveAsyncBooleanData(key, value)
        }
    }

    private suspend fun saveAsyncBooleanData(
        key: String,
        value: Boolean
    ) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    private fun saveSyncIntData(
        key: String,
        value: Int
    ) {
        return runBlocking {
            saveAsyncIntData(key, value)
        }
    }

    private suspend fun saveAsyncIntData(
        key: String,
        value: Int
    ) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(key)] = value
        }
    }

    private fun saveSyncStringData(
        key: String,
        value: String
    ) {
        return runBlocking {
            saveAsyncStringData(key, value)
        }
    }

    private suspend fun saveAsyncStringData(
        key: String,
        value: String
    ) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    private fun saveSyncFloatData(
        key: String,
        value: Float
    ) {
        return runBlocking {
            saveAsyncFloatData(key, value)
        }
    }

    private suspend fun saveAsyncFloatData(
        key: String,
        value: Float
    ) {
        dataStore.edit { preferences ->
            preferences[floatPreferencesKey(key)] = value
        }
    }

    private fun saveSyncLongData(
        key: String,
        value: Long
    ) {
        return runBlocking {
            saveAsyncLongData(key, value)
        }
    }

    private suspend fun saveAsyncLongData(
        key: String,
        value: Long
    ) {
        dataStore.edit { preferences ->
            preferences[longPreferencesKey(key)] = value
        }
    }
}