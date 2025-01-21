package io.dev.relic.feature.pages.home.ui.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.RelicAppTheme
import io.core.ui.theme.RelicFontFamily.googleSans
import io.dev.relic.R
import io.dev.relic.feature.function.agent.AgentQuestionModel

data class HomeQuickPromptsPanelAction(
    val questions: List<AgentQuestionModel>,
    @Stable val onRefreshClick: () -> Unit,
    @Stable val onPromptClick: (prompt: String) -> Unit
)

@Composable
fun HomeQuickPromptsPanel(
    action: HomeQuickPromptsPanelAction,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.Start
    ) {
        HomeQuickPromptsPanelTitle(onRefreshClick = action.onRefreshClick)
        HomeQuestionsList(action = action)
    }
}

@Composable
private fun HomeQuickPromptsPanelTitle(onRefreshClick: () -> Unit) {
    val title = stringResource(R.string.agent_quick_question_title)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HomeTitleBar(titleStr = title)
        IconButton(onClick = onRefreshClick) {
            Icon(
                painter = painterResource(R.drawable.ic_refresh),
                contentDescription = DEFAULT_DESC,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun HomeQuestionsList(action: HomeQuickPromptsPanelAction) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.Start
    ) {
        action.questions.fastForEachIndexed { index, model ->
            if (index >= 3) {
                return@fastForEachIndexed
            }
            HomeQuestionItem(
                strResId = model.strResId,
                onClick = { action.onPromptClick.invoke(it) }
            )
        }
    }
}

@Composable
private fun HomeQuestionItem(
    @StringRes strResId: Int,
    onClick: (prompt: String) -> Unit
) {
    val question = stringResource(strResId)

    Surface(
        onClick = { onClick.invoke(question) },
        color = MaterialTheme.colorScheme.surfaceContainer,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = question,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = googleSans,
                style = MaterialTheme.typography.labelMedium
            )
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = DEFAULT_DESC
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun HomeQuickPromptsPanelPreview() {
    RelicAppTheme {
        HomeQuickPromptsPanel(
            action = HomeQuickPromptsPanelAction(
                questions = AgentQuestionModel.getRandomQuestions(),
                onRefreshClick = {},
                onPromptClick = {}
            )
        )
    }
}