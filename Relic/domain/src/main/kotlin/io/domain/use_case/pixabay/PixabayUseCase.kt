package io.domain.use_case.pixabay

import io.domain.use_case.pixabay.action.SearchImages

internal const val TAG = "PixabayUseCase"

data class PixabayUseCase(
    val searchImages: SearchImages
)