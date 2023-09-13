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
    Home(
        titleResId = R.string.home_title,
        labelResId = R.string.home_title,
        selectedIconResId = R.drawable.ic_bottom_tab_home_selected,
        unselectedIconResId = R.drawable.ic_bottom_tab_home_unselected,
    ),
    Explore(
        titleResId = R.string.explore_title,
        labelResId = R.string.explore_title,
        selectedIconResId = R.drawable.ic_bottom_tab_explore_selected,
        unselectedIconResId = R.drawable.ic_bottom_tab_explore_unselected,
    ),
    Hive(
        titleResId = R.string.hive_title,
        labelResId = R.string.hive_title,
        selectedIconResId = R.drawable.ic_bottom_tab_hive_selected,
        unselectedIconResId = R.drawable.ic_bottom_tab_hive_unselected,
    )
}