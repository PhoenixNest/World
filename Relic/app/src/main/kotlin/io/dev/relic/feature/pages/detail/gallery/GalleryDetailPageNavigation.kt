package io.dev.relic.feature.pages.detail.gallery

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import io.common.RelicConstants.Common.UNKNOWN_VALUE_INT
import io.common.RelicConstants.URL.DEFAULT_PLACEHOLDER_URL
import io.dev.relic.feature.route.RelicRoute.DETAIL_GALLERY

private const val KEY_PIXABAY_ID = "key_pixabay_id"
private const val KEY_PIXABAY_ORIGINAL_IMAGE_URL = "key_pixabay_original_image_url"
private const val KEY_PIXABAY_AUTHOR = "key_pixabay_author"
private const val KEY_PIXABAY_AUTHOR_AVATAR_URL = "key_pixabay_author_avatar_url"
private const val KEY_PIXABAY_AUTHOR_PAGE_URL = "key_pixabay_author_page_url"

@SuppressLint("RestrictedApi")
fun NavController.navigateToGalleryDetailPage(
    id: Int?,
    originalImageUrl: String?,
    author: String?,
    authorAvatarUrl: String?,
    authorPageUrl: String?,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val deepLinkRequest = NavDeepLinkRequest.Builder.fromUri(
        NavDestination.createRoute(DETAIL_GALLERY).toUri()
    ).build()

    graph.matchDeepLink(deepLinkRequest)?.let { deepLinkMatch ->
        val bundle = Bundle().apply {
            putInt(KEY_PIXABAY_ID, id ?: -1)
            putString(KEY_PIXABAY_ORIGINAL_IMAGE_URL, originalImageUrl)
            putString(KEY_PIXABAY_AUTHOR, author)
            putString(KEY_PIXABAY_AUTHOR_AVATAR_URL, authorAvatarUrl)
            putString(KEY_PIXABAY_AUTHOR_PAGE_URL, authorPageUrl)
        }

        this.navigate(
            resId = deepLinkMatch.destination.id,
            args = bundle,
            navOptions = navOptions,
            navigatorExtras = navigatorExtras
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.pageGalleryDetail(
    shareTransitionScope: SharedTransitionScope,
    onBackClick: () -> Unit
) {
    composable(
        route = DETAIL_GALLERY,
        enterTransition = {
            fadeIn()
        },
        exitTransition = {
            fadeOut()
        },
        popEnterTransition = {
            fadeIn()
        },
        popExitTransition = {
            fadeOut()
        }
    ) {
        val animatedContentScope = this

        it.arguments?.apply {
            val id = getInt(KEY_PIXABAY_ID, UNKNOWN_VALUE_INT)
            val originalImageUrl = getString(KEY_PIXABAY_ORIGINAL_IMAGE_URL, DEFAULT_PLACEHOLDER_URL)
            val author = getString(KEY_PIXABAY_AUTHOR, DEFAULT_PLACEHOLDER_URL)
            val authorAvatarUrl = getString(KEY_PIXABAY_AUTHOR_AVATAR_URL, DEFAULT_PLACEHOLDER_URL)
            val authorPageUrl = getString(KEY_PIXABAY_AUTHOR_PAGE_URL, DEFAULT_PLACEHOLDER_URL)
            GalleryDetailPageRoute(
                id = id,
                originalImageUrl = originalImageUrl,
                author = author,
                authorAvatarUrl = authorAvatarUrl,
                authorPageUrl = authorPageUrl,
                shareTransitionScope = shareTransitionScope,
                animatedContentScope = animatedContentScope,
                onBackClick = onBackClick,
            )
        }
    }
}