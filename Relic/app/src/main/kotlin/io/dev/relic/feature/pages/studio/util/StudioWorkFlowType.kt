package io.dev.relic.feature.pages.studio.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.dev.relic.R

enum class StudioWorkFlowType(
    @StringRes val labelResId: Int,
    @DrawableRes val backgroundResId: Int
) {
    GALLERY(
        labelResId = R.string.gallery_label,
        backgroundResId = R.mipmap.day
    ),
    DEVELOP(
        labelResId = R.string.studio_label,
        backgroundResId = R.mipmap.night
    ),
    MUSIC(
        labelResId = R.string.mine_title,
        backgroundResId = R.mipmap.midnight
    )
}