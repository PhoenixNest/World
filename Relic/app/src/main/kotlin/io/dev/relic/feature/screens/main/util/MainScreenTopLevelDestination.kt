package io.dev.relic.feature.screens.main.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.dev.relic.R

enum class MainScreenTopLevelDestination(
    @StringRes val titleResId: Int,
    @StringRes val labelResId: Int = titleResId,
    @DrawableRes val selectedIconResId: Int,
    @DrawableRes val unselectedIconResId: Int
) {
    HOME(
        titleResId = R.string.home_label,
        labelResId = R.string.home_label,
        unselectedIconResId = R.drawable.ic_bottom_tab_home_unselected,
        selectedIconResId = R.drawable.ic_bottom_tab_home_selected,
    ),
    STUDIO(
        titleResId = R.string.studio_label,
        labelResId = R.string.studio_label,
        selectedIconResId = R.drawable.ic_bottom_tab_studio_selected,
        unselectedIconResId = R.drawable.ic_bottom_tab_studio_unselected,
    )
}