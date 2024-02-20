package io.dev.relic.feature.pages.detail.food_recipes.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainTextColor
import io.data.model.food_recipes.FoodRecipeInformationModel
import io.dev.relic.R

@Composable
fun FoodRecipeDetailPanel(model: FoodRecipeInformationModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        model.apply {
            FoodRecipeTitleRow(
                title = title ?: "",
                cookTime = readyInMinutes ?: 0,
                healthScore = healthScore ?: 0
            )
            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FoodRecipeTitleRow(
    title: String,
    cookTime: Int,
    healthScore: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier
                .weight(1F)
                .basicMarquee(),
            style = TextStyle(
                color = mainTextColor,
                fontSize = 26.sp,
                fontFamily = ubuntu,
                fontWeight = FontWeight.Bold
            )
        )
        FoodRecipeTimerBar(
            cookTime = cookTime,
            healthScore = healthScore
        )
    }
}

@Composable
private fun RowScope.FoodRecipeTimerBar(
    cookTime: Int,
    healthScore: Int
) {
    Row(
        modifier = Modifier.weight(1F),
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
            contentDescription = DEFAULT_DESC,
            tint = mainTextColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = contentString,
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )
    }
}

@Composable
@Preview
private fun FoodRecipeTitleRowPreview() {
    FoodRecipeTitleRow(
        title = "Egg Ramen",
        cookTime = 10,
        healthScore = 10
    )
}