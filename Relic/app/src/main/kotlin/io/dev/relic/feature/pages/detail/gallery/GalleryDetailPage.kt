package io.dev.relic.feature.pages.detail.gallery

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.data.model.pixabay.PixabayDataModel
import io.dev.relic.feature.pages.detail.gallery.ui.GalleryDetailPageContent

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GalleryDetailPageRoute(
    model: PixabayDataModel,
    shareTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val sharedContentState = shareTransitionScope
        .rememberSharedContentState(key = "image${model.id}")

    var isShowPreview by remember {
        mutableStateOf(false)
    }

    with(shareTransitionScope) {
        Box(modifier = Modifier.fillMaxSize()){
            GalleryDetailPage(
                model = model,
                isShowPreview = isShowPreview,
                onBackClick = onBackClick,
                onPreviewClick = {
                    isShowPreview = !isShowPreview
                },
                modifier = Modifier.sharedBounds(
                    sharedContentState = sharedContentState,
                    animatedVisibilityScope = animatedContentScope
                )
            )
        }
    }
}

@Composable
private fun GalleryDetailPage(
    model: PixabayDataModel,
    isShowPreview: Boolean,
    onBackClick: () -> Unit,
    onPreviewClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    GalleryDetailPageContent(
        model = model,
        isShowPreview = isShowPreview,
        onBackClick = onBackClick,
        onPreviewClick = onPreviewClick,
        modifier = modifier
    )
}

@Composable
@Preview(showBackground = true)
private fun GalleryDetailPageContentPreview() {
    GalleryDetailPageContent(
        model = PixabayDataModel(
            id = null,
            previewImageUrl = null,
            previewImageWidth = null,
            previewImageHeight = null,
            webFormatImageUrl = null,
            webFormatImageWidth = null,
            webFormatImageHeight = null,
            originalImageUrl = null,
            originalImageWidth = null,
            originalImageHeight = null,
            author = null,
            authorAvatarUrl = null,
            authorPageUrl = null,
            likes = null
        ),
        isShowPreview = false,
        onBackClick = {},
        onPreviewClick = {}
    )
}