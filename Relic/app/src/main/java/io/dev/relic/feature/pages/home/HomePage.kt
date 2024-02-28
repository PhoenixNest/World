package io.dev.relic.feature.pages.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.common.RelicConstants.Common.UNKNOWN_VALUE_INT
import io.common.RelicConstants.Common.UNKNOWN_VALUE_STRING
import io.common.util.TimeUtil
import io.data.model.food_recipes.FoodRecipesComplexSearchModel
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.agent.gemini.viewmodel.GeminiAgentViewModel
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesCategories
import io.dev.relic.feature.function.food_recipes.viewmodel.FoodRecipesViewModel
import io.dev.relic.feature.pages.agent.navigateToAgentChatPage
import io.dev.relic.feature.pages.detail.food_recipe.navigateToFoodRecipeDetailPage
import io.dev.relic.feature.pages.home.ui.HomePageContent
import io.dev.relic.feature.pages.settings.navigateToSettingsPage
import io.dev.relic.feature.screens.main.MainScreenState
import kotlinx.coroutines.launch

@Composable
fun HomePageRoute(
    mainScreenState: MainScreenState,
    drawerState: DrawerState,
    mainViewModel: MainViewModel,
    geminiAgentViewModel: GeminiAgentViewModel,
    foodReViewModel: FoodRecipesViewModel
) {

    /* ======================== Common ======================== */

    val context = LocalContext.current
    val localFocusManager = LocalFocusManager.current

    val coroutineScope = mainScreenState.coroutineScope
    val navController = mainScreenState.navHostController

    /* ======================== Field ======================== */

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
                if (drawerState.isOpen || drawerState.isAnimationRunning) {
                    return@launch
                }

                if (drawerState.isClosed) {
                    drawerState.open()
                }
            }
        },
        onOpenSetting = {
            // Navigate to settings page.
            navController.navigateToSettingsPage()
        },
        agentSearchContent = agentSearchContent,
        onAgentSearchPromptChange = {
            geminiAgentViewModel.updateSearchPrompt(it)
        },
        onAgentStartChat = {
            geminiAgentViewModel.sendTextMessage(agentSearchContent)
            // Clear the latest input focus.
            localFocusManager.clearFocus()
            // Navigate to agent chat page.
            navController.navigateToAgentChatPage()
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
            val recipeId = it.id ?: UNKNOWN_VALUE_INT
            val recipeTitle = it.title ?: UNKNOWN_VALUE_STRING
            // Fetch the recipe details data.
            foodReViewModel.getRecipeInformationById(recipeId)
            // Then navigate to the details page.
            mainScreenState.navHostController.navigateToFoodRecipeDetailPage(
                recipeId = recipeId,
                recipeTitle = recipeTitle
            )
        },
        onFoodRecipesTimeSectionRetry = {
            val timeSection = TimeUtil.getCurrentTimeSection()
            foodReViewModel.getTimeSectionFoodRecipes(timeSection)
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
    HomePageContent(
        onOpenDrawer = onOpenDrawer,
        onOpenSetting = onOpenSetting,
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
        onOpenSetting = {},
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