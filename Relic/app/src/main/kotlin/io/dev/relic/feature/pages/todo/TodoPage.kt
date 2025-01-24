package io.dev.relic.feature.pages.todo

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
private fun TodoPage(
    windowsSizeClass: WindowSizeClass,
    topBarAction: TodoTopBarAction,
    featurePanelAction: TodoFeaturePanelAction
) {
    val isCompactMode = (windowsSizeClass.widthSizeClass == WindowWidthSizeClass.Compact)

    Column(
        modifier = Modifier.fillMaxSize(),
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