package io.dev.relic.feature.pages.studio.ui.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainButtonColor
import io.core.ui.theme.mainIconColorLight
import io.core.ui.theme.mainTextColor
import io.dev.relic.R

private const val DEFAULT_MAX_ITEM_PER_ROW = 4

@Suppress("FunctionName")
fun LazyListScope.StudioToolsPanel(
    onAgentClick: () -> Unit,
    onTodoClick: () -> Unit,
    onGalleryClick: () -> Unit,
    onMapClick: () -> Unit
) {
    item {
        StudioToolsFlowRow(
            onAgentClick = onAgentClick,
            onTodoClick = onTodoClick,
            onGalleryClick = onGalleryClick,
            onMapClick = onMapClick
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StudioToolsFlowRow(
    onAgentClick: () -> Unit,
    onTodoClick: () -> Unit,
    onGalleryClick: () -> Unit,
    onMapClick: () -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.Start
        ),
        verticalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.Top
        ),
        maxItemsInEachRow = DEFAULT_MAX_ITEM_PER_ROW
    ) {
        StudioToolsPanelItem(
            iconResId = R.drawable.ic_agent_start_chat,
            labelResId = R.string.gemini,
            onClick = onAgentClick
        )
        StudioToolsPanelItem(
            iconResId = R.drawable.ic_agent_craft,
            labelResId = R.string.todo_title,
            onClick = onTodoClick
        )
        StudioToolsPanelItem(
            iconResId = R.drawable.ic_gallery,
            labelResId = R.string.gallery_label,
            onClick = onGalleryClick
        )
        StudioToolsPanelItem(
            iconResId = R.drawable.ic_explore,
            labelResId = R.string.explore_label,
            onClick = onMapClick
        )
    }
}

@Composable
private fun StudioToolsPanelItem(
    @DrawableRes iconResId: Int,
    @StringRes labelResId: Int,
    onClick: () -> Unit,
    iconColor: Color = mainIconColorLight
) {
    Surface(
        modifier = Modifier.wrapContentSize(),
        shape = RoundedCornerShape(12.dp),
        color = mainButtonColor
    ) {
        Column(
            modifier = Modifier
                .size(72.dp)
                .clickable { onClick.invoke() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(iconResId),
                contentDescription = DEFAULT_DESC,
                colorFilter = ColorFilter.tint(iconColor)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(labelResId),
                style = TextStyle(
                    color = mainTextColor,
                    fontFamily = ubuntu
                )
            )
        }
    }
}

@Preview
@Composable
private fun StudioToolsFlowRowPreview() {
    StudioToolsFlowRow(
        onAgentClick = {},
        onTodoClick = {},
        onGalleryClick = {},
        onMapClick = {}
    )
}