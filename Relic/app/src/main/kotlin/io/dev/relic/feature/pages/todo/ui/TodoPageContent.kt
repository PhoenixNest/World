package io.dev.relic.feature.pages.todo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.dev.relic.feature.pages.todo.ui.widget.TodoFeaturePanelAction
import io.dev.relic.feature.pages.todo.ui.widget.TodoFeaturesPanel

@Composable
fun TodoPageContent(
    featurePanelAction: TodoFeaturePanelAction
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.Start,
        contentPadding = PaddingValues(top = 12.dp)
    ) {
        item {
            TodoFeaturesPanel(
                action = featurePanelAction,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}