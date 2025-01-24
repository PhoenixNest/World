package io.dev.relic.feature.route

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import io.dev.relic.feature.pages.agent.pageAgentChat
import io.dev.relic.feature.pages.detail.food_recipe.pageFoodRecipeDetail
import io.dev.relic.feature.pages.detail.gallery.pageGalleryDetail
import io.dev.relic.feature.pages.detail.news.pageNewsDetail
import io.dev.relic.feature.pages.gallery.pageGallery
import io.dev.relic.feature.pages.home.pageHome
import io.dev.relic.feature.pages.settings.pageSettings
import io.dev.relic.feature.pages.studio.pageStudio
import io.dev.relic.feature.pages.todo.pageTodo
import io.dev.relic.feature.route.RelicRoute.START_DESTINATION
import io.dev.relic.feature.screens.main.MainScreenState

var LocalNavHostController = staticCompositionLocalOf<NavHostController> {
    error("Error, couldn't provider controller.")
}

/**
 * Main Screen navigation route host
 *
 * Reference docs:
 *
 * - [Shared elements with Navigation Compose](https://developer.android.google.cn/develop/ui/compose/animation/shared-elements/navigation#predictive-back)
 * - [Shared Element Transitions in Compose](https://developer.android.google.cn/develop/ui/compose/animation/shared-elements)
 *
 * @param mainScreenState
 * @param startDestination              The journey begins from here
 * */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainFeatureNavHost(
    mainScreenState: MainScreenState,
    startDestination: String = START_DESTINATION,
    modifier: Modifier = Modifier
) {
    val navHostController = mainScreenState.navHostController

    SharedTransitionLayout(
        modifier = modifier.fillMaxSize()
    ) {
        NavHost(
            navController = mainScreenState.navHostController,
            startDestination = startDestination,
            modifier = Modifier.fillMaxSize(),
            enterTransition = {
                fadeIn()
            },
            exitTransition = {
                fadeOut()
            },
            popEnterTransition = {
                fadeIn()
            },
            popExitTransition = {
                fadeOut()
            }
        ) {
            // Top Level
            pageHome(mainScreenState = mainScreenState)
            pageStudio(mainScreenState = mainScreenState)
            pageGallery(
                mainScreenState = mainScreenState,
                sharedTransitionScope = this@SharedTransitionLayout
            )
            pageSettings(onBackClick = navHostController::popBackStack)

            // Inner function
            pageAgentChat(onBackClick = navHostController::popBackStack)
            pageTodo(
                mainScreenState = mainScreenState,
                sharedTransitionScope = this@SharedTransitionLayout,
                onBackClick = navHostController::popBackStack
            )

            // Detail
            pageFoodRecipeDetail(onBackClick = navHostController::popBackStack)
            pageNewsDetail(onBackClick = navHostController::popBackStack)
            pageGalleryDetail(
                shareTransitionScope = this@SharedTransitionLayout,
                onBackClick = navHostController::popBackStack
            )
        }
    }
}