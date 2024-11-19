package io.dev.relic.feature.route

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.core.ui.ext.SystemUiControllerExt.updateStatusBarColor
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.agent.gemini.vm.GeminiAgentViewModel
import io.dev.relic.feature.function.food_recipes.vm.FoodRecipesViewModel
import io.dev.relic.feature.function.gallery.vm.GalleryViewModel
import io.dev.relic.feature.function.news.vm.NewsViewModel
import io.dev.relic.feature.function.todo.vm.TodoViewModel
import io.dev.relic.feature.pages.agent.pageAgentChat
import io.dev.relic.feature.pages.detail.food_recipe.pageFoodRecipeDetail
import io.dev.relic.feature.pages.detail.gallery.pageGalleryDetail
import io.dev.relic.feature.pages.detail.news.pageNewsDetail
import io.dev.relic.feature.pages.gallery.pageGallery
import io.dev.relic.feature.pages.home.pageHome
import io.dev.relic.feature.pages.settings.pageSettings
import io.dev.relic.feature.pages.studio.pageStudio
import io.dev.relic.feature.route.RelicRoute.START_DESTINATION
import io.dev.relic.feature.screens.main.MainScreenState
import io.dev.relic.feature.screens.main.util.MainScreenTopLevelDestination.HOME
import io.dev.relic.feature.screens.main.util.MainScreenTopLevelDestination.STUDIO

/**
 * Main Screen navigation route host
 *
 * Reference docs:
 *
 * - [Shared elements with Navigation Compose](https://developer.android.google.cn/develop/ui/compose/animation/shared-elements/navigation#predictive-back)
 * - [Shared Element Transitions in Compose](https://developer.android.google.cn/develop/ui/compose/animation/shared-elements)
 *
 * @param mainScreenState
 * @param navHostController
 * @param mainViewModel                 Global ViewModel
 * @param geminiAgentViewModel          Provide the Ai Chat feature to Home page
 * @param foodRecipesViewModel          Provide the Food Recipes feature to Home page
 * @param todoViewModel                 Provide the todo feature to Studio page
 * @param newsViewModel                 Provide the News feature to Studio page
 * @param galleryViewModel              Provide the gallery feature to Gallery page
 * @param startDestination              The journey begins from here
 * */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainFeatureNavHost(
    mainScreenState: MainScreenState,
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    geminiAgentViewModel: GeminiAgentViewModel,
    foodRecipesViewModel: FoodRecipesViewModel,
    todoViewModel: TodoViewModel,
    newsViewModel: NewsViewModel,
    galleryViewModel: GalleryViewModel,
    startDestination: String = START_DESTINATION
) {
    val systemUiController = rememberSystemUiController()

    when (mainScreenState.currentTopLevelDestination) {
        HOME -> systemUiController.updateStatusBarColor(darkIcons = false)
        STUDIO -> systemUiController.updateStatusBarColor(darkIcons = false)
        else -> systemUiController.updateStatusBarColor(darkIcons = false)
    }

    SharedTransitionLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val sharedTransitionScope = this

        NavHost(
            navController = navHostController,
            startDestination = startDestination,
            modifier = Modifier.fillMaxSize(),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = 350)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = 350)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = 350)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = 350)
                )
            }
        ) {
            pageHome(
                mainScreenState = mainScreenState,
                mainViewModel = mainViewModel,
                geminiAgentViewModel = geminiAgentViewModel,
                foodRecipesViewModel = foodRecipesViewModel
            )
            pageStudio(
                mainScreenState = mainScreenState,
                mainViewModel = mainViewModel,
                todoViewModel = todoViewModel,
                newsViewModel = newsViewModel
            )
            pageGallery(
                mainScreenState = mainScreenState,
                mainViewModel = mainViewModel,
                galleryViewModel = galleryViewModel,
                sharedTransitionScope = sharedTransitionScope,
                onBackClick = navHostController::popBackStack
            )
            pageSettings(
                mainScreenState = mainScreenState,
                mainViewModel = mainViewModel,
                onBackClick = navHostController::popBackStack
            )
            pageAgentChat(
                mainScreenState = mainScreenState,
                mainViewModel = mainViewModel,
                geminiAgentViewModel = geminiAgentViewModel,
                onBackClick = navHostController::popBackStack
            )
            pageFoodRecipeDetail(
                foodRecipesViewModel = foodRecipesViewModel,
                onBackClick = navHostController::popBackStack
            )
            pageNewsDetail(
                onBackClick = navHostController::popBackStack
            )
            pageGalleryDetail(
                onBackClick = navHostController::popBackStack,
                shareTransitionScope = sharedTransitionScope
            )
        }
    }
}