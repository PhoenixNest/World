package io.dev.relic.feature.pages.detail.gallery

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.dev.relic.feature.pages.detail.gallery.ui.GalleryDetailPageContent

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GalleryDetailPageRoute(
    id: Int?,
    originalImageUrl: String?,
    author: String?,
    authorAvatarUrl: String?,
    authorPageUrl: String?,
    shareTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val sharedContentState = shareTransitionScope.rememberSharedContentState(key = "image$id")

    with(shareTransitionScope) {
        GalleryDetailPage(
            id = id,
            originalImageUrl = originalImageUrl,
            authorName = author,
            authorAvatarUrl = authorAvatarUrl,
            authorPageUrl = authorPageUrl,
            onBackClick = onBackClick,
            onSetWallpaperClick = {
                // WallpaperManager.setWallpaper()
            },
            onSetBothClick = {
                // WallpaperManager.setWallpaper()
            },
            modifier = Modifier.sharedBounds(
                sharedContentState = sharedContentState,
                animatedVisibilityScope = animatedContentScope
            )
        )
    }
}

@Composable
private fun GalleryDetailPage(
    id: Int?,
    originalImageUrl: String?,
    authorName: String?,
    authorAvatarUrl: String?,
    authorPageUrl: String?,
    onBackClick: () -> Unit,
    onSetWallpaperClick: () -> Unit,
    onSetBothClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    GalleryDetailPageContent(
        id = id,
        originalImageUrl = originalImageUrl,
        authorName = authorName,
        authorAvatarUrl = authorAvatarUrl,
        authorPageUrl = authorPageUrl,
        onBackClick = onBackClick,
        onSetWallpaperClick = onSetWallpaperClick,
        onSetBothClick = onSetBothClick,
        modifier = modifier
    )
}

@Composable
@Preview(showBackground = true)
private fun GalleryDetailPageContentPreview() {
    GalleryDetailPageContent(
        id = -1,
        originalImageUrl = "",
        authorName = "",
        authorAvatarUrl = "",
        authorPageUrl = "",
        onBackClick = {},
        onSetWallpaperClick = {},
        onSetBothClick = {}
    )
}