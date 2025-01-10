package io.dev.relic.feature.function.gallery.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.data.model.pixabay.PixabayDataModel
import io.dev.relic.feature.function.gallery.paging.GalleryPagingSource
import io.dev.relic.feature.function.gallery.util.WallpaperOrientation
import io.domain.use_case.pixabay.PixabayUseCase
import kotlinx.coroutines.flow.Flow
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    application: Application,
    private val pixabayUseCase: PixabayUseCase
) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "GalleryViewModel"

        private val DEFAULT_ORIENTATION = WallpaperOrientation.VERTICAL
        private const val DEFAULT_KEY_WORDS = "wallpaper"
        private const val DEFAULT_IMAGE_TYPE = "photo"
        private const val DEFAULT_IMAGE_CATEGORY = "nature"
        private const val DEFAULT_IS_EDITORS_CHOICE = false
        private const val DEFAULT_IS_SAFE_SEARCH = true
        private const val DEFAULT_ORDER_RULE = "popular"
        private const val DEFAULT_START_PAGE = 1
        private const val DEFAULT_RESULT_SIZE_PER_PAGE = 20
    }

    fun getGalleryPager(
        imageOrientation: WallpaperOrientation = DEFAULT_ORIENTATION
    ): Flow<PagingData<PixabayDataModel>> {
        return Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            config = PagingConfig(pageSize = DEFAULT_RESULT_SIZE_PER_PAGE),
            pagingSourceFactory = {
                GalleryPagingSource(
                    pixabayUseCase = pixabayUseCase,
                    keyWords = DEFAULT_KEY_WORDS,
                    language = Locale.getDefault().toString().lowercase(),
                    imageType = DEFAULT_IMAGE_TYPE,
                    orientation = imageOrientation.name.lowercase(),
                    category = DEFAULT_IMAGE_CATEGORY,
                    isEditorsChoice = DEFAULT_IS_EDITORS_CHOICE,
                    isSafeSearch = DEFAULT_IS_SAFE_SEARCH,
                    orderBy = DEFAULT_ORDER_RULE,
                    perPage = DEFAULT_RESULT_SIZE_PER_PAGE
                )
            }
        ).flow.cachedIn(viewModelScope)
    }
}