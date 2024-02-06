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
        selectedIconResId = R.drawable.ic_bottom_tab_home_selected,
        unselectedIconResId = R.drawable.ic_bottom_tab_home_unselected,
    ),
    EXPLORE(
        titleResId = R.string.explore_label,
        labelResId = R.string.explore_label,
        selectedIconResId = R.drawable.ic_bottom_tab_explore_selected,
        unselectedIconResId = R.drawable.ic_bottom_tab_explore_unselected,
    ),
    HIVE(
        titleResId = R.string.hive_label,
        labelResId = R.string.hive_label,
        selectedIconResId = R.drawable.ic_bottom_tab_hive_selected,
        unselectedIconResId = R.drawable.ic_bottom_tab_hive_unselected,
    )
}