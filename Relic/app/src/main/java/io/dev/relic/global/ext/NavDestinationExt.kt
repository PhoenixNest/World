package io.dev.relic.global.ext

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import io.dev.relic.feature.screens.main.util.MainScreenTopLevelDestination

object NavDestinationExt {

    fun NavDestination?.isTopLevelDestinationInHierarchy(
        destination: MainScreenTopLevelDestination
    ): Boolean {
        return this?.hierarchy?.any { navDestination: NavDestination ->
            navDestination.route?.contains(
                other = destination.name.lowercase(),
                ignoreCase = false
            ) ?: false
        } ?: false
    }
}