package io.dev.android.composer.jetpack.model.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.dev.android.composer.jetpack.R

data class TrendingModel(
    @DrawableRes val imageResId: Int,
    @StringRes val stringResId: Int
) {
    companion object {
        val testTrendingList = listOf(
            TrendingModel(R.drawable.ab1_inversions, R.string.ab1_inversions),
            TrendingModel(R.drawable.ab2_quick_yoga, R.string.ab2_quick_yoga),
            TrendingModel(R.drawable.ab3_stretching, R.string.ab3_stretching),
            TrendingModel(R.drawable.ab4_tabata, R.string.ab4_tabata),
            TrendingModel(R.drawable.ab5_hiit, R.string.ab5_hiit),
            TrendingModel(R.drawable.ab6_pre_natal_yoga, R.string.ab6_pre_natal_yoga),
        )
    }
}
