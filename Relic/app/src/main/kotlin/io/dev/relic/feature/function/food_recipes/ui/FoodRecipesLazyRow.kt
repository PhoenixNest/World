package io.dev.relic.feature.function.food_recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.CommonLoadingPlaceholder
import io.core.ui.CommonRetryComponent
import io.data.model.food_recipes.FoodRecipesComplexSearchModel
import io.dev.relic.R
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.function.food_recipes.ui.widget.FoodRecipesRowItem

@Composable
fun FoodRecipesLazyRow(
    title: String,
    listState: LazyListState,
    dataState: FoodRecipesDataState,
    onSeeMoreClick: () -> Unit,
    onItemClick: (recipesData: FoodRecipesComplexSearchModel) -> Unit,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        FoodRecipesComponentTitle(
            dishLabel = title,
            onSeeMoreClick = onSeeMoreClick
        )
        Spacer(modifier = Modifier.height(16.dp))
        FoodRecipesLazyRowContent(
            listState = listState,
            dataState = dataState,
            onItemClick = onItemClick,
            onRetryClick = onRetryClick
        )
    }
}

@Composable
private fun FoodRecipesComponentTitle(
    dishLabel: String,
    onSeeMoreClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = dishLabel,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun FoodRecipesLazyRowContent(
    listState: LazyListState,
    dataState: FoodRecipesDataState,
    onItemClick: (recipesData: FoodRecipesComplexSearchModel) -> Unit,
    onRetryClick: () -> Unit
) {
    when (dataState) {
        is FoodRecipesDataState.Init,
        is FoodRecipesDataState.Fetching -> {
            CommonLoadingPlaceholder(
                isVertical = false,
                backgroundColor = MaterialTheme.colorScheme.onPrimary
            )
        }

        is FoodRecipesDataState.Empty,
        is FoodRecipesDataState.NoFoodRecipesData,
        is FoodRecipesDataState.FetchFailed -> {
            CommonRetryComponent(
                onRetryClick = onRetryClick,
                modifier = Modifier.padding(12.dp),
                containerHeight = 300.dp,
                backgroundColor = MaterialTheme.colorScheme.onPrimary
            )
        }

        is FoodRecipesDataState.FetchSucceed<*> -> {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.Start
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(dataState.data as List<*>) { index, data ->
                    if (data == null) {
                        //
                    } else {
                        val itemDecorationModifier: Modifier = Modifier.padding(
                            start = if (index == 0) 16.dp else 0.dp,
                            end = if (index == dataState.data.size - 1) 16.dp else 0.dp
                        )
                        FoodRecipesRowItem(
                            data = data as FoodRecipesComplexSearchModel,
                            onItemClick = onItemClick,
                            modifier = itemDecorationModifier
                        )
                    }
                }
            }
        }

    }
}

@Composable
@Preview
private fun FoodRecipesLazyRowPreview() {
    FoodRecipesLazyRow(
        title = stringResource(id = R.string.food_recipes_label_recommend),
        listState = rememberLazyListState(),
        dataState = FoodRecipesDataState.FetchSucceed(
            data = listOf(
                FoodRecipesComplexSearchModel(
                    id = 0,
                    title = "Coffee Cookies",
                    author = "Foodista.com – The Cooking Encyclopedia Everyone Can Edit",
                    image = "https://spoonacular.com/recipeImages/639865-312x231.jpg",
                    isVegan = true,
                    healthScore = 10,
                    cookTime = 45
                ),
                FoodRecipesComplexSearchModel(
                    id = 0,
                    title = "Coffee Cookies",
                    author = "Foodista.com – The Cooking Encyclopedia Everyone Can Edit",
                    image = "https://spoonacular.com/recipeImages/639865-312x231.jpg",
                    isVegan = true,
                    healthScore = 10,
                    cookTime = 45
                ),
                FoodRecipesComplexSearchModel(
                    id = 0,
                    title = "Coffee Cookies",
                    author = "Foodista.com – The Cooking Encyclopedia Everyone Can Edit",
                    image = "https://spoonacular.com/recipeImages/639865-312x231.jpg",
                    isVegan = true,
                    healthScore = 10,
                    cookTime = 45
                )
            )
        ),
        onSeeMoreClick = {},
        onItemClick = {},
        onRetryClick = {}
    )
}