package io.dev.relic.feature.pages.explore.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.dev.relic.R

enum class ExploreBottomTabs(
    @DrawableRes val iconResId: Int,
    @StringRes val tabLabelResId: Int
) {
    FOODS(R.drawable.ic_foods, R.string.explore_bottom_sheet_tab_foods_title),
    DRINKS(R.drawable.ic_drinks, R.string.explore_bottom_sheet_tab_drinks_title),
    VIEWS(R.drawable.ic_views, R.string.explore_bottom_sheet_tab_views_title),
    HOTEL(R.drawable.ic_hotel, R.string.explore_bottom_sheet_tab_hotel_title),
    SHOPPING(R.drawable.ic_shopping, R.string.explore_bottom_sheet_tab_shopping_title),
    SPORT(R.drawable.ic_sports_center, R.string.explore_bottom_sheet_tab_sport_title),
    HEALTH(R.drawable.ic_health_center, R.string.explore_bottom_sheet_tab_health_title),
    SERVICE(R.drawable.ic_traffic_service, R.string.explore_bottom_sheet_tab_traffic_service_title)
}