package io.dev.relic.feature.pages.detail.food_recipe

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.function.food_recipes.viewmodel.FoodRecipesViewModel
import io.dev.relic.feature.pages.detail.food_recipe.ui.FoodRecipeDetailContent

@Composable
fun FoodRecipeDetailPageRoute(
    recipeId: Int,
    recipeTitle: String,
    foodRecipesViewModel: FoodRecipesViewModel,
    onBackClick: () -> Unit
) {

    val isLike by foodRecipesViewModel.isLikeRecipe(recipeId).collectAsStateWithLifecycle(initialValue = false)
    val recipesDataState by foodRecipesViewModel.informationDataStateFlow.collectAsStateWithLifecycle()

    FoodRecipeDetailPage(
        isLike = isLike,
        recipeTitle = recipeTitle,
        dataState = recipesDataState,
        onBackClick = onBackClick,
        onLikeClick = {
            foodRecipesViewModel.updateLikeStatus(recipeId, isLike)
        },
        onRetryClick = {
            foodRecipesViewModel.getRecipeInformationById(recipeId)
        }
    )
}

@Composable
private fun FoodRecipeDetailPage(
    isLike: Boolean,
    recipeTitle: String,
    dataState: FoodRecipesDataState,
    onBackClick: () -> Unit,
    onLikeClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    FoodRecipeDetailContent(
        isLike = isLike,
        recipeTitle = recipeTitle,
        dataState = dataState,
        onBackClick = onBackClick,
        onLikeClick = onLikeClick,
        onRetryClick = onRetryClick
    )
}

@Composable
@Preview
private fun FoodRecipeDetailPagePreview() {
    FoodRecipeDetailPage(
        isLike = false,
        recipeTitle = "Juice",
        dataState = FoodRecipesDataState.Init,
        onBackClick = {},
        onLikeClick = {},
        onRetryClick = {}
    )
}
