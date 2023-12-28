package io.dev.relic.feature.pages.detail.news

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
import io.common.RelicConstants.Common.ANDROID
import io.common.RelicConstants.URL.DEFAULT_PLACEHOLDER_URL
import io.dev.relic.feature.route.RelicRoute.DETAIL_NEWS

private const val KEY_NEWS_TITLE = "keys_news_title"
private const val KEY_NEWS_CONTENT_URL = "key_news_content_url"

@SuppressLint("RestrictedApi")
fun NavController.navigateToNewsDetailPage(
    title: String? = null,
    contUrl: String? = null,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val deepLinkRequest: NavDeepLinkRequest = NavDeepLinkRequest.Builder.fromUri(
        NavDestination.createRoute(DETAIL_NEWS).toUri()
    ).build()

    graph.matchDeepLink(deepLinkRequest)?.let { deepLinkMatch: NavDestination.DeepLinkMatch ->
        val bundle: Bundle = Bundle().apply {
            putString(KEY_NEWS_TITLE, title)
            putString(KEY_NEWS_CONTENT_URL, contUrl)
        }

        this.navigate(
            resId = deepLinkMatch.destination.id,
            args = bundle,
            navOptions = navOptions,
            navigatorExtras = navigatorExtras
        )
    }
}

fun NavGraphBuilder.pageNewsDetail(onBackClick: () -> Unit) {
    composable(route = DETAIL_NEWS) {
        it.arguments?.apply {
            val title: String = getString(KEY_NEWS_TITLE, ANDROID)
            val contentUrl: String = getString(KEY_NEWS_CONTENT_URL, DEFAULT_PLACEHOLDER_URL)
            NewsDetailPageRoute(
                title = title,
                contentUrl = contentUrl,
                onBackClick = onBackClick
            )
        }
    }
}