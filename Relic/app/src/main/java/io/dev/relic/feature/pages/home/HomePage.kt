package io.dev.relic.feature.pages.home

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.common.RelicConstants.Common.UNKNOWN_VALUE_INT
import io.common.RelicConstants.Common.UNKNOWN_VALUE_STRING
import io.common.util.LogUtil
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
    mainViewModel: MainViewModel,
    geminiAgentViewModel: GeminiAgentViewModel,
    foodRecipesViewModel: FoodRecipesViewModel
) {

    /* ======================== Common ======================== */

    val context = LocalContext.current
    val localFocusManager = LocalFocusManager.current
    val coroutineScope = mainScreenState.coroutineScope
    val navController = mainScreenState.navHostController

    /* ======================== Field ======================== */

    // Agent
    val agentSearchContent = geminiAgentViewModel.agentSearchContent

    // Time-section Food Recipes
    val foodRecipesTimeSectionDataState by foodRecipesViewModel.foodRecipesTimeSectionDataStateFlow
        .collectAsStateWithLifecycle()

    // Recommend Food Recipes
    val foodRecipesRecommendDataState by foodRecipesViewModel.foodRecipesRecommendDataStateFlow
        .collectAsStateWithLifecycle()

    /* ======================== Ui ======================== */

    val drawerState = mainScreenState.drawerState

    // List state
    val foodRecipesListState = HomeFoodRecipesListState(
        timeSectionListState = rememberLazyListState(),
        recommendTabListState = rememberLazyListState(),
        recommendListState = rememberLazyListState()
    )

    // Data state
    val foodRecipesState = HomeFoodRecipesState(
        currentSelectTab = foodRecipesViewModel.getSelectedFoodRecipesTab(),
        timeSectionDataState = foodRecipesTimeSectionDataState,
        recommendDataState = foodRecipesRecommendDataState,
        listState = foodRecipesListState
    )

    HomePage(
        onOpenDrawer = {
            coroutineScope.launch {
                if (drawerState.isOpen || drawerState.isAnimationRunning) {
                    LogUtil.d("MainScreenDrawer", "[Drawer] Can't open drawer")
                    return@launch
                }

                if (drawerState.isClosed) {
                    LogUtil.d("MainScreenDrawer", "[Drawer] Open drawer")
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
        foodRecipesState = foodRecipesState,
        onFoodRecipesTabItemClick = { currentSelectedTab, selectedItem ->
            foodRecipesViewModel.apply {
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
            foodRecipesViewModel.getRecipeInformationById(recipeId)
            // Then navigate to the details page.
            mainScreenState.navHostController.navigateToFoodRecipeDetailPage(
                recipeId = recipeId,
                recipeTitle = recipeTitle
            )
        },
        onFoodRecipesTimeSectionRetry = {
            val timeSection = TimeUtil.getCurrentTimeSection()
            foodRecipesViewModel.getTimeSectionFoodRecipes(timeSection)
        },
        onFoodRecipesRetry = {
            val foodRecipesCategories = FoodRecipesCategories.entries.toList()
            val currentTab = foodRecipesViewModel.getSelectedFoodRecipesTab()
            foodRecipesViewModel.getRecommendFoodRecipes(
                queryType = foodRecipesCategories[currentTab].name.lowercase(),
                offset = 0
            )
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
    // Data state
    foodRecipesState: HomeFoodRecipesState,
    // Click handler
    onFoodRecipesTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit,
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
        foodRecipesState = foodRecipesState,
        onSelectedFoodRecipesTabItem = onFoodRecipesTabItemClick,
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
        onFoodRecipesTabItemClick = { _, _ -> },
        onFoodRecipesSeeMoreClick = {},
        onFoodRecipesItemClick = {},
        onFoodRecipesTimeSectionRetry = {},
        onFoodRecipesRetry = {}
    )
}