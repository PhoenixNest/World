package io.dev.relic.feature.pages.agent

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import io.dev.relic.feature.route.RelicRoute.AGENT_CHAT

private const val KEY_AGENT_CHAT_PROMPT = "key_food_recipe_id"

@SuppressLint("RestrictedApi")
fun NavController.navigateToAgentChatPage(
    prompt: String?,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val deepLinkRequest = NavDeepLinkRequest.Builder.fromUri(
        NavDestination.createRoute(AGENT_CHAT).toUri()
    ).build()

    graph.matchDeepLink(deepLinkRequest)?.let { deepLinkMatch ->
        val bundle = Bundle().apply {
            putString(KEY_AGENT_CHAT_PROMPT, prompt)
        }

        this.navigate(
            resId = deepLinkMatch.destination.id,
            args = bundle,
            navOptions = navOptions,
            navigatorExtras = navigatorExtras
        )
    }
}

fun NavGraphBuilder.pageAgentChat(onBackClick: () -> Unit) {
    composable(route = AGENT_CHAT) {
        it.arguments?.apply {
            val prompt = getString(KEY_AGENT_CHAT_PROMPT, "")
            AgentChatPageRoute(
                prompt = prompt,
                onBackClick = onBackClick
            )
        }
    }
}