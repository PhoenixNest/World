package io.dev.relic.feature.pages.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.theme.RelicAppTheme
import io.dev.relic.feature.function.agent.AgentQuestionModel
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.pages.home.ui.widget.HomeFeaturePanelAction
import io.dev.relic.feature.pages.home.ui.widget.HomeFeaturesPanel
import io.dev.relic.feature.pages.home.ui.widget.HomeQuickPromptsPanel
import io.dev.relic.feature.pages.home.ui.widget.HomeQuickPromptsPanelAction
import io.dev.relic.feature.pages.home.ui.widget.HomeRecommendFoodsPanel
import io.dev.relic.feature.pages.home.ui.widget.HomeRecommendFoodsPanelAction

@Composable
fun HomePageContent(
    featurePanelAction: HomeFeaturePanelAction,
    quickPromptsPanelAction: HomeQuickPromptsPanelAction,
    recommendFoodsPanelAction: HomeRecommendFoodsPanelAction
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.Start,
        contentPadding = PaddingValues(top = 12.dp)
    ) {
        item {
            HomeFeaturesPanel(
                action = featurePanelAction,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
        item {
            HomeQuickPromptsPanel(
                action = quickPromptsPanelAction,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
        item {
            HomeRecommendFoodsPanel(
                state = recommendFoodsPanelAction.state,
                action = recommendFoodsPanelAction,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
        item { Spacer(Modifier.height(160.dp)) }
    }
}

@Composable
@Preview(showBackground = true)
private fun HomePageCompatModeContentPreview() {
    RelicAppTheme {
        HomePageContent(
            featurePanelAction = HomeFeaturePanelAction(
                onAgentClick = {},
                onFoodRecipesClick = {},
                onTodoClick = {}
            ),
            quickPromptsPanelAction = HomeQuickPromptsPanelAction(
                questions = AgentQuestionModel.getRandomQuestions(),
                onRefreshClick = {},
                onPromptClick = {}
            ),
            recommendFoodsPanelAction = HomeRecommendFoodsPanelAction(
                state = FoodRecipesDataState.Init,
                onRefreshClick = {},
                onItemClick = {}
            )
        )
    }
}