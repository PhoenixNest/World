package io.dev.relic.feature.pages.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState

@Composable
fun rememberHomeFoodRecipesState(
    timeSectionDataState: FoodRecipesDataState,
    mainDataState: FoodRecipesDataState,
    listState: HomeFoodRecipesListState,
): HomeFoodRecipesState {
    return remember(keys = arrayOf(listState)) {
        HomeFoodRecipesState(
            timeSectionDataState = timeSectionDataState,
            mainDataState = mainDataState,
            listState = listState
        )
    }
}

@Composable
fun rememberHomeFoodRecipesListState(
    timeSectionListState: LazyListState = rememberLazyListState(),
    tabListState: LazyListState = rememberLazyListState(),
    mainListState: LazyListState = rememberLazyListState()
): HomeFoodRecipesListState {
    return remember(
        keys = arrayOf(
            timeSectionListState,
            tabListState,
            mainListState
        )
    ) {
        HomeFoodRecipesListState(
            timeSectionListState = timeSectionListState,
            tabListState = tabListState,
            mainListState = mainListState
        )
    }
}

/**
 * Data state for home page.
 *
 * @param timeSectionDataState      For time-section list (horizontal).
 * @param mainDataState             For food Recipes main list (vertical).
 * @param listState                 Ui state of list.
 *
 * @see HomeFoodRecipesListState
 * */
data class HomeFoodRecipesState(
    val timeSectionDataState: FoodRecipesDataState,
    val mainDataState: FoodRecipesDataState,
    val listState: HomeFoodRecipesListState
)

/**
 * According to the page ui, the order of list state will be:
 *
 * @param timeSectionListState      For time-section list (horizontal).
 * @param tabListState              For food Recipes tab panel.
 * @param mainListState             For food Recipes main list (vertical).
 * */
data class HomeFoodRecipesListState(
    val timeSectionListState: LazyListState,
    val tabListState: LazyListState,
    val mainListState: LazyListState
)