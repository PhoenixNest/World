package io.dev.relic.feature.pages.studio.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.RelicFontFamily.googleSans
import io.dev.relic.feature.pages.studio.util.StudioFunctionType
import io.dev.relic.feature.pages.studio.util.StudioFunctionType.DEVELOP
import io.dev.relic.feature.pages.studio.util.StudioFunctionType.MEDIA
import io.dev.relic.feature.pages.studio.util.StudioFunctionType.MONITOR

data class StudioFunctionPanelAction(
    @Stable val onDevelopClick: () -> Unit,
    @Stable val onMonitorClick: () -> Unit,
    @Stable val onMediaClick: () -> Unit
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StudioFunctionPanel(
    action: StudioFunctionPanelAction,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        maxItemsInEachRow = 3
    ) {
        StudioFunctionType.entries.forEach { type ->
            StudioFunctionPanelItem(
                type = type,
                onClick = {
                    when (type) {
                        MONITOR -> action.onMonitorClick
                        DEVELOP -> action.onDevelopClick
                        MEDIA -> action.onMediaClick
                    }
                }
            )
        }
    }
}

@Composable
private fun StudioFunctionPanelItem(
    type: StudioFunctionType,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.size(100.dp),
        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3F),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick.invoke() }
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painterResource(id = type.iconResId),
                contentDescription = DEFAULT_DESC,
                tint = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(id = type.labelResId),
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = googleSans,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
@Preview
private fun StudioFunctionPanelPreview() {
    StudioFunctionPanel(
        action = StudioFunctionPanelAction(
            onDevelopClick = {},
            onMonitorClick = {},
            onMediaClick = {}
        ),
        modifier = Modifier.padding(12.dp)
    )
}