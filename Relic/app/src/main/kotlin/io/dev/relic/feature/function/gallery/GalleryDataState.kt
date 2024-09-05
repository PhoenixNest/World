package io.dev.relic.feature.function.gallery

import io.data.model.wallpaper.WallpaperImagesDataModel

sealed interface GalleryDataState {

    /* Common */

    data object Init : GalleryDataState

    data object Empty : GalleryDataState

    data object NoImageData : GalleryDataState

    /* Loading */

    data object Fetching : GalleryDataState

    /* Succeed */

    data class FetchSucceed(
        val modelList: List<WallpaperImagesDataModel?>
    ) : GalleryDataState

    /* Failed */

    data class FetchFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : GalleryDataState
}