package io.dev.relic.feature.pages.detail.food_recipe.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.common.RelicConstants.Common.UNKNOWN_VALUE_INT
import io.common.RelicConstants.Common.UNKNOWN_VALUE_STRING
import io.core.ui.RelicUiUtil.getCurrentScreenWidthDp
import io.core.ui.widget.CommonAsyncImage
import io.core.ui.widget.CommonLoadingComponent
import io.core.ui.widget.CommonNoDataComponent
import io.core.ui.widget.CommonRetryComponent
import io.core.ui.widget.CommonTopBar
import io.data.model.food_recipes.FoodRecipeInformationModel
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.pages.detail.food_recipe.ui.widget.FoodRecipeIngredientsRow
import io.dev.relic.feature.pages.detail.food_recipe.ui.widget.FoodRecipeLikeButton
import io.dev.relic.feature.pages.detail.food_recipe.ui.widget.FoodRecipeSummary
import io.dev.relic.feature.pages.detail.food_recipe.ui.widget.FoodRecipeTitleBar

@Composable
fun FoodRecipeDetailContent(
    isLike: Boolean,
    recipeTitle: String,
    dataState: FoodRecipesDataState,
    onBackClick: () -> Unit,
    onLikeClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            FoodRecipesDetailContent(
                dataState = dataState,
                onRetryClick = onRetryClick
            )
            CommonTopBar(
                onBackClick = onBackClick,
                hasTitle = false,
                title = recipeTitle,
                modifier = Modifier
                    .statusBarsPadding()
                    .align(Alignment.TopCenter),
                iconColor = MaterialTheme.colorScheme.onSurface,
                tailContent = {
                    FoodRecipeLikeButton(
                        isLike = isLike,
                        onLikeClick = onLikeClick
                    )
                }
            )
        }
    }
}

@Composable
private fun FoodRecipesDetailContent(
    dataState: FoodRecipesDataState,
    onRetryClick: () -> Unit
) {
    val widthDp = getCurrentScreenWidthDp()
    val heightDp = 320.dp

    when (dataState) {
        is FoodRecipesDataState.Init,
        is FoodRecipesDataState.Fetching -> {
            CommonLoadingComponent()
        }

        is FoodRecipesDataState.FetchFailed -> {
            CommonRetryComponent(onRetryClick = onRetryClick)
        }

        is FoodRecipesDataState.FetchSucceed<*> -> {
            val model = dataState.data
                    as? FoodRecipeInformationModel ?: return

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        state = rememberScrollState(),
                        enabled = true
                    ),
                verticalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.Top
                ),
                horizontalAlignment = Alignment.Start
            ) {
                CommonAsyncImage(
                    url = model.image,
                    imageWidth = widthDp,
                    imageHeight = heightDp,
                    imageShape = RoundedCornerShape(
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    ),
                    imageRadius = 16.dp
                )
                FoodRecipeTitleBar(
                    title = model.title ?: UNKNOWN_VALUE_STRING,
                    cookTime = model.readyInMinutes ?: UNKNOWN_VALUE_INT,
                    healthScore = model.healthScore ?: UNKNOWN_VALUE_INT,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                FoodRecipeSummary(
                    summary = model.summary ?: UNKNOWN_VALUE_STRING,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                FoodRecipeIngredientsRow(
                    ingredientList = model.extendedIngredients ?: emptyList(),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        is FoodRecipesDataState.Empty,
        is FoodRecipesDataState.NoFoodRecipesData -> {
            CommonNoDataComponent(isShowText = true)
        }
    }
}