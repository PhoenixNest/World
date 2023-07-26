package io.dev.relic.feature.route.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import io.dev.relic.feature.route.MainFeatureTopLevelDestination
import io.dev.relic.global.RelicConstants.ComposeUi.DEFAULT_DESC
import io.dev.relic.global.utils.LogUtil
import io.dev.relic.global.utils.ext.NavDestinationExt.isTopLevelDestinationInHierarchy
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainTextColor
import io.dev.relic.ui.theme.mainTextColorLight

@Composable
fun MainRailAppBar(
    destinations: List<MainFeatureTopLevelDestination>,
    onNavigateToDestination: (MainFeatureTopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
) {
    NavigationRail(modifier = modifier) {
        destinations.forEach { destination: MainFeatureTopLevelDestination ->
            MainRailBarItem(
                isSelected = currentDestination.isTopLevelDestinationInHierarchy(destination),
                selectedIcon = destination.selectedIconResId,
                unselectedIcon = destination.unselectedIconResId,
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
    @DrawableRes selectedIcon: Int,
    @DrawableRes unselectedIcon: Int,
    @StringRes labelResId: Int,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRailItem(
        selected = isSelected,
        onClick = onItemClick,
        icon = {
            Image(
                painter = painterResource(id = if (isSelected) selectedIcon else unselectedIcon),
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
                        mainTextColorLight
                    },
                    fontSize = if (isSelected) {
                        14.sp
                    } else {
                        12.sp
                    },
                    fontWeight = if (isSelected) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
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
        destinations = MainFeatureTopLevelDestination.values().asList(),
        onNavigateToDestination = {},
        currentDestination = null
    )
}