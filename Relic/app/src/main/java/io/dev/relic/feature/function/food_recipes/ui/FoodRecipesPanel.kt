package io.dev.relic.feature.function.food_recipes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import io.core.ui.CommonRetryComponent
import io.core.ui.theme.mainBackgroundColorLight
import io.core.ui.theme.placeHolderHighlightColor
import io.data.model.food_recipes.FoodRecipesComplexSearchInfoModel
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState

@Composable
fun FoodRecipesPanel(
    currentSelectedTab: Int,
    foodRecipesState: FoodRecipesDataState,
    onRetryClick: () -> Unit,
    onFetchMore: () -> Unit,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    tabLazyListState: LazyListState,
    contentLazyListState: LazyListState
) {
    when (val state: FoodRecipesDataState = foodRecipesState) {
        is FoodRecipesDataState.Init,
        is FoodRecipesDataState.Fetching -> {
            FoodRecipesPanel(
                currentSelectedTab = currentSelectedTab,
                isFetchingData = true,
                modelList = emptyList(),
                onRetryClick = {},
                onTabItemClick = { _: Int, _: String -> },
                tabLazyListState = tabLazyListState,
                contentLazyListState = contentLazyListState
            )
        }

        is FoodRecipesDataState.FetchSucceed -> {
            FoodRecipesPanel(
                currentSelectedTab = currentSelectedTab,
                tabLazyListState = tabLazyListState,
                contentLazyListState = contentLazyListState,
                isFetchingData = false,
                modelList = state.modelList,
                onRetryClick = onRetryClick,
                onTabItemClick = onTabItemClick
            )
        }

        is FoodRecipesDataState.Empty,
        is FoodRecipesDataState.NoFoodRecipesData,
        is FoodRecipesDataState.FetchFailed -> {
            FoodRecipesPanel(
                currentSelectedTab = currentSelectedTab,
                isFetchingData = false,
                modelList = emptyList(),
                onRetryClick = onRetryClick,
                onTabItemClick = onTabItemClick,
                tabLazyListState = tabLazyListState,
                contentLazyListState = contentLazyListState
            )
        }
    }
}

@Composable
private fun FoodRecipesPanel(
    currentSelectedTab: Int,
    isFetchingData: Boolean,
    modelList: List<FoodRecipesComplexSearchInfoModel?>?,
    onRetryClick: () -> Unit,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    tabLazyListState: LazyListState,
    contentLazyListState: LazyListState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        FoodRecipesTabBar(
            lazyListState = tabLazyListState,
            currentSelectedTab = currentSelectedTab,
            onTabItemClick = onTabItemClick
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (isFetchingData) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(196.dp)
                    .background(
                        color = mainBackgroundColorLight,
                        RoundedCornerShape(16.dp)
                    )
                    .placeholder(
                        visible = true,
                        color = Color.DarkGray,
                        shape = RoundedCornerShape(16.dp),
                        highlight = PlaceholderHighlight.shimmer(highlightColor = placeHolderHighlightColor)
                    )
            )
        } else {
            if (modelList.isNullOrEmpty()) {
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    CommonRetryComponent(
                        onRetryClick = onRetryClick,
                        containerHeight = 196.dp
                    )
                }
            } else {
                FoodRecipesCardList(
                    lazyListState = contentLazyListState,
                    modelList = modelList
                )
            }
        }
    }
}

@Composable
@Preview
private fun FoodRecipesNoDataCardPreview() {
    FoodRecipesPanel(
        currentSelectedTab = 0,
        isFetchingData = false,
        modelList = null,
        onRetryClick = {},
        onTabItemClick = { _: Int, _: String -> },
        tabLazyListState = rememberLazyListState(),
        contentLazyListState = rememberLazyListState()
    )
}

@Composable
@Preview
private fun FoodRecipesCardPreview() {
    FoodRecipesPanel(
        currentSelectedTab = 0,
        isFetchingData = false,
        modelList = listOf(
            FoodRecipesComplexSearchInfoModel(
                id = -1,
                title = "Food Recipes 1",
                image = "xxx",
                imageType = "xxx"
            ),
            FoodRecipesComplexSearchInfoModel(
                id = -2,
                title = "Food Recipes 2 | Food Recipes 2 | Food Recipes 2",
                image = "xxx",
                imageType = "xxx"
            )
        ),
        onRetryClick = {},
        onTabItemClick = { _: Int, _: String -> },
        tabLazyListState = rememberLazyListState(),
        contentLazyListState = rememberLazyListState()
    )
}