package io.dev.relic.feature.pages.agent

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.agent.gemini.viewmodel.GeminiAgentViewModel
import io.dev.relic.feature.screens.main.MainScreenState

@Composable
fun AgentChatPage(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    agentViewModel: GeminiAgentViewModel = hiltViewModel()
) {

}