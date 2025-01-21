package io.dev.relic.feature.pages.detail.gallery

import android.content.Intent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.common.util.ToastUtil
import io.core.ui.theme.RelicAppTheme
import io.data.model.pixabay.PixabayDataModel
import io.dev.relic.feature.pages.detail.gallery.ui.GalleryDetailPageContent
import io.dev.relic.feature.pages.detail.gallery.ui.GalleryDetailSetterSheet
import io.dev.relic.feature.pages.detail.gallery.ui.GalleryDetailSuccessSheet
import io.dev.relic.feature.pages.detail.gallery.vm.GalleryDetailViewModel
import io.module.media.wallpaper.WallpaperManager
import io.module.media.wallpaper.WallpaperType

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GalleryDetailPageRoute(
    model: PixabayDataModel,
    shareTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBackClick: () -> Unit,
    viewModel: GalleryDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val sharedContentState = shareTransitionScope
        .rememberSharedContentState(key = "image${model.id}")

    val galleryImageBitmap by viewModel.getImageDataFlow()
        .collectAsStateWithLifecycle()

    var isShowPreview by remember {
        mutableStateOf(false)
    }

    var isShowSetterSheetDialog by remember {
        mutableStateOf(false)
    }

    var isShowSuccessSheetDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.downloadImageUrl(model.originalImageUrl)
    }

    with(shareTransitionScope) {
        Box(modifier = Modifier.fillMaxSize()) {
            GalleryDetailPage(
                model = model,
                isShowPreview = isShowPreview,
                isShowSetterSheetDialog = isShowSetterSheetDialog,
                isShowSuccessSheetDialog = isShowSuccessSheetDialog,
                onBackClick = onBackClick,
                onPreviewClick = {
                    isShowPreview = !isShowPreview
                },
                onOpenSetterSheetClick = {
                    isShowSetterSheetDialog = true
                },
                onSet2HomeClick = {
                    val bitmap = galleryImageBitmap
                    if (bitmap == null) {
                        ToastUtil.showToast(io.module.media.R.string.wallpaper_setter_failed)
                        return@GalleryDetailPage
                    }

                    WallpaperManager.setBitmapWallpaper(context, bitmap)
                    isShowSetterSheetDialog = false
                    isShowSuccessSheetDialog = true
                },
                onSetBothClick = {
                    val bitmap = galleryImageBitmap
                    if (bitmap == null) {
                        ToastUtil.showToast(io.module.media.R.string.wallpaper_setter_failed)
                        return@GalleryDetailPage
                    }

                    WallpaperManager.setBitmapWallpaper(context, bitmap)
                    WallpaperManager.setBitmapWallpaper(context, bitmap, WallpaperType.LOCK_SCREEN)
                    isShowSetterSheetDialog = false
                    isShowSuccessSheetDialog = true
                },
                onGo2DesktopClick = {
                    context.startActivity(
                        Intent(Intent.ACTION_MAIN).apply {
                            addCategory(Intent.CATEGORY_HOME)
                        }
                    )
                },
                onDismissSetterSheet = {
                    isShowSetterSheetDialog = false
                },
                onDismissSuccessSheet = {
                    isShowSuccessSheetDialog = false
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
    isShowSetterSheetDialog: Boolean,
    isShowSuccessSheetDialog: Boolean,
    onBackClick: () -> Unit,
    onPreviewClick: () -> Unit,
    onOpenSetterSheetClick: () -> Unit,
    onSet2HomeClick: () -> Unit,
    onSetBothClick: () -> Unit,
    onGo2DesktopClick: () -> Unit,
    onDismissSetterSheet: () -> Unit,
    onDismissSuccessSheet: () -> Unit,
    modifier: Modifier = Modifier
) {
    GalleryDetailPageContent(
        model = model,
        isShowPreview = isShowPreview,
        onBackClick = onBackClick,
        onPreviewClick = onPreviewClick,
        onOpenSetterSheetClick = onOpenSetterSheetClick,
        modifier = modifier
    )
    GalleryDetailSetterSheet(
        isShow = isShowSetterSheetDialog,
        onSetHomeClick = onSet2HomeClick,
        onSetBothClick = onSetBothClick,
        onDismissRequest = onDismissSetterSheet
    )
    GalleryDetailSuccessSheet(
        isShow = isShowSuccessSheetDialog,
        onGo2DesktopClick = onGo2DesktopClick,
        onDismissRequest = onDismissSuccessSheet
    )
}

@Composable
@Preview(showBackground = true)
private fun GalleryDetailPageContentPreview() {
    RelicAppTheme {
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
            onPreviewClick = {},
            onOpenSetterSheetClick = {}
        )
    }
}