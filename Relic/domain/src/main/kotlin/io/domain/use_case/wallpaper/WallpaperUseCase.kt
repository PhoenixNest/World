package io.domain.use_case.wallpaper

import io.domain.use_case.wallpaper.action.SearchImages

internal const val TAG = "WallpaperUseCase"

data class WallpaperUseCase(
    val searchImages: SearchImages
)