package io.dev.android.game.data.datastore.one_line_finish

import androidx.datastore.preferences.core.intPreferencesKey

object OneLineFinishPreferenceKey {
    private const val CURRENT_LEVEL = "one_line_finish_current_level"
    val currentLevel = intPreferencesKey(CURRENT_LEVEL)
}