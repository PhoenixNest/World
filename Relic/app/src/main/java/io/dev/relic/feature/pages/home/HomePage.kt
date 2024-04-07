package io.dev.relic.feature.pages.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.common.RelicConstants.Common.UNKNOWN_VALUE_INT
import io.common.RelicConstants.Common.UNKNOWN_VALUE_STRING
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.common.util.LogUtil
import io.common.util.TimeUtil
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainIconColor
import io.core.ui.theme.mainTextColorDark
import io.core.ui.theme.mainThemeColorAccent
import io.data.model.food_recipes.FoodRecipesComplexSearchModel
import io.dev.relic.R
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
    val foodRecipesTimeSectionDataState by foodRecipesViewModel.timeSectionDataStateFlow
        .collectAsStateWithLifecycle()

    // Recommend Food Recipes
    val foodRecipesRecommendDataState by foodRecipesViewModel.recommendDataStateFlow
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
    Box(modifier = Modifier.fillMaxWidth()) {
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
        ExploreComponent(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
private fun ExploreComponent(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Surface(
        modifier = modifier
            .padding(bottom = 52.dp)
            .fillMaxWidth()
            .height(64.dp)
            .clickable {
                TomTomMapActivity.start(context)
            },
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        color = mainThemeColorAccent
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_bottom_tab_explore_unselected),
                contentDescription = DEFAULT_DESC,
                tint = mainIconColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.home_navigate_to_explore),
                style = TextStyle(
                    color = mainTextColorDark,
                    fontSize = 16.sp,
                    fontFamily = ubuntu,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
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