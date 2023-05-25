package io.dev.relic.feature.main.route.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import io.dev.relic.feature.main.route.MainFeatureTopLevelDestination
import io.dev.relic.global.RelicConstants.ComposeUi.DEFAULT_DESC
import io.dev.relic.global.utils.LogUtil
import io.dev.relic.global.utils.ext.NavDestinationExt.isTopLevelDestinationInHierarchy
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainTextColor
import io.dev.relic.ui.theme.mainTextColorLight

@Composable
fun MainBottomBar(
    destinations: List<MainFeatureTopLevelDestination>,
    onNavigateToDestination: (MainFeatureTopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        destinations.forEach { destination: MainFeatureTopLevelDestination ->
            MainBottomBarItem(
                isSelected = currentDestination.isTopLevelDestinationInHierarchy(destination),
                selectedIconResId = destination.selectedIconResId,
                unselectedIconResId = destination.unselectedIconResId,
                labelResId = destination.labelResId,
                onItemClick = {
                    LogUtil.debug("RelicBottomBar", "[BottomItem] onNavigateTo -> [${destination.name}]")
                    onNavigateToDestination.invoke(destination)
                }
            )
        }
    }
}

@Composable
private fun RowScope.MainBottomBarItem(
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

    TextButton(
        onClick = onItemClick,
        modifier = modifier.weight(1F)
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = iconSource,
                contentDescription = DEFAULT_DESC
            )
            Spacer(modifier = modifier.height(4.dp))
            Text(
                text = stringResource(id = labelResId),
                style = TextStyle(
                    color = if (isSelected) {
                        mainTextColor
                    } else {
                        mainTextColorLight
                    },
                    fontSize = if (isSelected) {
                        16.sp
                    } else {
                        14.sp
                    },
                    fontWeight = if (isSelected) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    },
                    fontFamily = RelicFontFamily.ubuntu
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun MainBottomBarPreview() {
    MainBottomBar(
        destinations = MainFeatureTopLevelDestination.values().asList(),
        onNavigateToDestination = {},
        currentDestination = null
    )
}
