package io.dev.relic.feature.function.gallery.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.ext.ViewModelExt.operationInViewModelScope
import io.dev.relic.feature.function.gallery.util.WallpaperOrientation
import io.domain.use_case.wallpaper.WallpaperUseCase
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    application: Application,
    private val wallpaperUseCase: WallpaperUseCase
) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "GalleryViewModel"

        private val DEFAULT_ORIENTATION = WallpaperOrientation.VERTICAL
        private const val DEFAULT_KEY_WORDS = "flower"
        private const val DEFAULT_IMAGE_TYPE = "photo"
        private const val DEFAULT_IMAGE_CATEGORY = "nature"
        private const val DEFAULT_IS_EDITORS_CHOICE = true
        private const val DEFAULT_IS_SAFE_SEARCH = true
        private const val DEFAULT_ORDER_RULE = "popular"
        private const val DEFAULT_START_PAGE = 1
        private const val DEFAULT_RESULT_SIZE_PER_PAGE = 20
    }

    fun fetchData(
        currentPage: Int = DEFAULT_START_PAGE,
        imageOrientation: WallpaperOrientation = DEFAULT_ORIENTATION
    ) {
        operationInViewModelScope {
            wallpaperUseCase.searchImages.invoke(
                keyWords = DEFAULT_KEY_WORDS,
                language = Locale.getDefault().toString().lowercase(),
                imageType = DEFAULT_IMAGE_TYPE,
                orientation = imageOrientation.name.lowercase(),
                category = DEFAULT_IMAGE_CATEGORY,
                isEditorsChoice = DEFAULT_IS_EDITORS_CHOICE,
                isSafeSearch = DEFAULT_IS_SAFE_SEARCH,
                orderBy = DEFAULT_ORDER_RULE,
                page = currentPage,
                perPage = DEFAULT_RESULT_SIZE_PER_PAGE
            )
        }
    }
}