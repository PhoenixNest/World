package io.dev.relic.core.route.widget

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import io.dev.relic.core.route.RelicTopLevelDestination
import io.dev.relic.global.util.LogUtil
import io.dev.relic.global.util.ext.isTopLevelDestinationInHierarchy

@Composable
fun RelicRailAppBar(
    destinations: List<RelicTopLevelDestination>,
    onNavigateToDestination: (RelicTopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
) {
    NavigationRail(modifier = modifier) {
        destinations.forEach { destination: RelicTopLevelDestination ->
            val isSelected: Boolean = currentDestination.isTopLevelDestinationInHierarchy(destination)
            RelicNavigationRailItem(
                isSelected = isSelected,
                modifier = modifier,
                selectedIcon = { destination.selectedIcon },
                unselectedIcon = { destination.unselectedIcon },
                label = { Text(text = stringResource(id = destination.labelResId)) }
            ) {
                LogUtil.debug("RelicAppRailBar", "Rail bar selected: ${destination.name}")
                // Navigate to the next page.
                onNavigateToDestination(destination)
            }
        }
    }
}

@Composable
fun ColumnScope.RelicNavigationRailItem(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit,
    unselectedIcon: @Composable () -> Unit = selectedIcon,
    label: @Composable() (() -> Unit)? = null,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    onClick: () -> Unit = {}
) {
    NavigationRailItem(
        selected = isSelected,
        onClick = onClick,
        label = label,
        modifier = modifier,
        icon = if (isSelected) selectedIcon else unselectedIcon,
        enabled = enabled,
        alwaysShowLabel = alwaysShowLabel
    )
}