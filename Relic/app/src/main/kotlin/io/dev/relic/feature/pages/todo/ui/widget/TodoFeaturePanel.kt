package io.dev.relic.feature.pages.todo.ui.widget

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
import androidx.compose.material3.Surface
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
        Column(
            modifier = Modifier
                .weight(1F)
                .height(featureLargeItemHeight.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TodoFeaturesPanelItem(
                iconEmoji = "🤠",
                labelResId = R.string.todo_create_task,
                onClick = action.onUrgentTasksClick,
                backgroundColor = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp,
                modifier = Modifier.weight(1F)
            )
            TodoFeaturesPanelItem(
                iconEmoji = "🧐",
                labelResId = R.string.todo_all_task,
                onClick = action.onUrgentTasksClick,
                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                fontSize = 20.sp,
                modifier = Modifier.weight(1F)
            )
        }
        Column(
            modifier = Modifier.weight(1F),
            verticalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TodoFeaturesPanelItem(
                iconEmoji = "🙂",
                labelResId = R.string.todo_priority_normal,
                onClick = action.onNormalTasksClick,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.height(featureNormalItemHeight.dp)
            )
            TodoFeaturesPanelItem(
                iconEmoji = "😮",
                labelResId = R.string.todo_priority_high,
                onClick = action.onHighTasksClick,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.height(featureNormalItemHeight.dp)
            )
            TodoFeaturesPanelItem(
                iconEmoji = "🤯",
                labelResId = R.string.todo_priority_urgent,
                onClick = action.onHighTasksClick,
                backgroundColor = MaterialTheme.colorScheme.errorContainer,
                modifier = Modifier.height(featureNormalItemHeight.dp)
            )
        }
    }
}

@Composable
private fun TodoFeaturesPanelItem(
    iconEmoji: String,
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
            Text(
                text = iconEmoji,
                modifier = Modifier
                    .padding(12.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceDim.copy(alpha = 0.3F),
                        shape = CircleShape
                    )
                    .padding(12.dp)
                    .align(Alignment.TopStart)
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