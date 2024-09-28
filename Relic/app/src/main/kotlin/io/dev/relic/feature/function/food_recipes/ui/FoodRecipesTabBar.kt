package io.dev.relic.feature.function.food_recipes.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.CommonVerticalIconTextButton
import io.dev.relic.R
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesCategories

@Composable
fun FoodRecipesTabBar(
    currentSelectedTab: Int,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    lazyListState: LazyListState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.onPrimary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.food_recipes_recommend_title),
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            state = lazyListState,
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Start
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemsIndexed(FoodRecipesCategories.entries) { index: Int, item: FoodRecipesCategories ->
                val tabLabel: String = stringResource(id = item.tabLabelResId)
                val itemDecorationModifier: Modifier = Modifier.padding(
                    start = if (index == 0) 16.dp else 0.dp,
                    end = if (index == FoodRecipesCategories.entries.size - 1) 16.dp else 0.dp
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
                MaterialTheme.colorScheme.primary
            } else {
                Color.Transparent
            },
            textColor = MaterialTheme.colorScheme.onPrimary,
            shape = RoundedCornerShape(12.dp)
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