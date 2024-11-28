package io.dev.relic.feature.function.food_recipes.vm

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.ext.ViewModelExt.setState
import io.common.util.LogUtil
import io.data.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.data.mappers.FoodRecipesDataMapper.toModelList
import io.data.model.NetworkResult
import io.data.model.food_recipes.FoodRecipesComplexSearchModel
import io.dev.relic.BuildConfig
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.domain.use_case.food_receipes.FoodRecipesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodRecipesViewModel @Inject constructor(
    application: Application,
    private val foodRecipesUseCase: FoodRecipesUseCase
) : AndroidViewModel(application) {

    /**
     * The food recipes data flow of complex search.
     * */
    private val complexSearchDataStateFlow = MutableStateFlow<FoodRecipesDataState>(FoodRecipesDataState.Init)

    /**
     * Indicate the status of fetch more.
     * */
    private var isFetchingMoreComplexData = false

    /**
     * Indicate the available status of fetch more.
     * */
    private var canFetchMoreComplexData = true

    /**
     * Indicate the current selected food recipes tab.
     * */
    private var currentSelectedFoodRecipesTab by mutableIntStateOf(0)

    /**
     * The number of results to skip (between 0 and 900).
     * */
    private var currentRecommendFoodRecipesOffset = 0

    /**
     * Memory cache list of complex search data.
     * */
    private val complexSearchDataList = mutableListOf<FoodRecipesComplexSearchModel>()

    companion object {
        private const val TAG = "FoodRecipesViewModel"
    }

    fun getSelectedFoodRecipesTab(): Int {
        return currentSelectedFoodRecipesTab
    }

    fun updateSelectedFoodRecipesTab(newIndex: Int) {
        viewModelScope.launch {
            foodRecipesUseCase.deleteAllComplexSearchData.invoke()
            complexSearchDataList.clear()
            currentSelectedFoodRecipesTab = newIndex
        }
    }

    fun resetRecommendFoodRecipesOffset() {
        currentRecommendFoodRecipesOffset = 0
    }

    fun resetCanFetchMoreStatus() {
        canFetchMoreComplexData = true
    }

    /**
     * [Search Recipes](https://spoonacular.com/food-api/docs#Search-Recipes-Complex)
     *
     * Search through thousands of recipes using advanced filtering and ranking.
     *
     * `NOTE`: This method combines searching by query, by ingredients, and by nutrients into one endpoint.
     *
     * @param query                      The (natural language) recipe search query.
     * @param addRecipeInformation       If set to true, you get more information about the recipes returned.
     * @param addRecipeNutrition         If set to true, you get nutritional information about each recipes returned.
     * @param offset                     The number of results to skip (between 0 and 900).
     *
     * @see FoodRecipesComplexSearchDTO
     * */
    private fun getComplexSearchData(
        query: String,
        offset: Int,
        addRecipeInformation: Boolean = true,
        addRecipeNutrition: Boolean = !BuildConfig.DEBUG,
        isFetchMore: Boolean = false
    ) {
        viewModelScope.launch {
            foodRecipesUseCase.getComplexSearchData.invoke(
                query = query,
                addRecipeInformation = addRecipeInformation,
                addRecipeNutrition = addRecipeNutrition,
                offset = offset
            ).stateIn(
                scope = this,
                started = SharingStarted.WhileSubscribed(5 * 1000L),
                initialValue = NetworkResult.Loading()
            ).collect { result ->
                handleComplexSearchData(
                    result = result,
                    isFetchMore = isFetchMore
                )
            }
        }
    }

    /**
     * Handle the remote-data of food recipes.
     *
     * @param result
     * @param isFetchMore
     * */
    private fun handleComplexSearchData(
        result: NetworkResult<FoodRecipesComplexSearchDTO>,
        isFetchMore: Boolean = false
    ) {
        when (result) {
            is NetworkResult.Loading -> {
                if (isFetchMore) {
                    LogUtil.d(TAG, "[Handle Food Recipes Data - Fetch more] Loading...")
                    // setState(complexSearchDataStateFlow, FoodRecipesDataState.FetchingMore)
                } else {
                    LogUtil.d(TAG, "[Handle Food Recipes Data] Loading...")
                    setState(complexSearchDataStateFlow, FoodRecipesDataState.Fetching)
                }
            }

            is NetworkResult.Success -> {
                result.data?.also { dto ->
                    LogUtil.d(TAG, "[Handle Food Recipes Data] Succeed, data: $dto")
                    val modelList = dto.toModelList()
                    val filteredModelList = modelList.filterNotNull()

                    if (isFetchMore && filteredModelList.isEmpty()) {
                        LogUtil.w(TAG, "[Fetch More Recommend Data] Server data is depleted, we can't get anymore sir.")
                        currentRecommendFoodRecipesOffset -= 10
                        isFetchingMoreComplexData = false
                        canFetchMoreComplexData = false
                        return
                    }

                    complexSearchDataList.addAll(filteredModelList)
                    val dataList = complexSearchDataList.toList()
                    setState(complexSearchDataStateFlow, FoodRecipesDataState.FetchSucceed(dataList))
                    isFetchingMoreComplexData = false
                } ?: {
                    LogUtil.d(TAG, "[Handle Food Recipes Data] Succeed without data")
                    setState(complexSearchDataStateFlow, FoodRecipesDataState.NoFoodRecipesData)
                    isFetchingMoreComplexData = false
                }
            }

            is NetworkResult.Failed -> {
                val errorCode = result.code
                val errorMessage = result.message
                LogUtil.e(TAG, "[Handle Food Recipes Data] Failed, ($errorCode, $errorMessage)")
                setState(complexSearchDataStateFlow, FoodRecipesDataState.FetchFailed(errorCode, errorMessage))
                isFetchingMoreComplexData = false
            }
        }
    }
}