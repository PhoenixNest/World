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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.common.util.TimeUtil.TimeSection
import io.common.util.TimeUtil.TimeSection.AFTERNOON
import io.common.util.TimeUtil.TimeSection.DAY
import io.common.util.TimeUtil.TimeSection.MIDNIGHT
import io.common.util.TimeUtil.TimeSection.NIGHT
import io.common.util.TimeUtil.TimeSection.NOON
import io.common.util.TimeUtil.TimeSection.UNKNOWN
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainButtonColorLight
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColor
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
                            mainThemeColor.copy(alpha = 0.1F),
                            mainThemeColor
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
            .systemBarsPadding()
    ) {
        IconButton(
            onClick = onOpenDrawer,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_apps),
                contentDescription = DEFAULT_DESC,
                tint = mainButtonColorLight
            )
        }
        Text(
            text = stringResource(R.string.home_title),
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle(
                color = mainTextColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ubuntu,
                textMotion = TextMotion.Animated
            )
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
                    tint = mainButtonColorLight
                )
            }
            IconButton(onClick = onNavigateToSetting) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = DEFAULT_DESC,
                    tint = mainButtonColorLight
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
            style = TextStyle(
                color = mainTextColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ubuntu,
                textMotion = TextMotion.Animated
            )
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