package io.dev.relic.feature.function.gallery.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.common.util.LogUtil
import io.data.dto.pixabay.PixabayImagesDTO
import io.data.mappers.PixabayDataMapper.toModelList
import io.data.model.NetworkResult
import io.data.model.pixabay.PixabayDataModel
import io.domain.use_case.pixabay.PixabayUseCase

/**
 * Reference docs:
 *
 * [Paging library overview](https://developer.android.google.cn/topic/libraries/architecture/paging/v3-overview)
 * */
class GalleryPagingSource(
    private val pixabayUseCase: PixabayUseCase,
    private val keyWords: String,
    private val language: String,
    private val imageType: String,
    private val orientation: String,
    private val category: String,
    private val isEditorsChoice: Boolean,
    private val isSafeSearch: Boolean,
    private val orderBy: String,
    private val perPage: Int
) : PagingSource<Int, PixabayDataModel>() {

    companion object {
        private const val TAG = "GalleryPagingSource"
    }

    /**
     * Provide a [Key] used for the initial [load] for the next [PagingSource] due to invalidation
     * of this [PagingSource]. The [Key] is provided to [load] via [LoadParams.key].
     *
     * The [Key] returned by this method should cause [load] to load enough items to
     * fill the viewport *around* the last accessed position, allowing the next generation to
     * transparently animate in. The last accessed position can be retrieved via
     * [state.anchorPosition][PagingState.anchorPosition], which is typically
     * the *top-most* or *bottom-most* item in the viewport due to access being triggered by binding
     * items as they scroll into view.
     *
     * For example, if items are loaded based on integer position keys, you can return
     * `( (state.anchorPosition ?: 0) - state.config.initialLoadSize / 2).coerceAtLeast(0)`.
     *
     * Alternately, if items contain a key used to load, get the key from the item in the page at
     * index [state.anchorPosition][PagingState.anchorPosition] then try to center it based on
     * `state.config.initialLoadSize`.
     *
     * @param state [PagingState] of the currently fetched data, which includes the most recently
     * accessed position in the list via [PagingState.anchorPosition].
     *
     * @return [Key] passed to [load] after invalidation used for initial load of the next
     * generation. The [Key] returned by [getRefreshKey] should load pages centered around
     * user's current viewport. If the correct [Key] cannot be determined, `null` can be returned
     * to allow [load] decide what default key to use.
     */
    override fun getRefreshKey(
        state: PagingState<Int, PixabayDataModel>
    ): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    /**
     * Loading API for [PagingSource].
     *
     * Implement this method to trigger your async load (e.g. from database or network).
     */
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, PixabayDataModel> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPageIndex = params.key ?: 1
            val result = pixabayUseCase.searchImages.invoke(
                keyWords = keyWords,
                language = language,
                imageType = imageType,
                orientation = orientation,
                category = category,
                isEditorsChoice = isEditorsChoice,
                isSafeSearch = isSafeSearch,
                orderBy = orderBy,
                page = nextPageIndex,
                perPage = perPage
            )

            handlePixabayNetworkResult(nextPageIndex, result)
        } catch (exception: Exception) {
            // Handle errors in this block and return LoadResult.Error for
            // expected errors (such as a network failure).
            LoadResult.Error<Int, PixabayDataModel>(exception)
        }
    }

    /**
     * Handle the remote-data of Gallery information.
     *
     * @param result
     * */
    private fun handlePixabayNetworkResult(
        nextPageIndex: Int,
        result: NetworkResult<PixabayImagesDTO>
    ): LoadResult<Int, PixabayDataModel> {
        var loadResult: LoadResult<Int, PixabayDataModel> = LoadResult.Invalid()
        when (result) {
            is NetworkResult.Loading -> {
                //
            }

            is NetworkResult.Success -> {
                result.data?.also { dto ->
                    LogUtil.d(TAG, "[Handle Gallery Data] Succeed, data: $dto")
                    val modelList = dto.toModelList()
                    val filteredModelList = modelList.filterNotNull()
                    loadResult = LoadResult.Page(filteredModelList, null, nextPageIndex)
                } ?: {
                    LogUtil.d(TAG, "[Handle Gallery Data] Succeed without data")
                }
            }

            is NetworkResult.Failed -> {
                val errorCode = result.code
                val errorMessage = result.message
                LogUtil.e(TAG, "[Handle Gallery Data] Failed, ($errorCode, $errorMessage)")
            }
        }

        return loadResult
    }
}