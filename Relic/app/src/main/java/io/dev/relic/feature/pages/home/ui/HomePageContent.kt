package io.dev.relic.feature.pages.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.core.ui.theme.mainThemeColor
import io.data.model.food_recipes.FoodRecipesComplexSearchInfoModel
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.pages.home.ui.widget.HomeFoodRecipesList
import io.dev.relic.feature.pages.home.ui.widget.HomeFoodRecipesTabBar
import io.dev.relic.feature.pages.home.ui.widget.HomeTopBar

@Composable
fun HomePageContent(
    foodRecipesState: FoodRecipesDataState,
    foodRecipesTabLazyListState: LazyListState,
    currentSelectedFoodRecipesTab: Int,
    onOpenDrawer: () -> Unit,
    onSelectedFoodRecipesTabItem: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    onFoodRecipesCardClick: (recipesData: FoodRecipesComplexSearchInfoModel) -> Unit,
    onFoodRecipesRetry: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mainThemeColor
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeTopBar(onOpenDrawer = onOpenDrawer)
            HomeFoodRecipesTabBar(
                currentSelectedTab = currentSelectedFoodRecipesTab,
                lazyListState = foodRecipesTabLazyListState,
                onTabItemClick = onSelectedFoodRecipesTabItem
            )
            item { Spacer(modifier = Modifier.height(16.dp)) }
            HomeFoodRecipesList(
                dataState = foodRecipesState,
                onRetryClick = onFoodRecipesRetry,
                onCardClick = onFoodRecipesCardClick
            )
        }
    }
}