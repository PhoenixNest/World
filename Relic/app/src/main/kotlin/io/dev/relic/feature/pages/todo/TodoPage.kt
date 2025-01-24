package io.dev.relic.feature.pages.todo

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import io.core.ui.RelicUiUtil.DEFAULT_BOTTOM_NAVIGATION_BAR_HEIGHT
import io.core.ui.RelicUiUtil.DEFAULT_SHEET_DRAG_HANDLE_HEIGHT
import io.dev.relic.feature.pages.todo.ui.TodoPageCompatModeContent
import io.dev.relic.feature.pages.todo.ui.widget.TodoFeaturePanelAction
import io.dev.relic.feature.pages.todo.ui.widget.TodoPageExpendModeContent
import io.dev.relic.feature.pages.todo.ui.widget.TodoTopBar
import io.dev.relic.feature.pages.todo.ui.widget.TodoTopBarAction
import io.dev.relic.feature.screens.main.MainScreenState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TodoPageRoute(
    mainScreenState: MainScreenState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBackClick: () -> Unit
) {
    val topBarAction = TodoTopBarAction(onBackClick = onBackClick)

    val featurePanelAction = TodoFeaturePanelAction(
        onUrgentTasksClick = {},
        onHighTasksClick = {},
        onNormalTasksClick = {},
        onCreateTasksClick = {}
    )

    TodoPage(
        windowsSizeClass = mainScreenState.windowSizeClass,
        topBarAction = topBarAction,
        featurePanelAction = featurePanelAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TodoPage(
    windowsSizeClass: WindowSizeClass,
    topBarAction: TodoTopBarAction,
    featurePanelAction: TodoFeaturePanelAction
) {
    val isCompactMode = (windowsSizeClass.widthSizeClass == WindowWidthSizeClass.Compact)
    val gestureBarHeight = NavigationBarDefaults.windowInsets.asPaddingValues().calculateBottomPadding()
    val sheetPeakHeight = DEFAULT_SHEET_DRAG_HANDLE_HEIGHT + DEFAULT_BOTTOM_NAVIGATION_BAR_HEIGHT + gestureBarHeight
    val sheetContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
    val sheetContentColor = MaterialTheme.colorScheme.onSurface

    BottomSheetScaffold(
        sheetContent = {

        },
        modifier = Modifier,
        scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberModalBottomSheetState()
        ),
        sheetPeekHeight = sheetPeakHeight,
        sheetShape = RectangleShape,
        sheetContainerColor = sheetContainerColor,
        sheetContentColor = sheetContentColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            TodoTopBar(action = topBarAction)
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isCompactMode) {
                    TodoPageCompatModeContent(featurePanelAction = featurePanelAction)
                } else {
                    TodoPageExpendModeContent()
                }
            }
        }
    }
}