package io.dev.relic.feature.pages.home.ui.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.RelicFontFamily.googleSans
import io.dev.relic.R

@Stable
data class HomeFeaturePanelAction(
    val onAgentClick: () -> Unit,
    val onFoodRecipesClick: () -> Unit,
    val onTodoClick: () -> Unit
)

@Composable
fun HomeFeaturesPanel(
    action: HomeFeaturePanelAction,
    modifier: Modifier = Modifier
) {
    val featureNormalItemHeight = 100
    val featureLargeItemHeight = ((featureNormalItemHeight * 2) + 12)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HomeFeaturesPanelItem(
            iconResId = R.drawable.ic_agent_craft,
            labelResId = R.string.agent_feature_title,
            onClick = action.onAgentClick,
            backgroundColor = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp,
            modifier = Modifier
                .weight(1F)
                .height(featureLargeItemHeight.dp)
        )
        Column(
            modifier = Modifier.weight(1F),
            verticalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeFeaturesPanelItem(
                iconResId = R.drawable.ic_foods,
                labelResId = R.string.food_recipes_feature_title,
                onClick = action.onFoodRecipesClick,
                backgroundColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.height(featureNormalItemHeight.dp)
            )
            HomeFeaturesPanelItem(
                iconResId = R.drawable.ic_todo,
                labelResId = R.string.todo_feature_title,
                onClick = action.onTodoClick,
                backgroundColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.height(featureNormalItemHeight.dp)
            )
        }
    }
}

@Composable
private fun HomeFeaturesPanelItem(
    @DrawableRes iconResId: Int,
    @StringRes labelResId: Int,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    fontSize: TextUnit = TextUnit.Unspecified,
    modifier: Modifier = Modifier
) {
    val contentColor = MaterialTheme.colorScheme.contentColorFor(backgroundColor)

    Card(
        onClick = onClick,
        modifier = modifier,
        colors = CardColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = CardDefaults.cardColors().disabledContainerColor,
            disabledContentColor = CardDefaults.cardColors().disabledContentColor
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                painter = painterResource(iconResId),
                contentDescription = DEFAULT_DESC,
                modifier = Modifier
                    .padding(12.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceDim.copy(alpha = 0.3F),
                        shape = CircleShape
                    )
                    .padding(12.dp)
                    .align(Alignment.TopStart),
                tint = contentColor
            )
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = DEFAULT_DESC,
                modifier = Modifier
                    .padding(
                        horizontal = 8.dp,
                        vertical = (12 + 12).dp
                    )
                    .align(Alignment.TopEnd),
                tint = contentColor
            )
            Text(
                text = stringResource(labelResId),
                color = contentColor,
                fontSize = fontSize,
                fontFamily = googleSans,
                lineHeight = TextUnit(
                    value = 1.6F,
                    type = TextUnitType.Em
                ),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    )
                    .align(Alignment.BottomStart)
            )
        }
    }
}

@Composable
@Preview
private fun HomeFeaturesPanelPreview() {
    HomeFeaturesPanel(
        action = HomeFeaturePanelAction(
            onAgentClick = {},
            onFoodRecipesClick = {},
            onTodoClick = {}
        )
    )
}