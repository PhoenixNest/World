package io.dev.relic.feature.screens.main.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import io.common.util.LogUtil
import io.core.ui.CommonVerticalIconTextButton
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
    val iconResId = if (isSelected) {
        selectedIconResId
    } else {
        unselectedIconResId
    }

    val iconColor = if (isSelected) {
        mainThemeColorAccent
    } else {
        mainIconColorLight
    }

    val textColor = if (isSelected) {
        mainThemeColorAccent
    } else {
        mainTextColor
    }

    CommonVerticalIconTextButton(
        iconResId = iconResId,
        labelResId = labelResId,
        onClick = onItemClick,
        containerModifier = modifier,
        iconColor = iconColor,
        textColor = textColor,
        shape = RoundedCornerShape(0.dp)
    )
}

@Composable
@Preview(showSystemUi = true)
private fun MainRailPreview() {
    MainRailAppBar(
        destinations = MainScreenTopLevelDestination.entries,
        onNavigateToDestination = {},
        currentDestination = null
    )
}