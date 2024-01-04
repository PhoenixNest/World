package io.dev.relic.feature.function.food_recipes.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.CommonVerticalIconTextButton
import io.core.ui.theme.RelicFontFamily
import io.core.ui.theme.mainBackgroundColorLight
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainTextColorDark
import io.core.ui.theme.mainThemeColorAccent
import io.dev.relic.R
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesSimpleCategories

@Composable
fun FoodRecipesTabBar(
    currentSelectedTab: Int,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    lazyListState: LazyListState
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(104.dp)
                .align(Alignment.BottomCenter),
            backgroundColor = mainBackgroundColorLight,
            shape = RoundedCornerShape(16.dp)
        ) {
            LazyRow(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                state = lazyListState,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.Start
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(FoodRecipesSimpleCategories.entries) { index: Int, item: FoodRecipesSimpleCategories ->
                    val tabLabel: String = stringResource(id = item.tabLabelResId)
                    val itemDecorationModifier: Modifier = Modifier.padding(
                        start = if (index == 0) 16.dp else 0.dp,
                        end = if (index == FoodRecipesSimpleCategories.entries.size - 1) 16.dp else 0.dp
                    )
                    FoodRecipesTabItem(
                        isSelected = (currentSelectedTab == index),
                        iconResId = item.iconResId,
                        tabLabelResId = item.tabLabelResId,
                        onTabClick = { onTabItemClick.invoke(index, tabLabel) },
                        modifier = itemDecorationModifier
                    )
                }
            }
        }
        Card(
            modifier = Modifier.padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = mainThemeColorAccent
        ) {
            Text(
                text = stringResource(R.string.food_recipes_card_title),
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
                style = TextStyle(
                    color = mainTextColor,
                    fontFamily = RelicFontFamily.ubuntu,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
        }
    }

}

@Composable
private fun FoodRecipesTabItem(
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
private fun FoodRecipesTabBarPreview() {
    FoodRecipesTabBar(
        currentSelectedTab = 0,
        onTabItemClick = { _, _ -> },
        lazyListState = rememberLazyListState()
    )
}