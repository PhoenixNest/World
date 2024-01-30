package io.dev.relic.feature.pages.home.ui.widget

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import io.dev.relic.feature.function.food_recipes.ui.FoodRecipesTabBar

@Suppress("FunctionName")
fun LazyListScope.HomeFoodRecipesTabBar(
    currentSelectedTab: Int,
    lazyListState: LazyListState,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit
) {
    item {
        FoodRecipesTabBar(
            currentSelectedTab = currentSelectedTab,
            onTabItemClick = onTabItemClick,
            lazyListState = lazyListState
        )
    }
}