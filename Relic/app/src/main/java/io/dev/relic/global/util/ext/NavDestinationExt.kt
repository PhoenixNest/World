package io.dev.relic.global.util.ext

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import io.dev.relic.core.route.RelicTopLevelDestination

fun NavDestination?.isTopLevelDestinationInHierarchy(destination: RelicTopLevelDestination): Boolean {
    return this?.hierarchy?.any { navDestination: NavDestination ->
        navDestination.route?.contains(destination.name.lowercase(), true) ?: false
    } ?: false
}