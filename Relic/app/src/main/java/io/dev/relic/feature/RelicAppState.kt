package io.dev.relic.feature

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import io.dev.relic.core.module.data.network.monitor.NetworkMonitor
import io.dev.relic.core.route.RelicAppRoute.HomeUnit.routeHomePage
import io.dev.relic.core.route.RelicAppRoute.HubUnit.routeHubPage
import io.dev.relic.core.route.RelicTopLevelDestination
import io.dev.relic.core.route.RelicTopLevelDestination.Home
import io.dev.relic.core.route.RelicTopLevelDestination.Hub
import io.dev.relic.core.route.RelicTopLevelDestination.values
import io.dev.relic.feature.main.home.navigateToHomeUnit
import io.dev.relic.feature.main.hub.navigateToHubUnit
import io.dev.relic.feature.main.todo.navigateToTodoPage
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberRelicAppState(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navHostController: NavHostController = rememberNavController()
): RelicAppState {
    return remember(
        windowSizeClass,
        networkMonitor,
        coroutineScope,
        navHostController
    ) {
        RelicAppState(
            windowSizeClass,
            networkMonitor,
            coroutineScope,
            navHostController
        )
    }
}

@Stable
class RelicAppState(
    val windowSizeClass: WindowSizeClass,
    val networkMonitor: NetworkMonitor,
    val coroutineScope: CoroutineScope,
    val navHostController: NavHostController
) {

    val currentDestination: NavDestination?
        @Composable get() = navHostController
            .currentBackStackEntryAsState()
            .value
            ?.destination

    val currentTopLevelDestination: RelicTopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            routeHomePage -> Home
            routeHubPage -> Hub
            else -> null
        }

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<RelicTopLevelDestination> = values().asList()

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowRailBar: Boolean
        get() = !shouldShowBottomBar

    var shouldShowPrivacyDialog: Boolean by mutableStateOf(value = false)
        private set

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination   The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: RelicTopLevelDestination) {

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
            Home -> navHostController.navigateToHomeUnit(navOptions = topLevelNavOptions)
            Hub -> navHostController.navigateToHubUnit(navOptions = topLevelNavOptions)
        }
    }

    fun navigateToTodoUnit() {
        val navOptions: NavOptions = navOptions {
            // Avoid multiple copies of the same destination when re-selecting the same item
            launchSingleTop = true
            // Restore state when re-selecting a previously selected item
            restoreState = true
        }
        navHostController.navigateToTodoPage(navOptions = navOptions)
    }

}