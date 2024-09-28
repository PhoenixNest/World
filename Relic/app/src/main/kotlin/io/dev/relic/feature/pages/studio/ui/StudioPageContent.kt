package io.dev.relic.feature.pages.studio.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.theme.mainThemeColorLight
import io.dev.relic.feature.function.todo.TodoDataState
import io.dev.relic.feature.pages.studio.StudioAgentAction
import io.dev.relic.feature.pages.studio.StudioAgentState
import io.dev.relic.feature.pages.studio.StudioGalleryAction
import io.dev.relic.feature.pages.studio.StudioGalleryState
import io.dev.relic.feature.pages.studio.StudioListState
import io.dev.relic.feature.pages.studio.StudioMapAction
import io.dev.relic.feature.pages.studio.StudioMapState
import io.dev.relic.feature.pages.studio.StudioTodoAction
import io.dev.relic.feature.pages.studio.StudioTotoState
import io.dev.relic.feature.pages.studio.ui.widget.StudioTabBar
import io.dev.relic.feature.pages.studio.ui.widget.StudioToolsPanel

@Composable
fun StudioPageContent(
    onUserClick: () -> Unit,
    todoState: StudioTotoState,
    agentState: StudioAgentState,
    galleryState: StudioGalleryState,
    mapState: StudioMapState
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            StudioTabBar(onUserClick = onUserClick)
            StudioPageContent(
                todoState = todoState,
                agentState = agentState,
                galleryState = galleryState,
                mapState = mapState
            )
        }
    }
}

@Composable
private fun StudioPageContent(
    todoState: StudioTotoState,
    agentState: StudioAgentState,
    galleryState: StudioGalleryState,
    mapState: StudioMapState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = mainThemeColorLight.copy(alpha = 0.1F),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 12.dp,
            end = 12.dp
        )
    ) {
        StudioToolsPanel(
            onAgentClick = agentState.action.onStartChatClick,
            onTodoClick = todoState.action.onCheckTodoClick,
            onGalleryClick = galleryState.action.onStartPreviewClick,
            onMapClick = mapState.action.onStartExploreClick
        )
    }
}

@Composable
@Preview
private fun StudioPageContentPreview() {
    StudioPageContent(
        onUserClick = {},
        todoState = StudioTotoState(
            dataState = TodoDataState.NoTodoData,
            action = StudioTodoAction(
                onCheckTodoClick = {},
                onAddClick = {},
                onItemClick = {},
                onTailClick = {}
            ),
            listState = StudioListState(
                todoListState = rememberLazyListState()
            )
        ),
        agentState = StudioAgentState(
            action = StudioAgentAction(
                onStartChatClick = {}
            )
        ),
        galleryState = StudioGalleryState(
            action = StudioGalleryAction(
                onStartPreviewClick = {}
            )
        ),
        mapState = StudioMapState(
            action = StudioMapAction(
                onStartExploreClick = {}
            )
        )
    )
}