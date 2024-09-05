package io.dev.relic.feature.pages.home.ui.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.dev.relic.feature.function.food_recipes.ui.FoodRecipesTabBar

@OptIn(ExperimentalFoundationApi::class)
@Suppress("FunctionName")
fun LazyListScope.HomeFoodRecipesTabBar(
    currentSelectedTab: Int,
    lazyListState: LazyListState,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit
) {
    stickyHeader {
        HomeFoodRecipesTabBarContent(
            currentSelectedTab = currentSelectedTab,
            onTabItemClick = onTabItemClick,
            lazyListState = lazyListState
        )
    }
}

@Composable
private fun HomeFoodRecipesTabBarContent(
    currentSelectedTab: Int,
    lazyListState: LazyListState,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit
) {
    FoodRecipesTabBar(
        currentSelectedTab = currentSelectedTab,
        onTabItemClick = onTabItemClick,
        lazyListState = lazyListState
    )
}


@Composable
@Preview
private fun HomeFoodRecipesTabBarContentPreview() {
    HomeFoodRecipesTabBarContent(
        currentSelectedTab = 0,
        onTabItemClick = { _, _ -> },
        lazyListState = rememberLazyListState()
    )
}