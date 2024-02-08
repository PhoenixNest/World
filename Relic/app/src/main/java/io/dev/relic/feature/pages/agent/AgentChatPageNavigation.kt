package io.dev.relic.feature.pages.agent

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.agent.gemini.viewmodel.GeminiAgentViewModel
import io.dev.relic.feature.route.RelicRoute.AGENT_CHAT
import io.dev.relic.feature.screens.main.MainScreenState

fun NavController.navigateToAgentChatPage(navOptions: NavOptions? = null) {
    this.navigate(
        route = AGENT_CHAT,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.pageAgentChat(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    geminiAgentViewModel: GeminiAgentViewModel,
    onBackClick: () -> Unit
) {
    composable(route = AGENT_CHAT) {
        AgentChatPageRoute(
            mainScreenState = mainScreenState,
            mainViewModel = mainViewModel,
            geminiAgentViewModel = geminiAgentViewModel,
            onBackClick = onBackClick
        )
    }
}