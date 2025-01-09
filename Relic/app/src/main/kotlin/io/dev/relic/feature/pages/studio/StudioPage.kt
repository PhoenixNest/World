package io.dev.relic.feature.pages.studio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.mandatorySystemGesturesPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.core.ui.RelicUiUtil
import io.core.ui.RelicUiUtil.DEFAULT_RAIL_BAR_WIDTH
import io.core.ui.background.BubbleBackground
import io.core.ui.theme.RelicFontFamily
import io.dev.relic.feature.pages.studio.ui.StudioPageContent
import io.dev.relic.feature.pages.studio.ui.widget.StudioFunctionPanelAction
import io.dev.relic.feature.pages.studio.ui.widget.StudioMaximWidgetAction
import io.dev.relic.feature.pages.studio.vm.StudioMaximViewModel
import io.dev.relic.feature.screens.main.MainScreenState

@Composable
fun StudioPageRoute(
    mainScreenState: MainScreenState,
    maximViewModel: StudioMaximViewModel = hiltViewModel()
) {

    val maximDataState by maximViewModel.getMaximDataStateFlow()
        .collectAsStateWithLifecycle()

    val maximWidgetAction = StudioMaximWidgetAction(
        state = maximDataState,
        onRefreshClick = maximViewModel::fetchRandomMaxim,
        onRetryClick = maximViewModel::fetchRandomMaxim
    )

    val functionPanelAction = StudioFunctionPanelAction(
        onDevelopClick = {},
        onMonitorClick = {},
        onMediaClick = {}
    )

    StudioPage(
        windowSizeClass = mainScreenState.windowSizeClass,
        maximWidgetAction = maximWidgetAction,
        functionPanelAction = functionPanelAction
    )
}

@Composable
private fun StudioPage(
    windowSizeClass: WindowSizeClass,
    maximWidgetAction: StudioMaximWidgetAction,
    functionPanelAction: StudioFunctionPanelAction
) {
    val currentScreenWidthDp = RelicUiUtil.getCurrentScreenWidthDp()
    val isCompactMode = (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact)

    val pageContentMaxWidth = if (isCompactMode) {
        currentScreenWidthDp
    } else {
        (currentScreenWidthDp - DEFAULT_RAIL_BAR_WIDTH) / 2
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface
    ) {
        BubbleBackground(
            modifier = Modifier.fillMaxSize(),
            numberBubbles = 10,
            bubbleColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1F)
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .mandatorySystemGesturesPadding(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StudioPageContent(
                maximWidgetAction = maximWidgetAction,
                functionPanelAction = functionPanelAction,
                modifier = Modifier.width(pageContentMaxWidth)
            )
            if (!isCompactMode) {
                Box(
                    modifier = Modifier
                        .width(pageContentMaxWidth)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Under construction...",
                        fontFamily = RelicFontFamily.googleSans,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}