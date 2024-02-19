package io.dev.relic.feature.pages.home.ui.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.core.ui.CommonLoadingPlaceholder
import io.core.ui.CommonRetryComponent
import io.data.model.food_recipes.FoodRecipesComplexSearchModel
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.function.food_recipes.ui.widget.FoodRecipesColumnItem

@Suppress("FunctionName")
fun LazyListScope.HomeFoodRecipesList(
    dataState: FoodRecipesDataState,
    onItemClick: (recipesData: FoodRecipesComplexSearchModel) -> Unit,
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
            itemsIndexed(dataState.data) { index, data ->
                if (data == null) {
                    //
                } else {
                    val itemDecorationModifier = Modifier.padding(
                        top = if (index == 0) 0.dp else 8.dp,
                        bottom = if (index == dataState.data.size - 1) 56.dp else 0.dp
                    )
                    FoodRecipesColumnItem(
                        data = data,
                        onItemClick = onItemClick,
                        modifier = itemDecorationModifier
                    )
                }
            }
        }
    }
}