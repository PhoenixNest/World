package io.dev.relic.feature.route

import androidx.compose.material.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.core.ui.ext.SystemUiControllerExt.updateStatusBarColor
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.agent.gemini.viewmodel.GeminiAgentViewModel
import io.dev.relic.feature.function.food_recipes.viewmodel.FoodRecipesViewModel
import io.dev.relic.feature.pages.agent.pageAgentChat
import io.dev.relic.feature.pages.detail.food_recipes.pageFoodRecipeDetail
import io.dev.relic.feature.pages.detail.news.pageNewsDetail
import io.dev.relic.feature.pages.explore.pageExplore
import io.dev.relic.feature.pages.hive.pageHive
import io.dev.relic.feature.pages.home.pageHome
import io.dev.relic.feature.screens.main.MainScreenState
import io.dev.relic.feature.screens.main.util.MainScreenTopLevelDestination.EXPLORE
import io.dev.relic.feature.screens.main.util.MainScreenTopLevelDestination.HIVE
import io.dev.relic.feature.screens.main.util.MainScreenTopLevelDestination.HOME

@Composable
fun MainFeatureNavHost(
    mainScreenState: MainScreenState,
    drawerState: DrawerState,
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    geminiAgentViewModel: GeminiAgentViewModel,
    foodRecipesViewModel: FoodRecipesViewModel,
    modifier: Modifier = Modifier,
    startDestination: String = RelicRoute.BASE_ROUTE
) {
    val systemUiController = rememberSystemUiController()

    when (mainScreenState.currentTopLevelDestination) {
        HOME -> systemUiController.updateStatusBarColor(darkIcons = false)
        EXPLORE -> systemUiController.updateStatusBarColor(darkIcons = true)
        HIVE -> systemUiController.updateStatusBarColor(darkIcons = false)
        else -> systemUiController.updateStatusBarColor(darkIcons = false)
    }

    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        pageHome(
            mainScreenState = mainScreenState,
            drawerState = drawerState,
            mainViewModel = mainViewModel,
            geminiAgentViewModel = geminiAgentViewModel,
            foodRecipesViewModel = foodRecipesViewModel
        )
        pageExplore(
            mainScreenState = mainScreenState,
            mainViewModel = mainViewModel
        )
        pageHive(
            mainScreenState = mainScreenState,
            mainViewModel = mainViewModel
        )
        pageAgentChat(
            mainScreenState = mainScreenState,
            mainViewModel = mainViewModel,
            geminiAgentViewModel = geminiAgentViewModel,
            onBackClick = navHostController::popBackStack
        )
        pageNewsDetail(
            onBackClick = navHostController::popBackStack
        )
        pageFoodRecipeDetail(
            foodRecipesViewModel = foodRecipesViewModel,
            onBackClick = navHostController::popBackStack
        )
    }
}