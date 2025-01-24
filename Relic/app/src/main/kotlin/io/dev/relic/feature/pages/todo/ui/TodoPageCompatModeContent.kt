package io.dev.relic.feature.pages.todo.ui

import androidx.compose.runtime.Composable
import io.dev.relic.feature.pages.todo.ui.widget.TodoFeaturePanelAction

@Composable
fun TodoPageCompatModeContent(featurePanelAction: TodoFeaturePanelAction) {
    TodoPageContent(featurePanelAction = featurePanelAction)
}