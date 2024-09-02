package io.dev.relic.feature.screens.main

import android.os.Bundle
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import io.core.network.monitor.NetworkMonitor
import io.core.network.monitor.NetworkStatus
import io.dev.relic.feature.pages.gallery.navigateToGalleryPage
import io.dev.relic.feature.pages.home.navigateToHomePage
import io.dev.relic.feature.pages.studio.navigateToStudioPage
import io.dev.relic.feature.route.RelicRoute
import io.dev.relic.feature.screens.main.util.MainScreenTopLevelDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberMainScreenState(
    savedInstanceState: Bundle?,
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navHostController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
): MainScreenState {
    return remember(
        keys = arrayOf(
            windowSizeClass,
            networkMonitor,
            coroutineScope,
            navHostController,
            drawerState
        )
    ) {
        MainScreenState(
            saveInstanceState = savedInstanceState,
            windowSizeClass = windowSizeClass,
            networkMonitor = networkMonitor,
            coroutineScope = coroutineScope,
            navHostController = navHostController,
            drawerState = drawerState
        )
    }
}

/**
 * Indicate the current state of main screen.
 *
 * @param saveInstanceState
 * @param windowSizeClass
 * @param networkMonitor
 * @param navHostController
 * @param drawerState
 * */
@Stable
class MainScreenState(
    val saveInstanceState: Bundle?,
    val windowSizeClass: WindowSizeClass,
    val networkMonitor: NetworkMonitor,
    val coroutineScope: CoroutineScope,
    val navHostController: NavHostController,
    val drawerState: DrawerState
) {

    /**
     * Indicate the current network status of device.
     * */
    val currentNetworkStatus = networkMonitor.observe().stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5 * 1000),
        initialValue = NetworkStatus.AVAILABLE
    )

    /**
     * Indicate the current destination of nav host.
     * */
    val currentDestination
        @Composable get() = navHostController
            .currentBackStackEntryAsState()
            .value
            ?.destination

    /**
     * Indicate the current destination of nav host.
     * */
    val currentTopLevelDestination
        @Composable get() = when (currentDestination?.route) {
            RelicRoute.HOME -> MainScreenTopLevelDestination.HOME
            RelicRoute.STUDIO -> MainScreenTopLevelDestination.STUDIO
            RelicRoute.GALLERY -> MainScreenTopLevelDestination.GALLERY
            else -> null
        }

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations = MainScreenTopLevelDestination.entries

    /**
     * Check the current screen size and toggle the visibility of bottom bar.
     * */
    val shouldShowBottomBar
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    /**
     * Check the current screen size and toggle the visibility of rail bar.
     * */
    val shouldShowRailBar
        get() = !shouldShowBottomBar

    /**
     * Check the current destination of nav host and toggle the gesture of main screen drawer.
     * */
    val isEnableDrawerGesture
        @Composable get() = (currentTopLevelDestination == MainScreenTopLevelDestination.HOME)

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination   The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: MainScreenTopLevelDestination) {

        val topLevelNavOptions = navOptions {
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
            MainScreenTopLevelDestination.HOME -> {
                navHostController.navigateToHomePage(topLevelNavOptions)
            }

            MainScreenTopLevelDestination.STUDIO -> {
                navHostController.navigateToStudioPage(topLevelNavOptions)
            }

            MainScreenTopLevelDestination.GALLERY -> {
                navHostController.navigateToGalleryPage(topLevelNavOptions)
            }
        }
    }
}