package io.dev.relic.feature.screens.main.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.RelicAppTheme
import io.core.ui.theme.RelicFontFamily.googleSans
import io.dev.relic.feature.screens.main.util.AppTopLevelDestinations
import io.dev.relic.global.ext.NavDestinationExt.isTopLevelDestinationInHierarchy

@Composable
fun MainNavigationSuiteScaffold(
    currentDestination: NavDestination?,
    destinations: List<AppTopLevelDestinations>,
    onItemClick: (nextDestinations: AppTopLevelDestinations) -> Unit,
    snackBarHostState: SnackbarHostState,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    content: @Composable (scaffoldPadding: PaddingValues) -> Unit
) {
    val layoutType = NavigationSuiteScaffoldDefaults
        .calculateFromAdaptiveInfo(windowAdaptiveInfo)

    val navigationSuiteItemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            destinations.forEach { destination ->
                val isSelected = currentDestination
                    .isTopLevelDestinationInHierarchy(destination)

                val iconResId = if (isSelected) {
                    destination.selectedIconResId
                } else {
                    destination.unselectedIconResId
                }

                val labelResId = destination.labelResId

                item(
                    selected = isSelected,
                    onClick = { onItemClick.invoke(destination) },
                    icon = { NavigationSuitIcon(iconResId) },
                    label = { NavigationSuitText(labelResId) },
                    colors = navigationSuiteItemColors
                )
            }
        },
        modifier = Modifier.fillMaxSize(),
        layoutType = layoutType,
        containerColor = Color.Transparent,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            navigationRailContainerColor = Color.Transparent
        )
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { SnackbarHost(snackBarHostState) }
        ) { paddingValues ->
            content.invoke(paddingValues)
        }
    }
}

@Composable
private fun NavigationSuitIcon(@DrawableRes iconResId: Int) {
    Icon(
        painter = painterResource(iconResId),
        contentDescription = DEFAULT_DESC
    )
}

@Composable
private fun NavigationSuitText(@StringRes labelResId: Int) {
    Text(
        text = stringResource(labelResId),
        color = MaterialTheme.colorScheme.onSurface,
        fontFamily = googleSans
    )
}

@Composable
@Preview(showBackground = true)
private fun MainNavigationSuiteScaffoldHorizontalPreview() {
    RelicAppTheme {
        MainNavigationSuiteScaffold(
            currentDestination = null,
            destinations = AppTopLevelDestinations.entries.toList(),
            onItemClick = {},
            snackBarHostState = SnackbarHostState(),
            content = {}
        )
    }
}

@Composable
@Preview(showBackground = true, device = "id:pixel_fold")
private fun MainNavigationSuiteScaffoldVerticalPreview() {
    RelicAppTheme {
        MainNavigationSuiteScaffold(
            currentDestination = null,
            destinations = AppTopLevelDestinations.entries.toList(),
            onItemClick = {},
            snackBarHostState = SnackbarHostState(),
            content = {}
        )
    }
}