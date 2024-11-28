package io.dev.relic.feature.pages.detail.food_recipe

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.pages.detail.food_recipe.ui.FoodRecipeDetailContent
import io.dev.relic.feature.pages.detail.food_recipe.vm.FoodRecipesDetailViewModel

@Composable
fun FoodRecipeDetailPageRoute(
    recipeId: Int,
    recipeTitle: String,
    onBackClick: () -> Unit,
    detailViewModel: FoodRecipesDetailViewModel = hiltViewModel()
) {

    val isLike by detailViewModel.isLikeRecipe(recipeId)
        .collectAsStateWithLifecycle(initialValue = false)

    val recipesDataState by detailViewModel.getInformationDataStateFlow()
        .collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        detailViewModel.getRecipeInformationById(recipeId)
    }

    FoodRecipeDetailPage(
        isLike = isLike,
        recipeTitle = recipeTitle,
        dataState = recipesDataState,
        onBackClick = onBackClick,
        onLikeClick = { detailViewModel.updateLikeStatus(recipeId, isLike) },
        onRetryClick = { detailViewModel.getRecipeInformationById(recipeId) }
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
