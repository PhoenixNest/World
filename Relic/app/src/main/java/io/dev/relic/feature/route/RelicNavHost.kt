package io.dev.relic.feature.route

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.page.explore.pageExplore
import io.dev.relic.feature.page.hive.pageHive
import io.dev.relic.feature.page.home.pageHome
import io.dev.relic.feature.screen.main.MainScreenState

@Composable
fun MainFeatureNavHost(
    mainScreenState: MainScreenState,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = RelicRoute.HOME
) {
    val mainViewModel: MainViewModel = hiltViewModel()

    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(durationMillis = 350)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(durationMillis = 350)
            )
        }
    ) {
        pageHome(
            mainScreenState = mainScreenState,
            mainViewModel = mainViewModel
        )
        pageExplore(
            mainScreenState = mainScreenState,
            mainViewModel = mainViewModel
        )
        pageHive(
            mainScreenState = mainScreenState,
            mainViewModel = mainViewModel
        )
    }
}