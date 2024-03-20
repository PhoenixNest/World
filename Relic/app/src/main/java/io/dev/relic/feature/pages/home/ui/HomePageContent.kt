package io.dev.relic.feature.pages.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.common.util.TimeUtil.getCurrentTimeSection
import io.core.ui.dialog.CommonItemDivider
import io.core.ui.theme.mainThemeColor
import io.data.model.food_recipes.FoodRecipesComplexSearchModel
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.pages.home.HomeFoodRecipesListState
import io.dev.relic.feature.pages.home.HomeFoodRecipesState
import io.dev.relic.feature.pages.home.ui.widget.HomeFoodRecipesAutoTimeComponent
import io.dev.relic.feature.pages.home.ui.widget.HomeFoodRecipesList
import io.dev.relic.feature.pages.home.ui.widget.HomeFoodRecipesTabBar
import io.dev.relic.feature.pages.home.ui.widget.HomeTopPanel

@Composable
fun HomePageContent(
    onOpenDrawer: () -> Unit,
    onOpenSetting: () -> Unit,
    agentSearchContent: String,
    onAgentSearchPromptChange: (newPrompt: String) -> Unit,
    onAgentStartChat: () -> Unit,
    foodRecipesState: HomeFoodRecipesState,
    onSelectedFoodRecipesTabItem: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    onFoodRecipesSeeMoreClick: (dishType: String) -> Unit,
    onFoodRecipesItemClick: (recipesData: FoodRecipesComplexSearchModel) -> Unit,
    onFoodRecipesTimeSectionRetry: () -> Unit,
    onFoodRecipesRetry: () -> Unit
) {
    foodRecipesState.apply {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = mainThemeColor
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState.recommendListState,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HomeTopPanel(
                    currentTimeSection = getCurrentTimeSection(),
                    agentSearchContent = agentSearchContent,
                    onAgentSearchPromptChange = onAgentSearchPromptChange,
                    onAgentStartChat = onAgentStartChat,
                    onOpenDrawer = onOpenDrawer,
                    onOpenSetting = onOpenSetting
                )
                item { Spacer(modifier = Modifier.height(16.dp)) }
                HomeFoodRecipesAutoTimeComponent(
                    currentTimeSection = getCurrentTimeSection(),
                    listState = listState.timeSectionListState,
                    dataState = timeSectionDataState,
                    onSeeMoreClick = onFoodRecipesSeeMoreClick,
                    onItemClick = onFoodRecipesItemClick,
                    onRetryClick = onFoodRecipesTimeSectionRetry
                )
                item { CommonItemDivider() }
                HomeFoodRecipesTabBar(
                    currentSelectedTab = currentSelectTab,
                    lazyListState = listState.recommendTabListState,
                    onTabItemClick = onSelectedFoodRecipesTabItem
                )
                item { Spacer(modifier = Modifier.height(16.dp)) }
                HomeFoodRecipesList(
                    dataState = recommendDataState,
                    onRetryClick = onFoodRecipesRetry,
                    onItemClick = onFoodRecipesItemClick
                )
            }
        }
    }
}

@Composable
@Preview
private fun HomePageContentPreview() {
    HomePageContent(
        onOpenDrawer = {},
        onOpenSetting = {},
        agentSearchContent = "",
        onAgentSearchPromptChange = {},
        onAgentStartChat = {},
        foodRecipesState = HomeFoodRecipesState(
            currentSelectTab = 0,
            timeSectionDataState = FoodRecipesDataState.Init,
            recommendDataState = FoodRecipesDataState.Init,
            listState = HomeFoodRecipesListState(
                timeSectionListState = rememberLazyListState(),
                recommendTabListState = rememberLazyListState(),
                recommendListState = rememberLazyListState()
            )
        ),
        onSelectedFoodRecipesTabItem = { _, _ -> },
        onFoodRecipesSeeMoreClick = {},
        onFoodRecipesItemClick = {},
        onFoodRecipesTimeSectionRetry = {},
        onFoodRecipesRetry = {}
    )
}