package io.dev.relic.feature.pages.home

import androidx.compose.foundation.lazy.LazyListState
import io.data.model.food_recipes.FoodRecipesComplexSearchModel
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState

/* ======================== Agent ======================== */

/**
 * Ui and Data state for Agent unit in home page.
 *
 * @param prompt
 * @param action        Ui action handler for Food Recipes unit in home page.
 * */
data class HomeAgentState(
    val prompt: String,
    val action: HomeAgentAction
)

/**
 * According to the page ui, the class is use for handle the ui action in Agent unit.
 *
 * @param onPromptChange        Detect the input change on user input the prompt.
 * @param onStartChat           Click the send button and navigate to the chat page to continue the conversation.
 * */
data class HomeAgentAction(
    val onPromptChange: (newPrompt: String) -> Unit,
    val onStartChat: () -> Unit
)

/* ======================== Food Recipes ======================== */

/**
 * Ui and Data state for food recipes unit in home page.
 *
 * @param currentSelectTab          Current selected category of recommend panel.
 * @param dataState                 Data for Food Recipes unit in home page.
 * @param action                    Ui action handler for Food Recipes unit in home page.
 * @param listState                 Compose list state for Food Recipes unit in home page.
 *
 * @see HomeFoodRecipesDataState
 * @see HomeFoodRecipesAction
 * @see HomeFoodRecipesListState
 * */
data class HomeFoodRecipesState(
    val currentSelectTab: Int,
    val dataState: HomeFoodRecipesDataState,
    val action: HomeFoodRecipesAction,
    val listState: HomeFoodRecipesListState
)

/**
 * Data state for Food Recipes unit in home page.
 *
 * @param timeSectionDataState      Data for the horizontal time-section panel.
 * @param recommendDataState        Data fot the vertical recommend panel.
 * */
data class HomeFoodRecipesDataState(
    val timeSectionDataState: FoodRecipesDataState,
    val recommendDataState: FoodRecipesDataState,
)

/**
 * According to the page ui, the class is use for handle the ui action in Food Recipes unit.
 *
 * @param timeSectionAction     Action handler of Time-section panel.
 * @param recommendAction       Action handler of recommend panel.
 * */
data class HomeFoodRecipesAction(
    val timeSectionAction: HomeFoodRecipesTimeSectionAction,
    val recommendAction: HomeFoodRecipesRecommendAction
)

/**
 * According to the page ui, this class is use for handle the click action in Time-section panel.
 *
 * @param onItemClick       Click the recipe item to read the detail information.
 * @param onSeeMoreClick    Click the see more button to get more choice idea in time-section food.
 * @param onRetryClick      Click the retry button when failed to fetch the latest recipe's data.
 * */
data class HomeFoodRecipesTimeSectionAction(
    val onItemClick: (recipesData: FoodRecipesComplexSearchModel) -> Unit,
    val onSeeMoreClick: (dishType: String) -> Unit,
    val onRetryClick: () -> Unit
)

/**
 * According to the page ui, this class is use for handle the click action in Recommend panel.
 *
 * @param onTabItemClick        Click the tab to change the recommend category.
 * @param onItemClick           Click the recipe item to read the detail information.
 * @param onRetryClick          Click the retry button when failed to fetch the latest recipes' data.
 * */
data class HomeFoodRecipesRecommendAction(
    val onTabItemClick: (selectedTab: Int, selectedItem: String) -> Unit,
    val onItemClick: (recipesData: FoodRecipesComplexSearchModel) -> Unit,
    val onRetryClick: () -> Unit
)

/**
 * According to the page ui, the order of list state will be:
 *
 * @param timeSectionListState           For time-section list (horizontal).
 * @param recommendTabListState          For food Recipes tab panel.
 * @param recommendListState             For food Recipes main list (vertical).
 * */
data class HomeFoodRecipesListState(
    val timeSectionListState: LazyListState,
    val recommendTabListState: LazyListState,
    val recommendListState: LazyListState
)