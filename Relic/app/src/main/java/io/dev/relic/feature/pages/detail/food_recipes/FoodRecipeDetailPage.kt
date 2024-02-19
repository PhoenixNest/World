package io.dev.relic.feature.pages.detail.food_recipes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.function.food_recipes.viewmodel.FoodRecipesViewModel
import io.dev.relic.feature.pages.detail.food_recipes.ui.FoodRecipeDetailContent

@Composable
fun FoodRecipeDetailPageRoute(
    recipeId: Int,
    foodRecipesViewModel: FoodRecipesViewModel,
    onBackClick: () -> Unit
) {

    val recipesDataState by foodRecipesViewModel.foodRecipeInformationDataStateFlow.collectAsStateWithLifecycle()

    FoodRecipeDetailPage(
        dataState = recipesDataState,
        onBackClick = onBackClick
    )
}

@Composable
private fun FoodRecipeDetailPage(
    dataState: FoodRecipesDataState,
    onBackClick: () -> Unit
) {
    FoodRecipeDetailContent(
        dataState = dataState,
        onBackClick = onBackClick
    )
}

@Composable
@Preview
private fun FoodRecipeDetailPagePreview() {

}
