package io.dev.relic.feature.screens.main

import android.os.Bundle
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import io.dev.relic.feature.pages.explore.navigateToExplorePage
import io.dev.relic.feature.pages.hive.navigateToHivePage
import io.dev.relic.feature.pages.home.navigateToHomePage
import io.dev.relic.feature.route.RelicRoute
import io.dev.relic.feature.screens.main.util.MainScreenTopLevelDestination
import io.module.core.network.monitor.NetworkMonitor
import io.module.core.network.monitor.NetworkStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberMainScreenState(
    savedInstanceState: Bundle?,
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navHostController: NavHostController = rememberAnimatedNavController()
): MainScreenState {
    return remember(
        keys = arrayOf(
            windowSizeClass,
            networkMonitor,
            coroutineScope,
            navHostController
        )
    ) {
        MainScreenState(
            saveInstanceState = savedInstanceState,
            windowSizeClass = windowSizeClass,
            networkMonitor = networkMonitor,
            coroutineScope = coroutineScope,
            navHostController = navHostController
        )
    }
}

@Stable
class MainScreenState(
    val saveInstanceState: Bundle?,
    val windowSizeClass: WindowSizeClass,
    val networkMonitor: NetworkMonitor,
    val coroutineScope: CoroutineScope,
    val navHostController: NavHostController
) {

    val currentNetworkStatus: StateFlow<NetworkStatus> = networkMonitor.observe().stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5 * 1000),
        initialValue = NetworkStatus.Available
    )

    val currentDestination: NavDestination?
        @Composable get() = navHostController
            .currentBackStackEntryAsState()
            .value
            ?.destination

    val currentTopLevelDestination: MainScreenTopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            RelicRoute.HOME -> MainScreenTopLevelDestination.Home
            RelicRoute.EXPLORE -> MainScreenTopLevelDestination.Explore
            RelicRoute.HIVE -> MainScreenTopLevelDestination.Hive
            else -> null
        }

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<MainScreenTopLevelDestination> = MainScreenTopLevelDestination.entries

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowRailBar: Boolean
        get() = !shouldShowBottomBar

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination   The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: MainScreenTopLevelDestination) {

        val topLevelNavOptions: NavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when re-selecting the same item
            launchSingleTop = true
            // Restore state when re-selecting a previously selected item
            restoreState = true
        }

        when (topLevelDestination) {
            MainScreenTopLevelDestination.Home -> {
                navHostController.navigateToHomePage(navOptions = topLevelNavOptions)
            }

            MainScreenTopLevelDestination.Explore -> {
                navHostController.navigateToExplorePage(navOptions = topLevelNavOptions)
            }

            MainScreenTopLevelDestination.Hive -> {
                navHostController.navigateToHivePage(navOptions = topLevelNavOptions)
            }
        }
    }

}