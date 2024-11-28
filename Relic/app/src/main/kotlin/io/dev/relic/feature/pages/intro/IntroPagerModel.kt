package io.dev.relic.feature.pages.intro

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.dev.relic.R

data class IntroPagerModel(
    @DrawableRes val pagerDrawableResId: Int,
    @StringRes val pagerTitleStrResId: Int,
    @StringRes val pagerDescStrResId: Int
) {
    companion object {
        fun defaultPagerList(): List<IntroPagerModel> {
            return listOf(
                IntroPagerModel(R.mipmap.day, -1, -1),
                IntroPagerModel(R.mipmap.night, -1, -1),
                IntroPagerModel(R.mipmap.midnight, -1, -1)
            )
        }
    }
}
