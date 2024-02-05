package io.dev.relic.feature.pages.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.common.util.TimeUtil.getCurrentTimeSection
import io.data.model.food_recipes.FoodRecipesComplexSearchModel
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.agent.gemini.viewmodel.GeminiAgentViewModel
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesCategories
import io.dev.relic.feature.function.food_recipes.viewmodel.FoodRecipesViewModel
import io.dev.relic.feature.pages.home.ui.HomePageContent
import io.dev.relic.feature.screens.main.MainScreenState
import kotlinx.coroutines.launch

@Composable
fun HomePageRoute(
    mainScreenState: MainScreenState,
    drawerState: DrawerState,
    mainViewModel: MainViewModel,
    geminiAgentViewModel: GeminiAgentViewModel = hiltViewModel(),
    foodReViewModel: FoodRecipesViewModel = hiltViewModel()
) {

    /* ======================== Common ======================== */

    val coroutineScope = rememberCoroutineScope()
    val currentTimeSection = getCurrentTimeSection()
    val localFocusManager = LocalFocusManager.current

    // Agent
    val agentSearchContent = geminiAgentViewModel.agentSearchContent

    // Food recipes
    val foodRecipesTimeSectionState by foodReViewModel.foodRecipesTimeSectionDataStateFlow.collectAsStateWithLifecycle()
    val foodRecipesState by foodReViewModel.foodRecipesDataStateFlow.collectAsStateWithLifecycle()

    /* ======================== Ui ======================== */

    val foodRecipesTabLazyListState = rememberLazyListState()
    val foodRecipesContentLazyListState = rememberLazyListState()

    HomePage(
        onOpenDrawer = {
            coroutineScope.launch {
                drawerState.open()
            }
        },
        agentSearchContent = agentSearchContent,
        onAgentSearchPromptChange = {
            geminiAgentViewModel.updateSearchPrompt(it)
        },
        onAgentStartChat = {
            geminiAgentViewModel.sendTextMessage(agentSearchContent)
            localFocusManager.clearFocus()
        },
        foodRecipesTabLazyListState = foodRecipesTabLazyListState,
        foodRecipesContentLazyListState = foodRecipesContentLazyListState,
        foodRecipesTimeSectionState = foodRecipesTimeSectionState,
        foodRecipesState = foodRecipesState,
        currentSelectedFoodRecipesTab = foodReViewModel.getSelectedFoodRecipesTab(),
        onSelectedFoodRecipesTabItem = { currentSelectedTab, selectedItem ->
            foodReViewModel.apply {
                updateSelectedFoodRecipesTab(currentSelectedTab)
                getRecommendFoodRecipes(
                    queryType = selectedItem,
                    offset = 0
                )
            }
        },
        onFoodRecipesSeeMoreClick = {
            //
        },
        onFoodRecipesItemClick = {
            //
        },
        onFoodRecipesTimeSectionRetry = {
            foodReViewModel.getTimeSectionFoodRecipes(currentTimeSection)
        },
        onFoodRecipesRetry = {
            foodReViewModel.apply {
                val foodRecipesCategories = FoodRecipesCategories.entries.toList()
                val selectedFoodRecipesTab = getSelectedFoodRecipesTab()
                val currentSelectedTab = foodRecipesCategories[selectedFoodRecipesTab].name.lowercase()
                getRecommendFoodRecipes(
                    queryType = currentSelectedTab,
                    offset = 0
                )
            }
        }
    )
}

@Composable
private fun HomePage(
    onOpenDrawer: () -> Unit,
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
    HomePageContent(
        onOpenDrawer = onOpenDrawer,
        agentSearchContent = agentSearchContent,
        onAgentSearchPromptChange = onAgentSearchPromptChange,
        onAgentStartChat = onAgentStartChat,
        foodRecipesTabLazyListState = foodRecipesTabLazyListState,
        foodRecipesContentLazyListState = foodRecipesContentLazyListState,
        foodRecipesTimeSectionState = foodRecipesTimeSectionState,
        foodRecipesState = foodRecipesState,
        currentSelectedFoodRecipesTab = currentSelectedFoodRecipesTab,
        onSelectedFoodRecipesTabItem = onSelectedFoodRecipesTabItem,
        onFoodRecipesSeeMoreClick = onFoodRecipesSeeMoreClick,
        onFoodRecipesItemClick = onFoodRecipesItemClick,
        onFoodRecipesTimeSectionRetry = onFoodRecipesTimeSectionRetry,
        onFoodRecipesRetry = onFoodRecipesRetry
    )
}

@Composable
@Preview
private fun HomePagePreview() {
    HomePage(
        onOpenDrawer = {},
        agentSearchContent = "",
        onAgentSearchPromptChange = {},
        onAgentStartChat = {},
        foodRecipesTabLazyListState = rememberLazyListState(),
        foodRecipesContentLazyListState = rememberLazyListState(),
        foodRecipesTimeSectionState = FoodRecipesDataState.Init,
        foodRecipesState = FoodRecipesDataState.Init,
        currentSelectedFoodRecipesTab = 0,
        onSelectedFoodRecipesTabItem = { _, _ -> },
        onFoodRecipesSeeMoreClick = {},
        onFoodRecipesItemClick = {},
        onFoodRecipesTimeSectionRetry = {},
        onFoodRecipesRetry = {}
    )
}