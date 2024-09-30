package io.module.ad.core

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

object AdSharePreference {

    private const val TAG = "AdSharePreference"

    private const val SHARE_PREFERENCE_AD = "share_preference_ad"

    @Suppress("UNCHECKED_CAST")
    fun <T> readData(application: Application, key: String, default: T): T {
        val sharePreferenceManager = getSharePreferenceManager(application) ?: return false as T
        return when (default) {
            is Int -> readSyncIntData(sharePreferenceManager, key, default)
            is Long -> readSyncLongData(sharePreferenceManager, key, default)
            is Float -> readSyncFloatData(sharePreferenceManager, key, default)
            is Boolean -> readSyncBooleanData(sharePreferenceManager, key, default)
            is String -> readSyncStringData(sharePreferenceManager, key, default)
            else -> throw IllegalArgumentException("Unknown value type.")
        } as T
    }

    fun <T> writeData(application: Application, key: String, value: T): Boolean {
        val sharePreferenceManager = getSharePreferenceManager(application) ?: return false
        val editor = sharePreferenceManager.edit() ?: return false

        return when (value) {
            is Int -> {
                val intEditor = writeSyncIntData(editor, key, value) ?: return false
                intEditor.apply()
                true
            }

            is Long -> {
                val longEditor = writeSyncLongData(editor, key, value) ?: return false
                longEditor.apply()
                true
            }

            is Float -> {
                val floatEditor = writeSyncFloatData(editor, key, value) ?: return false
                floatEditor.apply()
                true
            }

            is Boolean -> {
                val booleanEditor = writeSyncBooleanData(editor, key, value) ?: return false
                booleanEditor.apply()
                true
            }

            is String -> {
                val stringEditor = writeSyncStringData(editor, key, value) ?: return false
                stringEditor.apply()
                true
            }

            else -> throw IllegalArgumentException("Unknown value type.")
        }
    }

    fun clearSharePreferenceData(application: Application): Boolean {
        val sharePreferenceManager = getSharePreferenceManager(application) ?: return false
        val editor = sharePreferenceManager.edit() ?: return false
        editor.clear().apply()
        return true
    }

    /**
     * Get the data manager of share preference.
     *
     * @param application   Current instance of app's application.
     * */
    private fun getSharePreferenceManager(application: Application): SharedPreferences? {
        return application.getSharedPreferences(SHARE_PREFERENCE_AD, Context.MODE_PRIVATE)
    }

    /* ======================== Read ======================== */

    private fun readSyncIntData(sharedPreferences: SharedPreferences, key: String, default: Int = 0): Int {
        return sharedPreferences.getInt(key, default)
    }

    private fun readSyncLongData(sharedPreferences: SharedPreferences, key: String, default: Long = 0L): Long {
        return sharedPreferences.getLong(key, default)
    }

    private fun readSyncFloatData(sharedPreferences: SharedPreferences, key: String, default: Float = 0F): Float {
        return sharedPreferences.getFloat(key, default)
    }

    private fun readSyncBooleanData(
        sharedPreferences: SharedPreferences,
        key: String,
        default: Boolean = false
    ): Boolean {
        return sharedPreferences.getBoolean(key, default)
    }

    private fun readSyncStringData(sharedPreferences: SharedPreferences, key: String, default: String = ""): String {
        return sharedPreferences.getString(key, default) ?: default
    }

    /* ======================== Write ======================== */

    private fun writeSyncIntData(editor: Editor, key: String, value: Int): Editor? {
        return editor.putInt(key, value)
    }

    private fun writeSyncLongData(editor: Editor, key: String, value: Long): Editor? {
        return editor.putLong(key, value)
    }

    private fun writeSyncFloatData(editor: Editor, key: String, value: Float): Editor? {
        return editor.putFloat(key, value)
    }

    private fun writeSyncBooleanData(editor: Editor, key: String, value: Boolean): Editor? {
        return editor.putBoolean(key, value)
    }

    private fun writeSyncStringData(editor: Editor, key: String, value: String): Editor? {
        return editor.putString(key, value)
    }
}