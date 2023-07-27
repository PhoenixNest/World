package io.dev.relic.global.utils.ext

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import io.dev.relic.feature.screen.main.MainScreenTopLevelDestination

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