package io.dev.relic.feature.pages.studio

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.news.viewmodel.NewsViewModel
import io.dev.relic.feature.function.todo.TodoDataState
import io.dev.relic.feature.function.todo.viewmodel.TodoViewModel
import io.dev.relic.feature.pages.agent.navigateToAgentChatPage
import io.dev.relic.feature.pages.gallery.navigateToGalleryPage
import io.dev.relic.feature.pages.studio.ui.StudioPageContent
import io.dev.relic.feature.screens.main.MainScreenState
import io.module.map.tomtom.legacy.TomTomMapActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudioPageRoute(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    todoViewModel: TodoViewModel,
    newsViewModel: NewsViewModel
) {

    /* ======================== Common ======================== */

    val context = LocalContext.current
    val navController = mainScreenState.navHostController

    /* ======================== Field ======================== */

    val todoDataState by todoViewModel.todoDataStateFlow
        .collectAsStateWithLifecycle()

    /* ======================== Ui ======================== */

    val todoRowListState = rememberLazyListState()

    /* ======================== Ui State ======================== */

    val todoListState = StudioListState(
        todoListState = todoRowListState
    )

    val todoState = buildTodoState(
        dataState = todoDataState,
        listState = todoListState
    )

    val agentState = buildAgentState(
        onNavigateToChatPage = navController::navigateToAgentChatPage
    )

    val galleryState = buildGalleryState(
        onNavigateToGalleryPage = navController::navigateToGalleryPage
    )

    val mapState = buildMapState(
        onNavigateToMapActivity = { TomTomMapActivity.start(context) }
    )

    BottomSheetScaffold(
        sheetContent = {
            StudioPageBottomSheet(
                newsViewModel = newsViewModel,
                mainScreenState = mainScreenState
            )
        },
        scaffoldState = rememberBottomSheetScaffoldState(),
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        sheetPeekHeight = 120.dp,
        sheetContainerColor = MaterialTheme.colorScheme.secondaryContainer
    ) {
        StudioPageContent(
            onUserClick = {},
            todoState = todoState,
            agentState = agentState,
            galleryState = galleryState,
            mapState = mapState
        )
    }
}

/* ======================== Page Ui State Builder ======================== */

/**
 * Build state to power the Todo unit of studio page
 *
 * @param dataState     Data state flow of todo.
 * @param listState     List state for row and column.
 * */
private fun buildTodoState(
    dataState: TodoDataState,
    listState: StudioListState
): StudioTotoState {
    return StudioTotoState(
        dataState = dataState,
        action = StudioTodoAction(
            onCheckTodoClick = {},
            onAddClick = {},
            onItemClick = {},
            onTailClick = {}
        ),
        listState = listState
    )
}

/**
 * Build state to power the Agent unit of studio page.
 *
 * @param onNavigateToChatPage      Navigate to the next page to continue chat with your Agent.
 * */
private fun buildAgentState(onNavigateToChatPage: () -> Unit): StudioAgentState {
    return StudioAgentState(
        action = StudioAgentAction(onStartChatClick = onNavigateToChatPage)
    )
}

/**
 * Build state to power the Agent unit of studio page.
 *
 * @param onNavigateToGalleryPage      Navigate to the next page to preview the gallery by using pixabay api.
 * */
private fun buildGalleryState(onNavigateToGalleryPage: () -> Unit): StudioGalleryState {
    return StudioGalleryState(
        action = StudioGalleryAction(onStartPreviewClick = onNavigateToGalleryPage)
    )
}

/**
 * Build state to power the Agent unit of studio page.
 *
 * @param onNavigateToMapActivity      Navigate to the next page to explore the poi by using TomTom map.
 * */
private fun buildMapState(onNavigateToMapActivity: () -> Unit): StudioMapState {
    return StudioMapState(
        action = StudioMapAction(
            onStartExploreClick = onNavigateToMapActivity
        )
    )
}