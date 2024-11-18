package io.dev.relic.feature.pages.gallery

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.navOptions
import io.common.util.LogUtil
import io.data.model.pixabay.PixabayDataModel
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.gallery.GalleryDataState
import io.dev.relic.feature.function.gallery.viewmodel.GalleryViewModel
import io.dev.relic.feature.pages.detail.gallery.navigateToGalleryDetailPage
import io.dev.relic.feature.pages.gallery.ui.GalleryPageContent
import io.dev.relic.feature.screens.main.MainScreenState
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GalleryPageRoute(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    galleryViewModel: GalleryViewModel,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBackClick: () -> Unit
) {

    /* ======================== Common ======================== */

    val context = LocalContext.current
    val localFocusManager = LocalFocusManager.current
    val coroutineScope = mainScreenState.coroutineScope
    val navController = mainScreenState.navHostController

    /* ======================== Field ======================== */

    val galleryDataState by galleryViewModel.getGalleryDataFlow()
        .collectAsStateWithLifecycle()

    /* ======================== Ui ======================== */

    val galleryListState = GalleryListState(lazyStaggeredGridState = rememberLazyStaggeredGridState())

    /* ======================== Ui State ======================== */

    val galleryState = buildGalleryState(
        dataState = galleryDataState,
        viewModel = galleryViewModel,
        listState = galleryListState,
        onItemClick = { model ->
            navController.navigateToGalleryDetailPage(
                id = model.id,
                originalImageUrl = model.originalImageUrl,
                author = model.author,
                authorAvatarUrl = model.authorAvatarUrl,
                authorPageUrl = model.authorPageUrl,
                navOptions = navOptions {
                    launchSingleTop = true
                    restoreState = true
                }
            )
        }
    )

    LaunchedEffect(galleryListState.lazyStaggeredGridState) {
        snapshotFlow {
            galleryListState.lazyStaggeredGridState.firstVisibleItemIndex
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

    GalleryPage(
        galleryState = galleryState,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun GalleryPage(
    galleryState: GalleryState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBackClick: () -> Unit
) {
    GalleryPageContent(
        galleryState = galleryState,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        onBackClick = onBackClick
    )
}

/* ======================== Page Ui State Builder ======================== */

private fun buildGalleryState(
    dataState: GalleryDataState,
    viewModel: GalleryViewModel,
    listState: GalleryListState,
    onItemClick: (dataModel: PixabayDataModel) -> Unit
): GalleryState {
    return GalleryState(
        dataState = dataState,
        action = GalleryAction(
            onItemClick = onItemClick,
            onRetryClick = { viewModel.getGalleryData() }
        ),
        listState = listState
    )
}