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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.common.util.LogUtil
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColor
import io.core.ui.theme.mainThemeColorAccent
import io.dev.relic.feature.screens.main.util.MainScreenTopLevelDestination
import io.dev.relic.global.ext.NavDestinationExt.isTopLevelDestinationInHierarchy

@Composable
fun MainBottomBar(
    currentDestination: NavDestination?,
    destinations: List<MainScreenTopLevelDestination>,
    onNavigateToDestination: (MainScreenTopLevelDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = mainThemeColor),
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
                        LogUtil.d("RelicBottomBar", "[BottomItem] onNavigateTo -> [${destination.name}]")
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
    val iconSource = painterResource(
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
                        mainThemeColorAccent
                    } else {
                        mainTextColor
                    },
                    fontFamily = ubuntu
                )
            )
        }
    }
}

@Composable
@Preview
private fun MainBottomBarPreview() {
    MainBottomBar(
        destinations = MainScreenTopLevelDestination.entries,
        onNavigateToDestination = {},
        currentDestination = null
    )
}
