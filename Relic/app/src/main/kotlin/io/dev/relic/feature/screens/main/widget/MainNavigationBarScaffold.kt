package io.dev.relic.feature.screens.main.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun MainNavigationBarScaffold(
    currentDestination: NavDestination?,
    destinations: List<AppTopLevelDestinations>,
    onItemClick: (nextDestinations: AppTopLevelDestinations) -> Unit,
    isShowBottomBar: Boolean,
    isShowRailBar: Boolean,
    snackBarHostState: SnackbarHostState,
    content: @Composable (scaffoldPadding: PaddingValues) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(
            left = 0,
            top = 0,
            right = 0,
            bottom = 0
        ),
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            MainNavigationRailBar(
                isVisible = isShowRailBar,
                currentDestination = currentDestination,
                destinations = destinations,
                onItemClick = onItemClick
            )
            Box(modifier = Modifier.fillMaxSize()) {
                content.invoke(paddingValues)
                MainNavigationBottomBar(
                    isVisible = isShowBottomBar,
                    currentDestination = currentDestination,
                    destinations = destinations,
                    onItemClick = onItemClick,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}

/* ======================== Bottom bar ======================== */

@Composable
private fun MainNavigationBottomBar(
    isVisible: Boolean,
    currentDestination: NavDestination?,
    destinations: List<AppTopLevelDestinations>,
    onItemClick: (nextDestinations: AppTopLevelDestinations) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        modifier = modifier.fillMaxWidth(),
        enter = slideInVertically(
            animationSpec = tween(
                durationMillis = 400,
                easing = LinearOutSlowInEasing
            ),
            initialOffsetY = { fullHeight ->
                2 * fullHeight
            }
        ),
        exit = slideOutVertically(
            animationSpec = tween(
                durationMillis = 400,
                easing = LinearOutSlowInEasing
            ),
            targetOffsetY = { fullHeight ->
                fullHeight
            }
        ),
        label = "AnimatedVisibility_MainNavigationBottomBar"
    ) {
        MainNavigationBottomBar(
            currentDestination = currentDestination,
            destinations = destinations,
            onItemClick = onItemClick
        )
    }
}

@Composable
private fun MainNavigationBottomBar(
    currentDestination: NavDestination?,
    destinations: List<AppTopLevelDestinations>,
    onItemClick: (nextDestinations: AppTopLevelDestinations) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        modifier = Modifier.fillMaxWidth(),
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        destinations.forEach { destination ->
            val isSelected = currentDestination
                .isTopLevelDestinationInHierarchy(destination)

            val iconResId = if (isSelected) {
                destination.selectedIconResId
            } else {
                destination.unselectedIconResId
            }

            val labelResId = destination.labelResId

            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick.invoke(destination) },
                icon = { NavigationBarIcon(iconResId) },
                label = { NavigationBarText(labelResId) }
            )
        }
    }
}

/* ======================== Rail bar ======================== */

@Composable
private fun MainNavigationRailBar(
    isVisible: Boolean,
    currentDestination: NavDestination?,
    destinations: List<AppTopLevelDestinations>,
    onItemClick: (nextDestinations: AppTopLevelDestinations) -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        modifier = Modifier.fillMaxHeight(),
        /*enter = slideInHorizontally(
            animationSpec = tween(
                durationMillis = 400,
                easing = LinearOutSlowInEasing
            ),
            initialOffsetX = { fullWidth ->
                -2 * fullWidth
            }
        ),
        exit = slideOutHorizontally(
            animationSpec = tween(
                durationMillis = 400,
                easing = FastOutLinearInEasing
            ),
            targetOffsetX = { fullWidth ->
                -fullWidth
            }
        ),*/
        label = "AnimatedVisibility_MainNavigationRailBar"
    ) {
        MainNavigationRailBar(
            currentDestination = currentDestination,
            destinations = destinations,
            onItemClick = onItemClick
        )
    }
}

@Composable
private fun MainNavigationRailBar(
    currentDestination: NavDestination?,
    destinations: List<AppTopLevelDestinations>,
    onItemClick: (nextDestinations: AppTopLevelDestinations) -> Unit,
) {
    NavigationRail(
        modifier = Modifier
            .fillMaxHeight()
            .navigationBarsPadding(),
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        destinations.forEach { destination ->
            val isSelected = currentDestination
                .isTopLevelDestinationInHierarchy(destination)

            val iconResId = if (isSelected) {
                destination.selectedIconResId
            } else {
                destination.unselectedIconResId
            }

            val labelResId = destination.labelResId

            NavigationRailItem(
                selected = isSelected,
                onClick = { onItemClick.invoke(destination) },
                icon = { NavigationBarIcon(iconResId) },
                label = { NavigationBarText(labelResId) }
            )
        }
    }
}

/* ======================== Inner component ======================== */

@Composable
private fun NavigationBarIcon(@DrawableRes iconResId: Int) {
    Icon(
        painter = painterResource(iconResId),
        contentDescription = DEFAULT_DESC
    )
}

@Composable
private fun NavigationBarText(@StringRes labelResId: Int) {
    Text(
        text = stringResource(labelResId),
        color = MaterialTheme.colorScheme.onSurface,
        fontFamily = googleSans
    )
}

@Composable
@Preview(showBackground = true)
private fun MainNavigationBottomBarPreview() {
    RelicAppTheme {
        MainNavigationBottomBar(
            currentDestination = null,
            destinations = AppTopLevelDestinations.entries.toList(),
            onItemClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun MainNavigationRailBarPreview() {
    RelicAppTheme {
        MainNavigationRailBar(
            currentDestination = null,
            destinations = AppTopLevelDestinations.entries.toList(),
            onItemClick = {}
        )
    }
}