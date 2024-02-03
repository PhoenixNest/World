package io.dev.relic.feature.pages.home.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import io.common.util.TimeUtil.TimeSection.Afternoon
import io.common.util.TimeUtil.TimeSection.Day
import io.common.util.TimeUtil.TimeSection.MidNight
import io.common.util.TimeUtil.TimeSection.Night
import io.common.util.TimeUtil.TimeSection.Noon
import io.common.util.TimeUtil.TimeSection.Unknown
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainButtonColorLight
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColor
import io.dev.relic.R
import io.dev.relic.feature.function.agent.AgentSearchBar

@Suppress("FunctionName")
fun LazyListScope.HomeTopPanel(
    currentTimeSection: TimeSection,
    onOpenDrawer: () -> Unit
) {
    item {
        HomeTopPanelContent(
            currentTimeSection = currentTimeSection,
            onOpenDrawer = onOpenDrawer
        )
    }
}

@Composable
private fun HomeTopPanelContent(
    currentTimeSection: TimeSection,
    onOpenDrawer: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        HomeTopCover(currentTimeSection)
        HomeTopBar(onOpenDrawer)
        HomeAgentPanel(
            currentTimeSection = currentTimeSection,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun HomeTopCover(currentTimeSection: TimeSection) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val coverHeight = screenHeight * 3 / 8
    val coverResId = when (currentTimeSection) {
        Day,
        Noon,
        Afternoon -> R.mipmap.day

        Night -> R.mipmap.night
        MidNight -> R.mipmap.midnight
        Unknown -> R.mipmap.day
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
    {
        Image(
            painter = painterResource(id = coverResId),
            contentDescription = DEFAULT_DESC,
            modifier = Modifier
                .fillMaxWidth()
                .height(coverHeight),
            alignment = Alignment.Center,
            contentScale = ContentScale.FillWidth
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(coverHeight)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            mainThemeColor.copy(alpha = 0.2F),
                            mainThemeColor
                        )
                    )
                )
        )
    }
}

@Composable
private fun HomeTopBar(onOpenDrawer: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .systemBarsPadding()
        ) {
            IconButton(
                onClick = onOpenDrawer,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Apps,
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
        }
    }
}

@Composable
private fun HomeAgentPanel(
    currentTimeSection: TimeSection,
    modifier: Modifier = Modifier
) {
    val greetingTextResId = when (currentTimeSection) {
        Day,
        Noon -> R.string.home_greeting_title_day

        Afternoon -> R.string.home_greeting_title_afternoon

        Night,
        MidNight -> R.string.home_greeting_title_night

        Unknown -> R.string.home_greeting_title_day
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
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
        Spacer(modifier = Modifier.height(16.dp))
        AgentSearchBar()
        Spacer(modifier = Modifier.height(52.dp))
    }
}

@Composable
@Preview
private fun HomeTopBarPanelDayPreview() {
    HomeTopPanelContent(
        currentTimeSection = Day,
        onOpenDrawer = {}
    )
}

@Composable
@Preview
private fun HomeTopBarPanelNightPreview() {
    HomeTopPanelContent(
        currentTimeSection = Night,
        onOpenDrawer = {}
    )
}

@Composable
@Preview
private fun HomeTopBarPanelMidnightPreview() {
    HomeTopPanelContent(
        currentTimeSection = MidNight,
        onOpenDrawer = {}
    )
}