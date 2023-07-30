package io.dev.relic.domain.use_case.food_receipes.action.complex_search

import io.dev.relic.core.data.network.api.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.dev.relic.core.data.network.monitor.IFetchDataMonitor
import io.dev.relic.domain.model.NetworkResult
import io.dev.relic.domain.repository.IFoodRecipesDataRepository
import io.dev.relic.domain.use_case.weather.TAG
import io.dev.relic.global.utils.LogUtil
import javax.inject.Inject

class FetchComplexRecipesData @Inject constructor(
    private val foodRecipesDataRepository: IFoodRecipesDataRepository
) {

    suspend operator fun invoke(
        offset: Int,
        listener: IFetchDataMonitor
    ) {
        LogUtil.verbose(TAG, "[FoodRecipesApi] Start requesting data.")
        listener.onFetching()

        // Fetch the latest data from remote-server.
        val result: NetworkResult<FoodRecipesComplexSearchDTO> = foodRecipesDataRepository.getComplexSearchRecipesData(
            offset = offset
        )

        // Handle server result.
        when (result) {
            is NetworkResult.Success -> {
                if (result.data == null) {
                    // In fact, sometimes the request succeeds, but the server returns empty data.
                    val errorMessage = "Server error, retry it after."
                    LogUtil.debug(TAG, "[FoodRecipesApi] $errorMessage")
                    listener.onFetchSucceedButNoData(errorMessage = errorMessage)
                } else {
                    LogUtil.apply {
                        debug(TAG, "[FoodRecipesApi] Loading data succeeded.")
                        debug(TAG, "[FoodRecipesApi] Datasource: ${result.data}")
                    }
                    listener.onFetchSucceed(dto = result.data)
                }
            }

            is NetworkResult.Failed -> {
                LogUtil.apply {
                    error(TAG, "[FoodRecipesApi] Failed to load data.")
                    error(TAG, "[FoodRecipesApi] Error message: ${result.message}")
                }
                listener.onFetchFailed(errorMessage = result.message)
            }
        }
    }

}