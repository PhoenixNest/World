package io.dev.relic.feature.pages.gallery

import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.common.util.LogUtil
import io.data.model.wallpaper.WallpaperImagesDataModel
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.gallery.GalleryDataState
import io.dev.relic.feature.function.gallery.viewmodel.GalleryViewModel
import io.dev.relic.feature.pages.gallery.ui.GalleryPageContent
import io.dev.relic.feature.screens.main.MainScreenState
import kotlinx.coroutines.flow.filter

@Composable
fun GalleryPageRoute(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    galleryViewModel: GalleryViewModel,
    onBackClick: () -> Unit
) {

    /* ======================== Common ======================== */

    val context = LocalContext.current
    val localFocusManager = LocalFocusManager.current
    val coroutineScope = mainScreenState.coroutineScope
    val navController = mainScreenState.navHostController

    /* ======================== Field ======================== */

    val galleryDataState by galleryViewModel.galleryDataStateFlow
        .collectAsStateWithLifecycle()

    /* ======================== Ui ======================== */

    val galleryListState = GalleryListState(stagedGridState = rememberLazyStaggeredGridState())

    /* ======================== Ui State ======================== */

    val galleryState = buildGalleryState(
        dataState = galleryDataState,
        viewModel = galleryViewModel,
        listState = galleryListState,
        onCheckDetail = {
            //
        }
    )

    LaunchedEffect(galleryListState.stagedGridState) {
        snapshotFlow {
            galleryListState.stagedGridState.firstVisibleItemIndex
        }.filter {
            it >= (galleryViewModel.getGalleryList().size / 2)
        }.collect {
            if (galleryViewModel.isFetchingMore) {
                LogUtil.w("GalleryViewModel", "[Fetch More Gallery Data] Already executed, skip this time.")
                return@collect
            }

            // Fetch more gallery data.
            galleryViewModel.apply {
                if (canFetchMore) {
                    fetchMoreGalleryData()
                }
            }
        }
    }

    GalleryPage(galleryState, onBackClick)
}

@Composable
private fun GalleryPage(galleryState: GalleryState, onBackClick: () -> Unit) {
    GalleryPageContent(
        galleryState = galleryState,
        onBackClick = onBackClick
    )
}

@Preview
@Composable
private fun GalleryPagePreview() {
    GalleryPage(
        GalleryState(
            dataState = GalleryDataState.Init,
            action = GalleryAction(
                onItemClick = {},
                onRetryClick = {}
            ),
            listState = GalleryListState(stagedGridState = rememberLazyStaggeredGridState())
        ),
        onBackClick = {}
    )
}

/* ======================== Page Ui State Builder ======================== */

private fun buildGalleryState(
    dataState: GalleryDataState,
    viewModel: GalleryViewModel,
    listState: GalleryListState,
    onCheckDetail: (dataModel: WallpaperImagesDataModel) -> Unit
): GalleryState {
    return GalleryState(
        dataState = dataState,
        action = GalleryAction(
            onItemClick = onCheckDetail,
            onRetryClick = { viewModel.getGalleryData() }
        ),
        listState = listState
    )
}