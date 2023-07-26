package io.dev.relic.global.utils.ext

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import io.dev.relic.feature.route.MainFeatureTopLevelDestination

object NavDestinationExt {

    fun NavDestination?.isTopLevelDestinationInHierarchy(
        destination: MainFeatureTopLevelDestination
    ): Boolean {
        return this?.hierarchy?.any { navDestination: NavDestination ->
            navDestination.route?.contains(
                other = destination.name.lowercase(),
                ignoreCase = false
            ) ?: false
        } ?: false
    }
}