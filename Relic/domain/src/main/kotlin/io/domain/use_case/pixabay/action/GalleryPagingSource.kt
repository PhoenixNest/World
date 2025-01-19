package io.domain.use_case.pixabay.action

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import io.common.util.LogUtil
import io.core.network.NetworkParameters.Keys.PIXABAY_API_KEY
import io.data.mappers.PixabayDataMapper.toModelList
import io.data.model.NetworkResult
import io.data.model.pixabay.PixabayDataModel
import io.domain.repository.IPixabayDataRepository
import okio.IOException

/**
 * Reference docs:
 *
 * [Paging library overview](https://developer.android.google.cn/topic/libraries/architecture/paging/v3-overview)
 * */
class GalleryPagingSource(
    private val repository: IPixabayDataRepository,
    private val keyWords: String,
    private val language: String,
    private val imageType: String,
    private val orientation: String,
    private val category: String,
    private val isEditorsChoice: Boolean,
    private val isSafeSearch: Boolean,
    private val orderBy: String,
    private val startPage: Int,
    private val perPage: Int
) : PagingSource<Int, PixabayDataModel>() {

    companion object {
        private const val TAG = "Gallery_Paging"
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
        //  * both prevKey and nextKey are null -> anchorPage is the initial page, so return null.
        val refreshKey = state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

        LogUtil.d(TAG, "[Refresh Key] Current refresh key: $refreshKey")
        return refreshKey
    }

    /**
     * Loading API for [PagingSource].
     *
     * Implement this method to trigger your async load (e.g. from database or network).
     */
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, PixabayDataModel> {
        LogUtil.d(TAG, "[Load] Current params: (key: ${params.key}, loadSize: ${params.loadSize})")
        return try {
            // Start refresh at page 1 if undefined.
            val pageIndex = params.key ?: startPage
            val result = repository.searchImages(
                apiKey = PIXABAY_API_KEY,
                keyWords = keyWords,
                language = language,
                imageType = imageType,
                orientation = orientation,
                category = category,
                isEditorsChoice = isEditorsChoice,
                isSafeSearch = isSafeSearch,
                orderBy = orderBy,
                page = pageIndex,
                perPage = perPage
            )

            val preKey = if (params.key == 1) null else params.key?.minus(1)
            val nextKey = params.key?.plus(1)

            var loadResult: LoadResult<Int, PixabayDataModel> = LoadResult.Invalid()
            when (result) {
                is NetworkResult.Loading -> {
                    //
                }

                is NetworkResult.Success -> {
                    result.data?.also { dto ->
                        LogUtil.d(TAG, "[Handle Remote Data] Succeed, data: $dto")
                        val modelList = dto.toModelList()
                        val filteredModelList = modelList.filterNotNull()

                        loadResult = LoadResult.Page(
                            data = filteredModelList,
                            prevKey = preKey,
                            nextKey = nextKey
                        )
                    } ?: {
                        LogUtil.w(TAG, "[Handle Remote Data] Succeed without data")
                        loadResult = LoadResult.Page(
                            data = emptyList(),
                            prevKey = preKey,
                            nextKey = null
                        )
                    }
                }

                is NetworkResult.Failed -> {
                    val errorCode = result.code
                    val errorMessage = result.message
                    LogUtil.e(TAG, "[Handle Remote Data] Failed, ($errorCode, $errorMessage)")
                }
            }

            return loadResult
        } catch (exception: HttpException) {
            // Handle errors in this block and return LoadResult.Error for
            // expected errors (such as a network failure).
            LoadResult.Error(exception)
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}