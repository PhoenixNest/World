package io.dev.relic.feature.pages.studio.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.widget.CommonTextClock
import io.data.model.maxim.MaximModel
import io.dev.relic.feature.function.maxim.MaximDataState
import io.dev.relic.feature.pages.studio.ui.widget.StudioFunctionPanel
import io.dev.relic.feature.pages.studio.ui.widget.StudioFunctionPanelAction
import io.dev.relic.feature.pages.studio.ui.widget.StudioMaximWidget
import io.dev.relic.feature.pages.studio.ui.widget.StudioMaximWidgetAction

@Composable
fun StudioPageContent(
    maximWidgetAction: StudioMaximWidgetAction,
    functionPanelAction: StudioFunctionPanelAction,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 32.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommonTextClock(
            calendarTextColor = MaterialTheme.colorScheme.onSurface,
            clockBlockContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3F),
            clockTextColor = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth()
        )
        StudioFunctionPanel(
            action = functionPanelAction,
            modifier = Modifier.fillMaxWidth()
        )
        StudioMaximWidget(
            action = maximWidgetAction,
            maximTextColor = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun StudioPageContentPreview() {
    StudioPageContent(
        maximWidgetAction = StudioMaximWidgetAction(
            state = MaximDataState.FetchSucceed(
                data = MaximModel(
                    hitokoto = "羌笛何须怨杨柳，春风不度玉门关。",
                    from = "凉州词二首·其一",
                    fromWho = "王之涣",
                    creator = "---",
                    createdAt = "---"
                )
            ),
            onRefreshClick = {},
            onRetryClick = {}
        ),
        functionPanelAction = StudioFunctionPanelAction(
            onDevelopClick = {},
            onMonitorClick = {},
            onMediaClick = {}
        )
    )
}