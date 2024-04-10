package io.dev.relic.feature.pages.studio

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.core.ui.theme.bottomSheetBackgroundColor
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.news.viewmodel.NewsViewModel
import io.dev.relic.feature.function.todo.TodoDataState
import io.dev.relic.feature.function.todo.viewmodel.TodoViewModel
import io.dev.relic.feature.pages.agent.navigateToAgentChatPage
import io.dev.relic.feature.pages.studio.ui.StudioPageContent
import io.dev.relic.feature.screens.main.MainScreenState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StudioPageRoute(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    todoViewModel: TodoViewModel,
    newsViewModel: NewsViewModel
) {

    /* ======================== Common ======================== */

    val navController = mainScreenState.navHostController

    /* ======================== Field ======================== */

    val todoDataState by todoViewModel.todoDataStateFlow
        .collectAsStateWithLifecycle()

    /* ======================== Ui ======================== */

    val todoRowListState = rememberLazyListState()

    // List state
    val todoListState = StudioTodoListState(
        todoListState = todoRowListState
    )

    // Todo Data state
    val todoState = buildTodoState(
        dataState = todoDataState,
        listState = todoListState
    )

    // Agent Data State
    val agentState = buildAgentState(
        onNavigateToChatPage = navController::navigateToAgentChatPage
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
        sheetBackgroundColor = bottomSheetBackgroundColor,
        sheetPeekHeight = 140.dp
    ) {
        StudioPageContent(
            onUserClick = {},
            todoState = todoState,
            agentState = agentState
        )
    }
}

/**
 * Build state to power the Todo unit of studio page
 *
 * @param dataState     Data state flow of todo.
 * @param listState     List state for row and column.
 * */
private fun buildTodoState(
    dataState: TodoDataState,
    listState: StudioTodoListState
): StudioTotoState {
    return StudioTotoState(
        dataState = dataState,
        action = StudioTodoAction(
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