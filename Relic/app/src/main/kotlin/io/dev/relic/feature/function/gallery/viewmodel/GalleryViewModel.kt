package io.dev.relic.feature.function.gallery.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.ext.ViewModelExt.operationInViewModelScope
import io.common.ext.ViewModelExt.setState
import io.common.util.LogUtil
import io.data.dto.pixabay.PixabayImagesDTO
import io.data.mappers.PixabayDataMapper.toModelList
import io.data.model.NetworkResult
import io.data.model.pixabay.PixabayDataModel
import io.dev.relic.feature.function.gallery.GalleryDataState
import io.dev.relic.feature.function.gallery.paging.GalleryPagingSource
import io.dev.relic.feature.function.gallery.util.WallpaperOrientation
import io.domain.use_case.pixabay.PixabayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    application: Application,
    private val pixabayUseCase: PixabayUseCase
) : AndroidViewModel(application) {

    /**
     * Indicate the status of fetch more.
     * */
    var isFetchingMore = false

    /**
     * Indicate the available status of fetch more.
     * */
    var canFetchMore = true

    /**
     * The number of results to skip (between 0 and 900).
     * */
    private var currentGalleryPageIndex = DEFAULT_START_PAGE

    /**
     * The Gallery data flow.
     * */
    private val _galleryDataStateFlow = MutableStateFlow<GalleryDataState>(GalleryDataState.Init)
    val galleryDataStateFlow: StateFlow<GalleryDataState> get() = _galleryDataStateFlow

    /**
     * Memory cache list of gallery data.
     * */
    private val galleryDataList = mutableListOf<PixabayDataModel>()

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

    init {
        getGalleryData()
    }

    fun getGalleryPager(imageOrientation: WallpaperOrientation = DEFAULT_ORIENTATION) {
        operationInViewModelScope {
            Pager(
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

    fun fetchMoreGalleryData() {
        if (!canFetchMore) {
            LogUtil.w(TAG, "[Fetch More Gallery Data] Can't get more data from server, skip.")
            return
        }

        val newPageIndex = currentGalleryPageIndex + 1
        currentGalleryPageIndex = newPageIndex

        operationInViewModelScope {
            isFetchingMore = true
            getGalleryData(
                pageIndex = newPageIndex,
                isFetchMore = true
            )
        }
    }

    fun getGalleryData(
        pageIndex: Int = DEFAULT_START_PAGE,
        imageOrientation: WallpaperOrientation = DEFAULT_ORIENTATION,
        isFetchMore: Boolean = false
    ) {
        val resultFlow = flow {
            val result = pixabayUseCase.searchImages.invoke(
                keyWords = DEFAULT_KEY_WORDS,
                language = Locale.getDefault().toString().lowercase(),
                imageType = DEFAULT_IMAGE_TYPE,
                orientation = imageOrientation.name.lowercase(),
                category = DEFAULT_IMAGE_CATEGORY,
                isEditorsChoice = DEFAULT_IS_EDITORS_CHOICE,
                isSafeSearch = DEFAULT_IS_SAFE_SEARCH,
                orderBy = DEFAULT_ORDER_RULE,
                page = pageIndex,
                perPage = DEFAULT_RESULT_SIZE_PER_PAGE
            )
            emit(result)
        }.flowOn(Dispatchers.IO)

        operationInViewModelScope { scope ->
            resultFlow.stateIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(5 * 1000L),
                initialValue = NetworkResult.Loading()
            ).collect { result ->
                handleRemoteGalleryData(
                    result = result,
                    isFetchMore = isFetchMore
                )
            }
        }
    }

    fun getGalleryList(): List<PixabayDataModel> {
        return galleryDataList.toList()
    }

    fun resetGalleryPageIndex() {
        currentGalleryPageIndex = 0
    }

    fun resetCanFetchMoreStatus() {
        canFetchMore = true
    }

    /**
     * Handle the remote-data of Gallery information.
     *
     * @param result
     * */
    private fun handleRemoteGalleryData(
        result: NetworkResult<PixabayImagesDTO>,
        isFetchMore: Boolean = false
    ) {
        when (result) {
            is NetworkResult.Loading -> {
                if (isFetchMore) {
                    LogUtil.d(TAG, "[Handle Gallery Data - Fetch more] Loading...")
                    // setState(dataFlow, GalleryDataState.FetchingMore)
                } else {
                    LogUtil.d(TAG, "[Handle Gallery Data] Loading...")
                    setState(_galleryDataStateFlow, GalleryDataState.Fetching)
                }
            }

            is NetworkResult.Success -> {
                result.data?.also { dto ->
                    LogUtil.d(TAG, "[Handle Gallery Data] Succeed, data: $dto")
                    val modelList = dto.toModelList()
                    val filteredModelList = modelList.filterNotNull()

                    if (isFetchMore && filteredModelList.isEmpty()) {
                        LogUtil.w(TAG, "[Fetch More Gallery Data] Server data is depleted, we can't get anymore sir.")
                        currentGalleryPageIndex -= 1
                        isFetchingMore = false
                        canFetchMore = false
                        return
                    }

                    galleryDataList.addAll(filteredModelList)
                    setState(_galleryDataStateFlow, GalleryDataState.FetchSucceed(galleryDataList.toList()))
                    isFetchingMore = false
                } ?: {
                    LogUtil.d(TAG, "[Handle Gallery Data] Succeed without data")
                    setState(_galleryDataStateFlow, GalleryDataState.NoImageData)
                    isFetchingMore = false
                }
            }

            is NetworkResult.Failed -> {
                val errorCode = result.code
                val errorMessage = result.message
                LogUtil.e(TAG, "[Handle Gallery Data] Failed, ($errorCode, $errorMessage)")
                setState(_galleryDataStateFlow, GalleryDataState.FetchFailed(errorCode, errorMessage))
                isFetchingMore = false
            }
        }
    }
}