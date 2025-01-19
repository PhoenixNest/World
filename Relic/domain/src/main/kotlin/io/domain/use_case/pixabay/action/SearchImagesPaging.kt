package io.domain.use_case.pixabay.action

import androidx.paging.Pager
import androidx.paging.PagingConfig
import io.data.model.pixabay.PixabayDataModel
import io.domain.repository.IPixabayDataRepository
import io.domain.use_case.pixabay.action.util.WallpaperOrientation
import java.util.Locale
import javax.inject.Inject

class SearchImagesPaging @Inject constructor(
    private val pixabayDataRepository: IPixabayDataRepository
) {

    companion object {
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

    operator fun invoke(
        imageOrientation: WallpaperOrientation = DEFAULT_ORIENTATION
    ): Pager<Int, PixabayDataModel> {
        return Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            config = PagingConfig(
                pageSize = DEFAULT_RESULT_SIZE_PER_PAGE,
                enablePlaceholders = true
            ),
            initialKey = DEFAULT_START_PAGE,
            pagingSourceFactory = {
                GalleryPagingSource(
                    repository = pixabayDataRepository,
                    keyWords = DEFAULT_KEY_WORDS,
                    language = Locale.getDefault().toString().lowercase(),
                    imageType = DEFAULT_IMAGE_TYPE,
                    orientation = imageOrientation.name.lowercase(),
                    category = DEFAULT_IMAGE_CATEGORY,
                    isEditorsChoice = DEFAULT_IS_EDITORS_CHOICE,
                    isSafeSearch = DEFAULT_IS_SAFE_SEARCH,
                    orderBy = DEFAULT_ORDER_RULE,
                    startPage = DEFAULT_START_PAGE,
                    perPage = DEFAULT_RESULT_SIZE_PER_PAGE
                )
            }
        )
    }
}