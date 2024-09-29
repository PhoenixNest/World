package io.dev.relic.feature.pages.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.common.util.TimeUtil.getCurrentTimeSection
import io.core.ui.dialog.CommonItemDivider
import io.core.ui.theme.mainThemeColor
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.pages.home.HomeAgentAction
import io.dev.relic.feature.pages.home.HomeAgentState
import io.dev.relic.feature.pages.home.HomeFoodRecipesAction
import io.dev.relic.feature.pages.home.HomeFoodRecipesDataState
import io.dev.relic.feature.pages.home.HomeFoodRecipesListState
import io.dev.relic.feature.pages.home.HomeFoodRecipesRecommendAction
import io.dev.relic.feature.pages.home.HomeFoodRecipesState
import io.dev.relic.feature.pages.home.HomeFoodRecipesTimeSectionAction
import io.dev.relic.feature.pages.home.ui.widget.HomeFoodRecipesAutoTimeComponent
import io.dev.relic.feature.pages.home.ui.widget.HomeFoodRecipesList
import io.dev.relic.feature.pages.home.ui.widget.HomeFoodRecipesTabBar
import io.dev.relic.feature.pages.home.ui.widget.HomeTopPanel

@Composable
fun HomePageContent(
    onOpenDrawer: () -> Unit,
    onNavigateToExplore: () -> Unit,
    onNavigateToSetting: () -> Unit,
    agentState: HomeAgentState,
    foodRecipesState: HomeFoodRecipesState
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mainThemeColor
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = foodRecipesState.listState.recommendListState,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            agentState.apply {
                HomeTopPanel(
                    currentTimeSection = getCurrentTimeSection(),
                    agentPrompt = prompt,
                    onAgentPromptChange = action.onPromptChange,
                    onAgentStartChat = action.onStartChat,
                    onOpenDrawer = onOpenDrawer,
                    onNavigateToExplore = onNavigateToExplore,
                    onNavigateToSetting = onNavigateToSetting
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            foodRecipesState.apply {
                HomeFoodRecipesAutoTimeComponent(
                    currentTimeSection = getCurrentTimeSection(),
                    listState = listState.timeSectionListState,
                    dataState = dataState.timeSectionDataState,
                    onSeeMoreClick = action.timeSectionAction.onSeeMoreClick,
                    onItemClick = action.timeSectionAction.onItemClick,
                    onRetryClick = action.timeSectionAction.onRetryClick
                )
                item { CommonItemDivider() }
                HomeFoodRecipesTabBar(
                    currentSelectedTab = currentSelectTab,
                    lazyListState = listState.recommendTabListState,
                    onTabItemClick = action.recommendAction.onTabItemClick
                )
                item { Spacer(modifier = Modifier.height(16.dp)) }
                HomeFoodRecipesList(
                    dataState = dataState.recommendDataState,
                    onItemClick = action.recommendAction.onItemClick,
                    onRetryClick = action.recommendAction.onRetryClick
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
        onNavigateToExplore = {},
        onNavigateToSetting = {},
        agentState = HomeAgentState(
            prompt = "",
            action = HomeAgentAction(
                onPromptChange = {},
                onStartChat = {}
            )
        ),
        foodRecipesState = HomeFoodRecipesState(
            currentSelectTab = 0,
            dataState = HomeFoodRecipesDataState(
                timeSectionDataState = FoodRecipesDataState.Init,
                recommendDataState = FoodRecipesDataState.Init
            ),
            action = HomeFoodRecipesAction(
                timeSectionAction = HomeFoodRecipesTimeSectionAction(
                    onItemClick = {},
                    onSeeMoreClick = {},
                    onRetryClick = {}
                ),
                recommendAction = HomeFoodRecipesRecommendAction(
                    onTabItemClick = { _, _ -> },
                    onItemClick = {},
                    onRetryClick = {}
                )
            ),
            listState = HomeFoodRecipesListState(
                timeSectionListState = rememberLazyListState(),
                recommendTabListState = rememberLazyListState(),
                recommendListState = rememberLazyListState()
            )
        )
    )
}