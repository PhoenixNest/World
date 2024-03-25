package io.dev.relic.feature.screens.main.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
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
import io.core.ui.theme.mainIconColorLight
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColor
import io.core.ui.theme.mainThemeColorAccent
import io.dev.relic.feature.screens.main.util.MainScreenTopLevelDestination
import io.dev.relic.global.ext.NavDestinationExt.isTopLevelDestinationInHierarchy

@Composable
fun MainRailAppBar(
    destinations: List<MainScreenTopLevelDestination>,
    onNavigateToDestination: (MainScreenTopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(color = mainThemeColor),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.Start
    ) {
        destinations.forEach {
            val itemDecorationModifier = if (it.ordinal == 0) {
                Modifier.statusBarsPadding()
            } else {
                Modifier
            }
            MainRailBarItem(
                isSelected = currentDestination.isTopLevelDestinationInHierarchy(it),
                selectedIconResId = it.selectedIconResId,
                unselectedIconResId = it.unselectedIconResId,
                labelResId = it.labelResId,
                onItemClick = {
                    LogUtil.d("RelicRailBar", "[RailItem] onNavigateTo -> [${it.name}]")
                    onNavigateToDestination(it)
                },
                modifier = itemDecorationModifier
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
    val iconSource = painterResource(
        id = if (isSelected) {
            selectedIconResId
        } else {
            unselectedIconResId
        }
    )

    TextButton(
        onClick = onItemClick,
        modifier = modifier,
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
            Icon(
                painter = iconSource,
                contentDescription = DEFAULT_DESC,
                tint = if (isSelected) {
                    mainThemeColorAccent
                } else {
                    mainIconColorLight
                }
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
private fun MainRailPreview() {
    MainRailAppBar(
        destinations = MainScreenTopLevelDestination.entries,
        onNavigateToDestination = {},
        currentDestination = null
    )
}