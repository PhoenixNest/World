package io.dev.relic.feature.function.food_recipes.ui

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.common.util.TimeUtil
import io.data.model.food_recipes.FoodRecipesComplexSearchModel
import io.dev.relic.R
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesAutoConvertor

@Composable
fun FoodRecipesAutoTimeComponent(
    currentTimeSection: TimeUtil.TimeSection,
    listState: LazyListState,
    dataState: FoodRecipesDataState,
    onSeeMoreClick: (dishType: String) -> Unit,
    onItemClick: (recipesData: FoodRecipesComplexSearchModel) -> Unit,
    onRetryClick: () -> Unit
) {
    val dishType = FoodRecipesAutoConvertor.convertTimeSectionToDishType(currentTimeSection)
    val dishLabel = stringResource(id = dishType.labelResId)

    FoodRecipesLazyRow(
        title = stringResource(id = R.string.food_recipes_time_section_title, dishLabel),
        listState = listState,
        dataState = dataState,
        onSeeMoreClick = { onSeeMoreClick.invoke(dishLabel) },
        onItemClick = onItemClick,
        onRetryClick = onRetryClick
    )
}