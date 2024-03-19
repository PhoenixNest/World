package io.dev.relic.feature.pages.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState

@Composable
fun rememberHomeFoodRecipesListState(
    timeSectionListState: LazyListState = rememberLazyListState(),
    trendingTabListState: LazyListState = rememberLazyListState(),
    trendingListState: LazyListState = rememberLazyListState()
): HomeFoodRecipesListState {
    return remember(
        keys = arrayOf(
            timeSectionListState,
            trendingTabListState,
            trendingListState
        )
    ) {
        HomeFoodRecipesListState(
            timeSectionListState = timeSectionListState,
            tabListState = trendingTabListState,
            recommendListState = trendingListState
        )
    }
}

/**
 * Data state for home page.
 *
 * @param timeSectionDataState      For time-section list (horizontal).
 * @param recommendDataState             For food Recipes main list (vertical).
 * @param listState                 Ui state of list.
 *
 * @see HomeFoodRecipesListState
 * */
data class HomeFoodRecipesState(
    val currentSelectTab: Int,
    val timeSectionDataState: FoodRecipesDataState,
    val recommendDataState: FoodRecipesDataState,
    val listState: HomeFoodRecipesListState
)

/**
 * According to the page ui, the order of list state will be:
 *
 * @param timeSectionListState      For time-section list (horizontal).
 * @param tabListState              For food Recipes tab panel.
 * @param recommendListState             For food Recipes main list (vertical).
 * */
data class HomeFoodRecipesListState(
    val timeSectionListState: LazyListState,
    val tabListState: LazyListState,
    val recommendListState: LazyListState
)