package io.dev.relic.feature.pages.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.common.util.TimeUtil.getCurrentTimeSection
import io.core.ui.dialog.CommonItemDivider
import io.core.ui.theme.mainThemeColor
import io.data.model.food_recipes.FoodRecipesComplexSearchModel
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
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
    foodRecipesTabLazyListState: LazyListState,
    foodRecipesContentLazyListState: LazyListState,
    foodRecipesTimeSectionState: FoodRecipesDataState,
    foodRecipesState: FoodRecipesDataState,
    currentSelectedFoodRecipesTab: Int,
    onSelectedFoodRecipesTabItem: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    onFoodRecipesSeeMoreClick: (dishType: String) -> Unit,
    onFoodRecipesItemClick: (recipesData: FoodRecipesComplexSearchModel) -> Unit,
    onFoodRecipesTimeSectionRetry: () -> Unit,
    onFoodRecipesRetry: () -> Unit
) {
    val currentTimeSection = getCurrentTimeSection()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mainThemeColor
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = foodRecipesContentLazyListState,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeTopPanel(
                currentTimeSection = currentTimeSection,
                agentSearchContent = agentSearchContent,
                onAgentSearchPromptChange = onAgentSearchPromptChange,
                onAgentStartChat = onAgentStartChat,
                onOpenDrawer = onOpenDrawer,
                onOpenSetting = onOpenSetting
            )
            item { Spacer(modifier = Modifier.height(16.dp)) }
            HomeFoodRecipesAutoTimeComponent(
                currentTimeSection = currentTimeSection,
                dataState = foodRecipesTimeSectionState,
                onSeeMoreClick = onFoodRecipesSeeMoreClick,
                onItemClick = onFoodRecipesItemClick,
                onRetryClick = onFoodRecipesTimeSectionRetry
            )
            item { CommonItemDivider() }
            HomeFoodRecipesTabBar(
                currentSelectedTab = currentSelectedFoodRecipesTab,
                lazyListState = foodRecipesTabLazyListState,
                onTabItemClick = onSelectedFoodRecipesTabItem
            )
            item { Spacer(modifier = Modifier.height(16.dp)) }
            HomeFoodRecipesList(
                dataState = foodRecipesState,
                onRetryClick = onFoodRecipesRetry,
                onItemClick = onFoodRecipesItemClick
            )
        }
    }
}