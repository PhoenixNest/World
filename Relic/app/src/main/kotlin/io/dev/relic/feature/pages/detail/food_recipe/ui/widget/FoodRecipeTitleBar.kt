package io.dev.relic.feature.pages.detail.food_recipe.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants
import io.core.ui.theme.RelicFontFamily
import io.core.ui.theme.mainTextColor
import io.dev.relic.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoodRecipeTitleBar(
    title: String,
    cookTime: Int,
    healthScore: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier
                .weight(3F)
                .basicMarquee(),
            style = TextStyle(
                color = mainTextColor,
                fontSize = 24.sp,
                fontFamily = RelicFontFamily.ubuntu,
                fontWeight = FontWeight.Bold
            )
        )
        Row(
            modifier = Modifier.weight(2F),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FoodRecipeTimerItem(
                iconResId = R.drawable.ic_cook_time,
                contentString = "$cookTime mins"
            )
            Spacer(modifier = Modifier.width(12.dp))
            FoodRecipeTimerItem(
                iconResId = R.drawable.ic_health_score,
                contentString = "$healthScore"
            )
        }
    }
}

@Composable
private fun FoodRecipeTimerItem(
    @DrawableRes iconResId: Int,
    contentString: String
) {
    Row(
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
            tint = mainTextColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = contentString,
            style = TextStyle(
                color = mainTextColor,
                fontFamily = RelicFontFamily.ubuntu
            )
        )
    }
}

@Composable
@Preview
private fun FoodRecipesTitleBarPreview() {
    FoodRecipeTitleBar(
        title = "Egg Ramen",
        cookTime = 10,
        healthScore = 10
    )
}