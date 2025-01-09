package io.dev.relic.feature.pages.studio.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.dev.relic.R

enum class StudioFunctionType(
    @DrawableRes val iconResId: Int,
    @StringRes val labelResId: Int
) {
    MONITOR(
        iconResId = R.drawable.ic_monitor,
        labelResId = R.string.studio_function_type_label_monitor
    ),
    DEVELOP(
        iconResId = R.drawable.ic_develop,
        labelResId = R.string.studio_function_type_label_develop
    ),
    MEDIA(
        iconResId = R.drawable.ic_media,
        labelResId = R.string.studio_function_type_label_media
    ),
}