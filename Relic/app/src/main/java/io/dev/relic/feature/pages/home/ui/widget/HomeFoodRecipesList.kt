package io.dev.relic.feature.pages.home.ui.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.core.ui.CommonLoadingPlaceholder
import io.core.ui.CommonRetryComponent
import io.data.model.food_recipes.FoodRecipesComplexSearchInfoModel
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.function.food_recipes.ui.FoodRecipesCardItem

@Suppress("FunctionName")
fun LazyListScope.HomeFoodRecipesList(
    dataState: FoodRecipesDataState,
    onCardClick: (recipesData: FoodRecipesComplexSearchInfoModel) -> Unit,
    onRetryClick: () -> Unit,
) {
    when (dataState) {
        is FoodRecipesDataState.Init,
        is FoodRecipesDataState.Fetching -> {
            item {
                CommonLoadingPlaceholder()
            }
        }

        is FoodRecipesDataState.Empty,
        is FoodRecipesDataState.NoFoodRecipesData,
        is FoodRecipesDataState.FetchFailed -> {
            item {
                CommonRetryComponent(
                    onRetryClick = onRetryClick,
                    modifier = Modifier.padding(12.dp),
                    containerHeight = 300.dp
                )
            }
        }

        is FoodRecipesDataState.FetchSucceed -> {
            itemsIndexed(dataState.modelList) { index, data ->
                if (data == null) {
                    //
                } else {
                    val itemDecorationModifier = Modifier.padding(
                        top = if (index == 0) 0.dp else 8.dp,
                        bottom = if (index == dataState.modelList.size - 1) 56.dp else 0.dp
                    )
                    FoodRecipesCardItem(
                        data = data,
                        modifier = itemDecorationModifier,
                        onCardClick = onCardClick
                    )
                }
            }
        }
    }
}