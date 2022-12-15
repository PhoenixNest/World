package io.dev.android.game.data.datastore.block_2048

import androidx.datastore.preferences.core.intPreferencesKey

object Block2048PreferenceKey {
    private const val HIGHEST_SCORE = "block_2048_highest_score"
    val highestScore = intPreferencesKey(HIGHEST_SCORE)
}