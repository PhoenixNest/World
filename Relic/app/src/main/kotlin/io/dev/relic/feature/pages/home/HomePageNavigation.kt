package io.dev.relic.feature.pages.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.agent.gemini.viewmodel.GeminiAgentViewModel
import io.dev.relic.feature.function.food_recipes.viewmodel.FoodRecipesViewModel
import io.dev.relic.feature.route.RelicRoute.HOME
import io.dev.relic.feature.screens.main.MainScreenState

fun NavController.navigateToHomePage(navOptions: NavOptions? = null) {
    this.navigate(
        route = HOME,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.pageHome(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    geminiAgentViewModel: GeminiAgentViewModel,
    foodRecipesViewModel: FoodRecipesViewModel
) {
    composable(route = HOME) {
        HomePageRoute(
            mainScreenState = mainScreenState,
            mainViewModel = mainViewModel,
            geminiAgentViewModel = geminiAgentViewModel,
            foodRecipesViewModel = foodRecipesViewModel
        )
    }
}