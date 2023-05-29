package io.dev.relic.feature.main

import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.navigation.navOptions
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import io.dev.relic.core.data.network.monitor.NetworkMonitor
import io.dev.relic.feature.main.route.MainFeatureRoute.HiveUnit.routeHivePage
import io.dev.relic.feature.main.route.MainFeatureRoute.HomeUnit.routeHomePage
import io.dev.relic.feature.main.route.MainFeatureRoute.MineUnit.routeMinePage
import io.dev.relic.feature.main.route.MainFeatureTopLevelDestination
import io.dev.relic.feature.main.route.MainFeatureTopLevelDestination.Hive
import io.dev.relic.feature.main.route.MainFeatureTopLevelDestination.Home
import io.dev.relic.feature.main.route.MainFeatureTopLevelDestination.Mine
import io.dev.relic.feature.main.route.MainFeatureTopLevelDestination.values
import io.dev.relic.feature.main.unit.hive.navigateToHiveUnit
import io.dev.relic.feature.main.unit.home.navigateToHomeUnit
import io.dev.relic.feature.main.unit.mine.navigateToMineUnit
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberMainScreenState(
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
            windowSizeClass,
            networkMonitor,
            coroutineScope,
            navHostController
        )
    }
}

@Stable
class MainScreenState(
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

    val currentTopLevelDestination: MainFeatureTopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            routeHomePage -> Home
            routeHivePage -> Hive
            routeMinePage -> Mine
            else -> null
        }

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<MainFeatureTopLevelDestination> = values().asList()

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
    fun navigateToTopLevelDestination(topLevelDestination: MainFeatureTopLevelDestination) {

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
            Hive -> navHostController.navigateToHiveUnit(navOptions = topLevelNavOptions)
            Mine -> navHostController.navigateToMineUnit(navOptions = topLevelNavOptions)
        }
    }

}