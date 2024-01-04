package io.dev.relic.feature.pages.explore.widget.bottom_sheet

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.CommonVerticalIconTextButton
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainTextColorDark
import io.core.ui.theme.mainThemeColorAccent
import io.dev.relic.feature.pages.explore.util.ExploreBottomTabs

@Composable
fun ExploreBottomSheetTabBar(
    currentSelectedTab: Int,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(ExploreBottomTabs.entries) { index, item ->
            val tabLabel = stringResource(id = item.tabLabelResId)
            val itemDecorationModifier = Modifier.padding(
                start = if (index == 0) 16.dp else 0.dp,
                end = if (index == ExploreBottomTabs.entries.size - 1) 16.dp else 0.dp
            )
            ExploreBottomSheetTabItem(
                isSelected = currentSelectedTab == index,
                iconResId = item.iconResId,
                tabLabelResId = item.tabLabelResId,
                onTabClick = { onTabItemClick.invoke(index, tabLabel) },
                modifier = itemDecorationModifier
            )
        }
    }
}

@Composable
private fun ExploreBottomSheetTabItem(
    isSelected: Boolean,
    @DrawableRes iconResId: Int,
    @StringRes tabLabelResId: Int,
    onTabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        CommonVerticalIconTextButton(
            iconResId = iconResId,
            labelResId = tabLabelResId,
            onClick = onTabClick,
            backgroundColor = if (isSelected) {
                mainThemeColorAccent
            } else {
                Color.Transparent
            },
            textColor = if (isSelected) {
                mainTextColor
            } else {
                mainTextColorDark
            },
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ExploreBottomSheetTabPreview() {
    ExploreBottomSheetTabBar(
        currentSelectedTab = 0,
        onTabItemClick = { _, _ -> }
    )
}