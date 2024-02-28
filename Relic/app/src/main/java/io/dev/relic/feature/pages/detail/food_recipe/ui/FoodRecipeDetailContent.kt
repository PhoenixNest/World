package io.dev.relic.feature.pages.detail.food_recipe.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.core.ui.CommonAsyncImage
import io.core.ui.CommonLoadingComponent
import io.core.ui.CommonNoDataComponent
import io.core.ui.CommonRetryComponent
import io.core.ui.CommonTopBar
import io.core.ui.theme.mainThemeColor
import io.core.ui.utils.RelicUiUtil.getCurrentScreenWidthDp
import io.data.model.food_recipes.FoodRecipeInformationModel
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.pages.detail.food_recipe.ui.widget.FoodRecipeDetailPanel
import io.dev.relic.feature.pages.detail.food_recipe.ui.widget.FoodRecipeLikeButton

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
        color = mainThemeColor
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            FoodRecipeDataDetailContent(
                dataState = dataState,
                onRetryClick = onRetryClick
            )
            CommonTopBar(
                onBackClick = onBackClick,
                hasTitle = false,
                title = recipeTitle,
                containerModifier = Modifier
                    .statusBarsPadding()
                    .align(Alignment.TopCenter),
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
private fun FoodRecipeDataDetailContent(
    dataState: FoodRecipesDataState,
    onRetryClick: () -> Unit
) {
    when (dataState) {
        is FoodRecipesDataState.Init,
        is FoodRecipesDataState.Fetching -> {
            CommonLoadingComponent()
        }

        is FoodRecipesDataState.FetchFailed -> {
            CommonRetryComponent(onRetryClick = onRetryClick)
        }

        is FoodRecipesDataState.FetchSucceed<*> -> {
            val data = dataState.data
            if (data == null) {
                CommonNoDataComponent(isShowText = true)
            } else {
                FoodRecipeDataDetailContent(model = data as FoodRecipeInformationModel)
            }
        }

        is FoodRecipesDataState.Empty,
        is FoodRecipesDataState.NoFoodRecipesData -> {
            CommonNoDataComponent(isShowText = true)
        }
    }
}

@Composable
private fun FoodRecipeDataDetailContent(model: FoodRecipeInformationModel) {
    val widthDp = getCurrentScreenWidthDp()
    val heightDp = 320.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = rememberScrollState(),
                enabled = true
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        CommonAsyncImage(
            url = model.image,
            imageWidth = widthDp,
            imageHeight = heightDp,
            imageShape = RoundedCornerShape(
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            )
        )
        FoodRecipeDetailPanel(model)
    }
}