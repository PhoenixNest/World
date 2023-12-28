package io.dev.relic.feature.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.core.ui.ext.SystemUiControllerExt.updateStatusBarColor
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.pages.detail.news.pageNewsDetail
import io.dev.relic.feature.pages.explore.pageExplore
import io.dev.relic.feature.pages.hive.pageHive
import io.dev.relic.feature.pages.home.pageHome
import io.dev.relic.feature.screens.main.MainScreenState
import io.dev.relic.feature.screens.main.util.MainScreenTopLevelDestination

@Composable
fun MainFeatureNavHost(
    mainScreenState: MainScreenState,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = RelicRoute.BASE_ROUTE
) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val systemUiController: SystemUiController = rememberSystemUiController()

    when (mainScreenState.currentTopLevelDestination) {
        MainScreenTopLevelDestination.Home -> {
            systemUiController.updateStatusBarColor(darkIcons = false)
        }

        MainScreenTopLevelDestination.Explore -> {
            systemUiController.updateStatusBarColor(darkIcons = true)
        }

        MainScreenTopLevelDestination.Hive -> {
            systemUiController.updateStatusBarColor(darkIcons = false)
        }

        else -> {
            systemUiController.updateStatusBarColor(darkIcons = false)
        }
    }

    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier,
        /*enterTransition = {
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
        }*/
    ) {
        // Main unit
        pageHome(mainScreenState, mainViewModel)
        pageExplore(mainScreenState, mainViewModel)
        pageHive(mainScreenState, mainViewModel)

        // Detail
        pageNewsDetail(navHostController::popBackStack)
    }
}