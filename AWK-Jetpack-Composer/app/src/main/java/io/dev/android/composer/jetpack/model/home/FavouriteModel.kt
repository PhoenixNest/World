package io.dev.android.composer.jetpack.model.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.dev.android.composer.jetpack.R

data class FavouriteModel(
    @DrawableRes val imageResId: Int,
    @StringRes val stringResId: Int
) {
    companion object {
        val testFavouriteList = listOf(
            FavouriteModel(R.drawable.fc1_short_mantras, R.string.fc1_short_mantras),
            FavouriteModel(R.drawable.fc2_nature_meditations, R.string.fc2_nature_meditations),
            FavouriteModel(R.drawable.fc3_stress_and_anxiety, R.string.fc3_stress_and_anxiety),
            FavouriteModel(R.drawable.fc4_self_massage, R.string.fc4_self_massage),
            FavouriteModel(R.drawable.fc5_overwhelmed, R.string.fc5_overwhelmed),
            FavouriteModel(R.drawable.fc6_nightly_wind_down, R.string.fc6_nightly_wind_down),
        )
    }
}
