package io.dev.relic.feature.pages.home.ui.widget

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import io.common.util.TimeUtil
import io.data.model.food_recipes.FoodRecipesComplexSearchModel
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.function.food_recipes.ui.FoodRecipesAutoTimeComponent

@Suppress("FunctionName")
fun LazyListScope.HomeFoodRecipesAutoTimeComponent(
    currentTimeSection: TimeUtil.TimeSection,
    listState: LazyListState,
    dataState: FoodRecipesDataState,
    onSeeMoreClick: (dishType: String) -> Unit,
    onItemClick: (recipesData: FoodRecipesComplexSearchModel) -> Unit,
    onRetryClick: () -> Unit
) {
    item {
        FoodRecipesAutoTimeComponent(
            currentTimeSection = currentTimeSection,
            listState = listState,
            dataState = dataState,
            onSeeMoreClick = onSeeMoreClick,
            onItemClick = onItemClick,
            onRetryClick = onRetryClick
        )
    }
}