package io.dev.relic.feature.pages.home.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.common.util.TimeUtil.TimeSection
import io.common.util.TimeUtil.TimeSection.AFTERNOON
import io.common.util.TimeUtil.TimeSection.DAY
import io.common.util.TimeUtil.TimeSection.MIDNIGHT
import io.common.util.TimeUtil.TimeSection.NIGHT
import io.common.util.TimeUtil.TimeSection.NOON
import io.common.util.TimeUtil.TimeSection.UNKNOWN
import io.dev.relic.R
import io.dev.relic.feature.function.agent.ui.AgentSearchBar

@Suppress("FunctionName")
fun LazyListScope.HomeTopPanel(
    onOpenDrawer: () -> Unit,
    onNavigateToExplore: () -> Unit,
    onNavigateToSetting: () -> Unit,
    currentTimeSection: TimeSection,
    agentPrompt: String,
    onAgentPromptChange: (newPrompt: String) -> Unit,
    onAgentStartChat: () -> Unit
) {
    item {
        HomeTopPanelContent(
            onOpenDrawer = onOpenDrawer,
            onNavigateToExplore = onNavigateToExplore,
            onNavigateToSetting = onNavigateToSetting,
            currentTimeSection = currentTimeSection,
            agentSearchContent = agentPrompt,
            onAgentSearchPromptChange = onAgentPromptChange,
            onAgentStartChat = onAgentStartChat
        )
    }
}

@Composable
private fun HomeTopPanelContent(
    onOpenDrawer: () -> Unit,
    onNavigateToExplore: () -> Unit,
    onNavigateToSetting: () -> Unit,
    currentTimeSection: TimeSection,
    agentSearchContent: String,
    onAgentSearchPromptChange: (newPrompt: String) -> Unit,
    onAgentStartChat: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
    ) {
        HomeTopCover(currentTimeSection)
        HomeTopBar(
            onOpenDrawer = onOpenDrawer,
            onNavigateToExplore = onNavigateToExplore,
            onNavigateToSetting = onNavigateToSetting
        )
        HomeAgentPanel(
            currentTimeSection = currentTimeSection,
            agentSearchContent = agentSearchContent,
            onAgentSearchPromptChange = onAgentSearchPromptChange,
            onAgentStartChat = onAgentStartChat,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun HomeTopCover(
    currentTimeSection: TimeSection,
    modifier: Modifier = Modifier
) {
    val coverResId = when (currentTimeSection) {
        DAY,
        NOON,
        AFTERNOON -> R.mipmap.day

        NIGHT -> R.mipmap.night
        MIDNIGHT -> R.mipmap.midnight
        UNKNOWN -> R.mipmap.day
    }

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = coverResId),
            contentDescription = DEFAULT_DESC,
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.Center,
            contentScale = ContentScale.FillWidth
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1F),
                            MaterialTheme.colorScheme.primary
                        )
                    )
                )
        )
    }
}

@Composable
private fun HomeTopBar(
    onOpenDrawer: () -> Unit,
    onNavigateToExplore: () -> Unit,
    onNavigateToSetting: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = onOpenDrawer,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_apps),
                contentDescription = DEFAULT_DESC,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        Text(
            text = stringResource(R.string.home_title),
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            Modifier.align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateToExplore) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_explore),
                    contentDescription = DEFAULT_DESC,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            IconButton(onClick = onNavigateToSetting) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = DEFAULT_DESC,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
private fun HomeAgentPanel(
    currentTimeSection: TimeSection,
    agentSearchContent: String,
    onAgentSearchPromptChange: (newPrompt: String) -> Unit,
    onAgentStartChat: () -> Unit,
    modifier: Modifier = Modifier
) {
    val greetingTextResId = when (currentTimeSection) {
        DAY,
        NOON -> R.string.home_greeting_title_day

        AFTERNOON -> R.string.home_greeting_title_afternoon

        NIGHT,
        MIDNIGHT -> R.string.home_greeting_title_night

        UNKNOWN -> R.string.home_greeting_title_day
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Text(
            text = stringResource(id = greetingTextResId),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(20.dp))
        AgentSearchBar(
            contentValue = agentSearchContent,
            onValueChange = onAgentSearchPromptChange,
            onAgentStartChat = onAgentStartChat
        )
        Spacer(modifier = Modifier.height(52.dp))
    }
}

@Composable
@Preview
private fun HomeTopBarPanelDayPreview() {
    HomeTopPanelContent(
        onOpenDrawer = {},
        onNavigateToExplore = {},
        onNavigateToSetting = {},
        currentTimeSection = DAY,
        agentSearchContent = "",
        onAgentSearchPromptChange = {},
        onAgentStartChat = {}
    )
}

@Composable
@Preview
private fun HomeTopBarPanelNightPreview() {
    HomeTopPanelContent(
        onOpenDrawer = {},
        onNavigateToExplore = {},
        onNavigateToSetting = {},
        currentTimeSection = NIGHT,
        agentSearchContent = "",
        onAgentSearchPromptChange = {},
        onAgentStartChat = {}
    )
}

@Composable
@Preview
private fun HomeTopBarPanelMidnightPreview() {
    HomeTopPanelContent(
        onOpenDrawer = {},
        onNavigateToExplore = {},
        onNavigateToSetting = {},
        currentTimeSection = MIDNIGHT,
        agentSearchContent = "",
        onAgentSearchPromptChange = {},
        onAgentStartChat = {}
    )
}