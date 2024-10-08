package io.dev.relic.feature.function.food_recipes.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.RelicResCenter.getString
import io.common.ext.ViewModelExt.operationInViewModelScope
import io.common.ext.ViewModelExt.setState
import io.common.util.LogUtil
import io.common.util.TimeUtil
import io.common.util.TimeUtil.getCurrentTimeSection
import io.core.datastore.RelicDatastoreCenter.readAsyncData
import io.core.datastore.RelicDatastoreCenter.writeAsyncData
import io.data.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.data.dto.food_recipes.get_recipes_information_by_id.FoodRecipesInformationDTO
import io.data.mappers.FoodRecipesDataMapper.toModel
import io.data.mappers.FoodRecipesDataMapper.toModelList
import io.data.model.NetworkResult
import io.data.model.food_recipes.FoodRecipesComplexSearchModel
import io.dev.relic.BuildConfig
import io.dev.relic.R
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesAutoConvertor.convertTimeSectionToDishType
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesCategories
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesType
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesType.RECOMMEND
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesType.TIME_SECTION
import io.domain.preference_key.FoodRecipesPreferenceKey.KEY_LAST_TIME_SECTION
import io.domain.use_case.food_receipes.FoodRecipesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class FoodRecipesViewModel @Inject constructor(
    application: Application,
    private val foodRecipesUseCase: FoodRecipesUseCase
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
     * Indicate the current selected food recipes tab.
     * */
    private var currentSelectedFoodRecipesTab by mutableIntStateOf(0)

    /**
     * The number of results to skip (between 0 and 900).
     * */
    private var currentRecommendFoodRecipesOffset = 0

    /**
     * The food recipes data flow of daily recommend.
     * */
    private val _recommendDataStateFlow = MutableStateFlow<FoodRecipesDataState>(FoodRecipesDataState.Init)
    val recommendDataStateFlow: StateFlow<FoodRecipesDataState> get() = _recommendDataStateFlow

    /**
     * The food recipes data flow of time section.
     * */
    private val _timeSectionDataStateFlow = MutableStateFlow<FoodRecipesDataState>(FoodRecipesDataState.Init)
    val timeSectionDataStateFlow: StateFlow<FoodRecipesDataState> get() = _timeSectionDataStateFlow

    /**
     * The food recipe information flow.
     * */
    private val _informationDataStateFlow = MutableStateFlow<FoodRecipesDataState>(FoodRecipesDataState.Init)
    val informationDataStateFlow: StateFlow<FoodRecipesDataState> get() = _informationDataStateFlow

    /**
     * Memory cache list of recommend data.
     * */
    private val recommendDataList = mutableListOf<FoodRecipesComplexSearchModel>()

    /**
     * Memory cache list of time-section data.
     * */
    private val timeSectionDataList = mutableListOf<FoodRecipesComplexSearchModel>()

    companion object {
        private const val TAG = "FoodRecipesViewModel"
        private const val KEY_FOOD_RECIPE_ID = "key_food_recipe_id"
    }

    init {
        val currentTimeSection = getCurrentTimeSection()
        getTimeSectionFoodRecipes(currentTimeSection)

        val defaultDishType = FoodRecipesCategories.entries.first().name.lowercase()
        getRecommendFoodRecipes(defaultDishType, currentRecommendFoodRecipesOffset)
    }

    fun getTimeSectionFoodRecipes(currentTimeSection: TimeUtil.TimeSection) {
        val dishType = convertTimeSectionToDishType(currentTimeSection)
        var dishQueryParameter = getString(dishType.labelResId).lowercase().trim()

        // Special treatment for the "Teatime" type
        if (dishQueryParameter == getString(R.string.food_recipes_label_teatime).lowercase().trim()) {
            dishQueryParameter = "tea"
        }

        operationInViewModelScope {
            writeAsyncData(KEY_LAST_TIME_SECTION, currentTimeSection.name)
            getFoodRecipesData(
                type = TIME_SECTION,
                dataFlow = _timeSectionDataStateFlow,
                query = dishQueryParameter,
                offset = Random.nextInt(0, 10)
            )
        }
    }

    fun getRecommendFoodRecipes(
        queryType: String,
        offset: Int
    ) {
        operationInViewModelScope {
            getFoodRecipesData(
                type = RECOMMEND,
                dataFlow = _recommendDataStateFlow,
                query = queryType,
                offset = offset,
                isFetchMore = false
            )
        }
    }

    fun fetchMoreRecommendData(queryString: String) {
        if (!canFetchMore) {
            LogUtil.w(TAG, "[Fetch More Recommend Data] Can't get more data from server, skip.")
            return
        }

        val newOffset = (currentRecommendFoodRecipesOffset + 10)
        currentRecommendFoodRecipesOffset = newOffset

        operationInViewModelScope {
            isFetchingMore = true
            getFoodRecipesData(
                type = RECOMMEND,
                dataFlow = _recommendDataStateFlow,
                query = queryString,
                offset = newOffset,
                isFetchMore = true
            )
        }
    }

    fun getRecommendDataList(): List<FoodRecipesComplexSearchModel> {
        return recommendDataList.toList()
    }

    fun getSelectedFoodRecipesTab(): Int {
        return currentSelectedFoodRecipesTab
    }

    fun updateSelectedFoodRecipesTab(newIndex: Int) {
        operationInViewModelScope {
            foodRecipesUseCase.deleteAllComplexSearchData.invoke()
            recommendDataList.clear()
            currentSelectedFoodRecipesTab = newIndex
        }
    }

    fun resetRecommendFoodRecipesOffset() {
        currentRecommendFoodRecipesOffset = 0
    }

    fun resetCanFetchMoreStatus() {
        canFetchMore = true
    }

    fun getRecipeInformationById(
        recipeId: Int,
        includeNutrition: Boolean = true
    ) {
        operationInViewModelScope { scope ->
            foodRecipesUseCase.getRecipeInformationById.invoke(
                recipeId = recipeId,
                includeNutrition = includeNutrition
            ).stateIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(5 * 1000L),
                initialValue = NetworkResult.Loading()
            ).collect { result ->
                handleRemoteFoodRecipeInformationData(result)
            }
        }
    }

    /**
     * Check the like status of selected recipe.
     *
     * @param recipeId
     * */
    fun isLikeRecipe(recipeId: Int): Flow<Boolean> {
        return readAsyncData("${KEY_FOOD_RECIPE_ID}_$recipeId", false)
    }

    /**
     * Update the like status of the selected recipe.
     *
     * @param recipeId
     * */
    fun updateLikeStatus(
        recipeId: Int,
        isLike: Boolean
    ) {
        operationInViewModelScope {
            writeAsyncData("${KEY_FOOD_RECIPE_ID}_$recipeId", isLike)
        }
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
     *
     * @see FoodRecipesComplexSearchDTO
     * */
    private fun getFoodRecipesData(
        type: FoodRecipesType,
        dataFlow: MutableStateFlow<FoodRecipesDataState>,
        query: String,
        offset: Int,
        addRecipeInformation: Boolean = true,
        addRecipeNutrition: Boolean = !BuildConfig.DEBUG,
        isFetchMore: Boolean = false
    ) {
        operationInViewModelScope { scope ->
            foodRecipesUseCase.getComplexRecipesData.invoke(
                query = query,
                addRecipeInformation = addRecipeInformation,
                addRecipeNutrition = addRecipeNutrition,
                offset = offset
            ).stateIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(5 * 1000L),
                initialValue = NetworkResult.Loading()
            ).collect { result ->
                handleRemoteFoodRecipesData(
                    type = type,
                    dataFlow = dataFlow,
                    result = result,
                    isFetchMore = isFetchMore
                )
            }
        }
    }

    /**
     * Handle the remote-data of food recipes.
     *
     * @param dataFlow
     * @param result
     * */
    private fun handleRemoteFoodRecipesData(
        type: FoodRecipesType,
        dataFlow: MutableStateFlow<FoodRecipesDataState>,
        result: NetworkResult<FoodRecipesComplexSearchDTO>,
        isFetchMore: Boolean = false
    ) {
        when (result) {
            is NetworkResult.Loading -> {
                if (isFetchMore) {
                    LogUtil.d(TAG, "[Handle Food Recipes Data - Fetch more] Loading...")
                    // setState(dataFlow, FoodRecipesDataState.FetchingMore)
                } else {
                    LogUtil.d(TAG, "[Handle Food Recipes Data] Loading...")
                    setState(dataFlow, FoodRecipesDataState.Fetching)
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
                        isFetchingMore = false
                        canFetchMore = false
                        return
                    }

                    handleSuccessComplexData(
                        type = type,
                        dataFlow = dataFlow,
                        modelList = filteredModelList
                    ).also { isFetchingMore = false }
                } ?: {
                    LogUtil.d(TAG, "[Handle Food Recipes Data] Succeed without data")
                    setState(dataFlow, FoodRecipesDataState.NoFoodRecipesData)
                    isFetchingMore = false
                }
            }

            is NetworkResult.Failed -> {
                val errorCode = result.code
                val errorMessage = result.message
                LogUtil.e(TAG, "[Handle Food Recipes Data] Failed, ($errorCode, $errorMessage)")
                setState(dataFlow, FoodRecipesDataState.FetchFailed(errorCode, errorMessage))
                isFetchingMore = false
            }
        }
    }

    /**
     * Handle the remote-data of food recipe information.
     *
     * @param result
     * */
    private fun handleRemoteFoodRecipeInformationData(result: NetworkResult<FoodRecipesInformationDTO>) {
        when (result) {
            is NetworkResult.Loading -> {
                LogUtil.d(TAG, "[Handle Food Recipe Information Data] Loading...")
                setState(_informationDataStateFlow, FoodRecipesDataState.Fetching)
            }

            is NetworkResult.Success -> {
                result.data?.also { dto ->
                    LogUtil.d(TAG, "[Handle Food Recipe Information Data] Succeed, data: $dto")
                    val model = dto.toModel()
                    setState(_informationDataStateFlow, FoodRecipesDataState.FetchSucceed(model))
                } ?: {
                    LogUtil.d(TAG, "[Handle Food Recipe Information Data] Succeed without data")
                    setState(_informationDataStateFlow, FoodRecipesDataState.NoFoodRecipesData)
                }
            }

            is NetworkResult.Failed -> {
                val errorCode = result.code
                val errorMessage = result.message
                LogUtil.e(TAG, "[Handle Food Recipe Information Data] Failed, ($errorCode, $errorMessage)")
                setState(_informationDataStateFlow, FoodRecipesDataState.FetchFailed(errorCode, errorMessage))
            }
        }
    }

    private fun handleSuccessComplexData(
        type: FoodRecipesType,
        dataFlow: MutableStateFlow<FoodRecipesDataState>,
        modelList: List<FoodRecipesComplexSearchModel>
    ) {
        when (type) {
            RECOMMEND -> {
                recommendDataList.addAll(modelList)
                val dataList = recommendDataList.toList()
                setState(dataFlow, FoodRecipesDataState.FetchSucceed(dataList))
            }

            TIME_SECTION -> {
                timeSectionDataList.addAll(modelList)
                val newList = timeSectionDataList.toList()
                setState(dataFlow, FoodRecipesDataState.FetchSucceed(newList))
            }
        }
    }
}