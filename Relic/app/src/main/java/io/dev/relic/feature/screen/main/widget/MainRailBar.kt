package io.dev.relic.feature.screen.main.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import io.dev.relic.feature.screen.main.util.MainScreenTopLevelDestination
import io.dev.relic.global.RelicConstants.ComposeUi.DEFAULT_DESC
import io.dev.relic.global.utils.LogUtil
import io.dev.relic.global.utils.ext.NavDestinationExt.isTopLevelDestinationInHierarchy
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainTextColor
import io.dev.relic.ui.theme.mainThemeColor

@Composable
fun MainRailAppBar(
    destinations: List<MainScreenTopLevelDestination>,
    onNavigateToDestination: (MainScreenTopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
) {
    NavigationRail(modifier = modifier) {
        destinations.forEach { destination: MainScreenTopLevelDestination ->
            MainRailBarItem(
                isSelected = currentDestination.isTopLevelDestinationInHierarchy(destination),
                selectedIconResId = destination.selectedIconResId,
                unselectedIconResId = destination.unselectedIconResId,
                labelResId = destination.labelResId,
                onItemClick = {
                    LogUtil.debug("RelicRailBar", "[RailItem] onNavigateTo -> [${destination.name}]")
                    onNavigateToDestination(destination)
                }
            )
        }
    }
}

@Composable
private fun MainRailBarItem(
    isSelected: Boolean,
    @DrawableRes selectedIconResId: Int,
    @DrawableRes unselectedIconResId: Int,
    @StringRes labelResId: Int,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val iconSource: Painter = painterResource(
        id = if (isSelected) {
            selectedIconResId
        } else {
            unselectedIconResId
        }
    )

    NavigationRailItem(
        selected = isSelected,
        onClick = onItemClick,
        icon = {
            Image(
                painter = iconSource,
                contentDescription = DEFAULT_DESC
            )
        },
        modifier = modifier,
        label = {
            Text(
                text = stringResource(id = labelResId),
                style = TextStyle(
                    color = if (isSelected) {
                        mainTextColor
                    } else {
                        mainThemeColor
                    },
                    fontFamily = RelicFontFamily.ubuntu
                )
            )
        },
        enabled = true,
        alwaysShowLabel = true
    )
}

@Composable
@Preview
private fun MainRailPreview() {
    MainRailAppBar(
        destinations = MainScreenTopLevelDestination.values().asList(),
        onNavigateToDestination = {},
        currentDestination = null
    )
}