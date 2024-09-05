package io.dev.relic.feature.pages.home

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.common.RelicConstants.Common.UNKNOWN_VALUE_INT
import io.common.RelicConstants.Common.UNKNOWN_VALUE_STRING
import io.common.util.LogUtil
import io.common.util.TimeUtil
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
import io.module.map.tomtom.legacy.TomTomMapActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filter
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
    val agentPrompt = geminiAgentViewModel.agentSearchContent

    // Time-section Food Recipes
    val foodRecipesTimeSectionDataState by foodRecipesViewModel.timeSectionDataStateFlow
        .collectAsStateWithLifecycle()

    // Recommend Food Recipes
    val foodRecipesRecommendDataState by foodRecipesViewModel.recommendDataStateFlow
        .collectAsStateWithLifecycle()

    /* ======================== Ui ======================== */

    val drawerState = mainScreenState.drawerState

    val foodRecipesListState = HomeFoodRecipesListState(
        timeSectionListState = rememberLazyListState(),
        recommendTabListState = rememberLazyListState(),
        recommendListState = rememberLazyListState()
    )

    /* ======================== Ui State ======================== */

    val agentState = buildAgentState(
        agentPrompt = agentPrompt,
        viewModel = geminiAgentViewModel,
        onNavigateToAgentChatPage = {
            // Clear the latest input focus.
            localFocusManager.clearFocus()
            // Navigate to agent chat page.
            navController.navigateToAgentChatPage()
        }
    )

    val foodRecipesState = buildFoodRecipesState(
        coroutineScope = coroutineScope,
        timeSectionDataState = foodRecipesTimeSectionDataState,
        recommendDataState = foodRecipesRecommendDataState,
        viewModel = foodRecipesViewModel,
        listState = foodRecipesListState,
        onNavigateToDetailPage = { recipeId, recipeTitle ->
            // Navigate to the details page.
            navController.navigateToFoodRecipeDetailPage(
                recipeId = recipeId,
                recipeTitle = recipeTitle
            )
        }
    )

    LaunchedEffect(foodRecipesListState.recommendListState) {
        snapshotFlow {
            foodRecipesListState.recommendListState.firstVisibleItemIndex
        }.filter {
            it >= (foodRecipesViewModel.getRecommendDataList().size / 2)
        }.collect {
            if (foodRecipesViewModel.isFetchingMore) {
                LogUtil.w("FoodRecipesViewModel", "[Fetch More Recommend Data] Already executed, skip this time.")
                return@collect
            }

            // Fetch more recommend data.
            foodRecipesViewModel.apply {
                if (canFetchMore) {
                    val currentTab = getSelectedFoodRecipesTab()
                    val queryType = FoodRecipesCategories.entries[currentTab]
                    fetchMoreRecommendData(queryString = queryType.name.lowercase())
                }
            }
        }
    }

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
        onNavigateToExplore = { TomTomMapActivity.start(context) },
        onNavigateToSetting = navController::navigateToSettingsPage,
        agentState = agentState,
        foodRecipesState = foodRecipesState
    )
}

@Composable
private fun HomePage(
    onOpenDrawer: () -> Unit,
    onNavigateToExplore: () -> Unit,
    onNavigateToSetting: () -> Unit,
    agentState: HomeAgentState,
    foodRecipesState: HomeFoodRecipesState,
) {
    HomePageContent(
        onOpenDrawer = onOpenDrawer,
        onNavigateToExplore = onNavigateToExplore,
        onNavigateToSetting = onNavigateToSetting,
        agentState = agentState,
        foodRecipesState = foodRecipesState
    )
}

@Composable
@Preview
private fun HomePagePreview() {
    HomePage(
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

/* ======================== Page Ui State Builder ======================== */

/**
 * Build state to power Agent unit of home page.
 *
 * @param agentPrompt                   Chat prompt of agent.
 * @param viewModel                     Handle the internal data.
 * @param onNavigateToAgentChatPage     Navigate to the next page to continue the agent chat.
 * */
private fun buildAgentState(
    agentPrompt: String,
    viewModel: GeminiAgentViewModel,
    onNavigateToAgentChatPage: () -> Unit
): HomeAgentState {
    return HomeAgentState(
        prompt = agentPrompt,
        action = HomeAgentAction(
            onPromptChange = {
                viewModel.updateSearchPrompt(it)
            },
            onStartChat = {
                viewModel.sendTextMessage(agentPrompt)
                // Navigate to agent chat page.
                onNavigateToAgentChatPage.invoke()
            }
        ),
    )
}

/**
 * Build state to power the Food Recipes unit of home page.
 *
 * @param timeSectionDataState          Data state flow of Time-Section.
 * @param recommendDataState            Data state flow of Recommend.
 * @param viewModel                     Handle the internal data or emit the latest ui state etc.
 * @param listState                     List state for row and column.
 * @param onNavigateToDetailPage        Navigate to next page to read the detail information of the selected food recipe.
 *
 * @see HomeFoodRecipesState
 * */
private fun buildFoodRecipesState(
    coroutineScope: CoroutineScope,
    timeSectionDataState: FoodRecipesDataState,
    recommendDataState: FoodRecipesDataState,
    viewModel: FoodRecipesViewModel,
    listState: HomeFoodRecipesListState,
    onNavigateToDetailPage: (recipeId: Int, recipeTitle: String) -> Unit
): HomeFoodRecipesState {
    return HomeFoodRecipesState(
        currentSelectTab = viewModel.getSelectedFoodRecipesTab(),
        dataState = HomeFoodRecipesDataState(
            timeSectionDataState = timeSectionDataState,
            recommendDataState = recommendDataState,
        ),
        action = HomeFoodRecipesAction(
            timeSectionAction = HomeFoodRecipesTimeSectionAction(
                onItemClick = {
                    val recipeId = it.id ?: UNKNOWN_VALUE_INT
                    val recipeTitle = it.title ?: UNKNOWN_VALUE_STRING
                    // Fetch the recipe details data.
                    viewModel.getRecipeInformationById(recipeId)
                    // Then navigate to the details page.
                    onNavigateToDetailPage.invoke(recipeId, recipeTitle)
                },
                onSeeMoreClick = {
                    //
                },
                onRetryClick = {
                    val timeSection = TimeUtil.getCurrentTimeSection()
                    viewModel.getTimeSectionFoodRecipes(timeSection)
                },
            ),
            recommendAction = HomeFoodRecipesRecommendAction(
                onTabItemClick = { selectedTab, selectedItem ->
                    // Avoid duplicate click from user.
                    val currentSelectedTab = viewModel.getSelectedFoodRecipesTab()
                    if (currentSelectedTab == selectedTab) {
                        return@HomeFoodRecipesRecommendAction
                    }

                    viewModel.apply {
                        resetRecommendFoodRecipesOffset()
                        resetCanFetchMoreStatus()
                        updateSelectedFoodRecipesTab(selectedTab)
                        getRecommendFoodRecipes(
                            queryType = selectedItem,
                            offset = 0
                        )

                        // Scroll to TOP.
                        coroutineScope.launch {
                            listState.recommendListState.animateScrollToItem(4)
                        }
                    }
                },
                onItemClick = {
                    val recipeId = it.id ?: UNKNOWN_VALUE_INT
                    val recipeTitle = it.title ?: UNKNOWN_VALUE_STRING
                    // Fetch the recipe details data.
                    viewModel.getRecipeInformationById(recipeId)
                    // Then navigate to the details page.
                    onNavigateToDetailPage.invoke(recipeId, recipeTitle)
                },
                onRetryClick = {
                    val foodRecipesCategories = FoodRecipesCategories.entries.toList()
                    val currentTab = viewModel.getSelectedFoodRecipesTab()
                    viewModel.getRecommendFoodRecipes(
                        queryType = foodRecipesCategories[currentTab].name.lowercase(),
                        offset = 0
                    )
                }
            )
        ),
        listState = listState
    )
}
