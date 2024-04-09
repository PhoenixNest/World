package io.dev.relic.feature.pages.studio.ui.widget

import androidx.compose.foundation.lazy.LazyListScope
import io.dev.relic.feature.function.agent.gemini.ui.AgentComponent

@Suppress("FunctionName")
fun LazyListScope.StudioAgentPanel(onStartChat: () -> Unit) {
    item {
        AgentComponent(onStartChat = onStartChat)
    }
}