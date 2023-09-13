package io.dev.relic.feature.screens.main.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import io.dev.relic.feature.screens.main.util.MainScreenTopLevelDestination
import io.dev.relic.global.RelicConstants.ComposeUi.DEFAULT_DESC
import io.dev.relic.global.utils.LogUtil
import io.dev.relic.global.utils.ext.NavDestinationExt.isTopLevelDestinationInHierarchy
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainTextColor
import io.dev.relic.ui.theme.mainThemeColor
import io.dev.relic.ui.theme.mainThemeColorAccent

@Composable
fun MainBottomBar(
    currentDestination: NavDestination?,
    destinations: List<MainScreenTopLevelDestination>,
    onNavigateToDestination: (MainScreenTopLevelDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = mainThemeColorAccent),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            destinations.forEach { destination: MainScreenTopLevelDestination ->
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
}

@Composable
private fun RowScope.MainBottomBarItem(
    isSelected: Boolean,
    @DrawableRes selectedIconResId: Int,
    @DrawableRes unselectedIconResId: Int,
    @StringRes labelResId: Int,
    onItemClick: () -> Unit
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
        modifier = Modifier.weight(1F),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.LightGray
        )
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = iconSource,
                contentDescription = DEFAULT_DESC
            )
            Spacer(modifier = Modifier.height(4.dp))
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
        }
    }
}

@Composable
@Preview
private fun MainBottomBarPreview() {
    MainBottomBar(
        destinations = MainScreenTopLevelDestination.values().asList(),
        onNavigateToDestination = {},
        currentDestination = null
    )
}
