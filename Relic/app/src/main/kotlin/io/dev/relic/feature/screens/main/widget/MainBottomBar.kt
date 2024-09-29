package io.dev.relic.feature.screens.main.widget

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.common.util.LogUtil
import io.dev.relic.feature.screens.main.util.MainScreenTopLevelDestination
import io.dev.relic.global.ext.NavDestinationExt.isTopLevelDestinationInHierarchy

@Composable
fun MaterialMainBottomBar(
    currentDestination: NavDestination?,
    destinations: List<MainScreenTopLevelDestination>,
    onNavigateToDestination: (MainScreenTopLevelDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        tonalElevation = 5.dp
    ) {
        destinations.forEach { destination: MainScreenTopLevelDestination ->
            val isSelected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    LogUtil.d("RelicBottomBar", "[BottomItem] onNavigateTo -> [${destination.name}]")
                    onNavigateToDestination.invoke(destination)
                },
                icon = {
                    val iconResId = if (isSelected) {
                        destination.selectedIconResId
                    } else {
                        destination.unselectedIconResId
                    }

                    Icon(
                        painter = painterResource(iconResId),
                        contentDescription = DEFAULT_DESC
                    )
                },
                label = {
                    Text(
                        text = stringResource(destination.labelResId),
                        style = if (isSelected) {
                            MaterialTheme.typography.labelMedium
                        } else {
                            MaterialTheme.typography.labelSmall
                        }
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                    unselectedIconColor = MaterialTheme.colorScheme.tertiary,
                    unselectedTextColor = MaterialTheme.colorScheme.tertiary
                )
            )
        }
    }
}

@Composable
@Preview
private fun MainBottomBarPreview() {
    MaterialMainBottomBar(
        destinations = MainScreenTopLevelDestination.entries,
        onNavigateToDestination = {},
        currentDestination = null
    )
}
