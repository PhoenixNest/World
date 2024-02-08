package io.dev.relic.feature.function.agent.gemini.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.agent.gemini.model.AbsGeminiCell
import io.dev.relic.feature.function.agent.gemini.ui.widget.AgentMessageCell

@Composable
fun AgentChatHistory(
    chatHistory: List<AbsGeminiCell>,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.Start,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        itemsIndexed(chatHistory) { index, cell ->
            val itemDecorationModifier = Modifier.padding(
                top = if (index == 0) 16.dp else 0.dp,
                bottom = if (index == chatHistory.size - 1) 160.dp else 0.dp
            )
            AgentMessageCell(
                geminiCellContent = cell,
                modifier = itemDecorationModifier
            )
        }
    }
}