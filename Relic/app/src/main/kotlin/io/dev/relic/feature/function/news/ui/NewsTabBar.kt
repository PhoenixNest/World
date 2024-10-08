package io.dev.relic.feature.function.news.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.CommonHorizontalIconTextButton
import io.dev.relic.feature.function.news.util.NewsTopHeadlineCategories

@Composable
fun NewsTabBar(
    currentSelectedTab: Int,
    lazyListState: LazyListState,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        state = lazyListState,
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.Start
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(NewsTopHeadlineCategories.entries) { index, item ->
            val tabLabel: String = stringResource(id = item.tabLabelResId)
            val itemDecorationModifier: Modifier = Modifier.padding(
                start = if (index == 0) 16.dp else 0.dp,
                end = if (index == NewsTopHeadlineCategories.entries.size - 1) 16.dp else 0.dp
            )
            NewsTabBarItem(
                isSelected = (currentSelectedTab == index),
                iconResId = item.iconResId,
                tabLabelResId = item.tabLabelResId,
                onTabClick = { onTabItemClick.invoke(index, tabLabel) },
                modifier = itemDecorationModifier
            )
        }
    }
}

@Composable
private fun NewsTabBarItem(
    isSelected: Boolean,
    @DrawableRes iconResId: Int,
    @StringRes tabLabelResId: Int,
    onTabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        CommonHorizontalIconTextButton(
            iconResId = iconResId,
            labelResId = tabLabelResId,
            onClick = onTabClick,
            textColor = if (isSelected) {
                MaterialTheme.colorScheme.onTertiary
            } else {
                MaterialTheme.colorScheme.tertiary
            },
            shape = RoundedCornerShape(16.dp),
            backgroundColor = if (isSelected) {
                MaterialTheme.colorScheme.tertiary
            } else {
                Color.Transparent
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun NewsTabBarPreview() {
    NewsTabBar(
        currentSelectedTab = 0,
        onTabItemClick = { _, _ -> },
        lazyListState = rememberLazyListState()
    )
}