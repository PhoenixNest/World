package io.dev.relic.feature.function.news.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.core.ui.CommonHorizontalIconTextButton
import io.core.ui.theme.RelicFontFamily
import io.core.ui.theme.mainTextColor
import io.dev.relic.R
import io.dev.relic.feature.function.news.util.NewsTopHeadlineCategories

@Composable
fun NewsTabBar(
    currentSelectedTab: Int,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.news_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            maxLines = 1,
            style = TextStyle(
                color = mainTextColor,
                fontSize = 72.sp,
                fontFamily = RelicFontFamily.newsReader,
            )
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemsIndexed(NewsTopHeadlineCategories.entries) { index: Int, item: NewsTopHeadlineCategories ->
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
                mainTextColor
            } else {
                mainTextColor.copy(alpha = 0.3F)
            },
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF282C34)
private fun NewsTabBarPreview() {
    NewsTabBar(
        currentSelectedTab = 0,
        onTabItemClick = { _, _ -> }
    )
}