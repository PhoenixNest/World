package io.dev.relic.feature.pages.todo.ui.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.common.util.TimeUtil.Season.AUTUMN
import io.common.util.TimeUtil.Season.SPRING
import io.common.util.TimeUtil.Season.SUMMER
import io.common.util.TimeUtil.Season.WINTER
import io.common.util.TimeUtil.getCurrentSeason
import io.core.ui.theme.RelicAppTheme
import io.core.ui.theme.RelicFontFamily.googleSans
import io.dev.relic.R

@Stable
data class TodoFeaturePanelAction(
    val onUrgentTasksClick: () -> Unit,
    val onHighTasksClick: () -> Unit,
    val onNormalTasksClick: () -> Unit,
    val onCreateTasksClick: () -> Unit
)

@Composable
fun TodoFeaturesPanel(
    action: TodoFeaturePanelAction,
    modifier: Modifier = Modifier
) {
    val totalNormalItemNums = 3
    val featureNormalItemHeight = 100
    val featureLargeItemHeight = ((featureNormalItemHeight * totalNormalItemNums) + 24)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TodoFeaturePanelLargeItem(
            onClick = action.onCreateTasksClick,
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
            TodoFeaturesPanelItem(
                iconResId = R.drawable.ic_counter_0,
                labelResId = R.string.todo_priority_normal,
                tasksNum = 30,
                backgroundColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.height(featureNormalItemHeight.dp)
            )
            TodoFeaturesPanelItem(
                iconResId = R.drawable.ic_counter_1,
                labelResId = R.string.todo_priority_high,
                tasksNum = 20,
                backgroundColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.height(featureNormalItemHeight.dp)
            )
            TodoFeaturesPanelItem(
                iconResId = R.drawable.ic_counter_2,
                labelResId = R.string.todo_priority_urgent,
                tasksNum = 10,
                backgroundColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.height(featureNormalItemHeight.dp)
            )
        }
    }
}

@Composable
private fun TodoFeaturePanelLargeItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentSeason = getCurrentSeason()
    val imageResId = when (currentSeason) {
        SPRING -> R.mipmap.absoluut_plant_spring
        SUMMER -> R.mipmap.absoluut_plant_summer
        AUTUMN -> R.mipmap.absoluut_plant_autumn
        WINTER -> R.mipmap.absoluut_plant_winter
    }

    Surface(
        modifier = modifier.border(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(12.dp)
        ),
        color = MaterialTheme.colorScheme.surfaceContainer,
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick.invoke() }
        ) {
            Image(
                painter = painterResource(imageResId),
                contentDescription = DEFAULT_DESC,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(1.dp),
                alignment = Alignment.BottomCenter,
                contentScale = ContentScale.FillHeight
            )
            Text(
                text = stringResource(R.string.todo_create_task),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp),
                color = Color.White.copy(alpha = 0.8F),
                fontSize = 32.sp,
                fontFamily = googleSans,
                fontWeight = FontWeight.Bold,
                lineHeight = TextUnit(
                    value = 1.6F,
                    type = TextUnitType.Em
                )
            )
        }
    }
}

@Composable
private fun TodoFeaturesPanelItem(
    @DrawableRes iconResId: Int,
    @StringRes labelResId: Int,
    tasksNum: Int,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    val contentColor = MaterialTheme.colorScheme.contentColorFor(backgroundColor)

    Card(
        modifier = modifier,
        colors = CardColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = CardDefaults.cardColors().disabledContainerColor,
            disabledContentColor = CardDefaults.cardColors().disabledContentColor
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(iconResId),
                contentDescription = DEFAULT_DESC,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1F),
                tint = contentColor.copy(alpha = 0.8F)
            )
            Column(
                modifier = Modifier.weight(3F),
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.Top
                ),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(labelResId),
                    modifier = Modifier,
                    color = contentColor,
                    fontFamily = googleSans,
                    fontWeight = FontWeight.Bold,
                    lineHeight = TextUnit(
                        value = 1.4F,
                        type = TextUnitType.Em
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(R.string.todo_tasks_num, tasksNum),
                    modifier = Modifier,
                    color = contentColor.copy(alpha = 0.8F),
                    fontFamily = googleSans,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
@Preview
private fun TodoFeaturesPanelPreview() {
    RelicAppTheme {
        Surface {
            TodoFeaturesPanel(
                action = TodoFeaturePanelAction(
                    onUrgentTasksClick = {},
                    onHighTasksClick = {},
                    onNormalTasksClick = {},
                    onCreateTasksClick = {}
                ),
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}