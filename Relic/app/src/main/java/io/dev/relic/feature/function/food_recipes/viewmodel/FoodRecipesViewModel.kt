package io.dev.relic.feature.function.food_recipes.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.ext.ViewModelExt.operationInViewModelScope
import io.common.ext.ViewModelExt.setState
import io.common.util.LogUtil
import io.common.util.TimeUtil
import io.common.util.TimeUtil.TimeSection.Afternoon
import io.common.util.TimeUtil.TimeSection.Day
import io.common.util.TimeUtil.TimeSection.MidNight
import io.common.util.TimeUtil.TimeSection.Night
import io.common.util.TimeUtil.TimeSection.Noon
import io.common.util.TimeUtil.TimeSection.Unknown
import io.common.util.TimeUtil.getCurrentTimeSection
import io.data.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.data.mappers.FoodRecipesDataMapper.toComplexSearchEntity
import io.data.mappers.FoodRecipesDataMapper.toComplexSearchModelList
import io.data.model.NetworkResult
import io.dev.relic.BuildConfig
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesCategories
import io.domain.use_case.food_receipes.FoodRecipesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodRecipesViewModel @Inject constructor(
    application: Application,
    private val foodRecipesUseCase: FoodRecipesUseCase,
) : AndroidViewModel(application) {

    /**
     * Indicate the current selected food recipes tab.
     * */
    private var currentSelectedFoodRecipesTab by mutableIntStateOf(0)

    /**
     * The number of results to skip (between 0 and 900).
     * */
    private var foodRecipesOffset = 0

    /**
     * The food recipes data flow of daily recommend.
     * */
    private val _foodRecipesDataStateFlow = MutableStateFlow<FoodRecipesDataState>(FoodRecipesDataState.Init)
    val foodRecipesDataStateFlow: StateFlow<FoodRecipesDataState> get() = _foodRecipesDataStateFlow

    /**
     * The food recipes data flow of time section.
     * */
    private val _foodRecipesTimeSectionDataStateFlow = MutableStateFlow<FoodRecipesDataState>(FoodRecipesDataState.Init)
    val foodRecipesTimeSectionDataStateFlow: StateFlow<FoodRecipesDataState> get() = _foodRecipesTimeSectionDataStateFlow

    companion object {
        private const val TAG = "FoodRecipesViewModel"
    }

    init {
        val currentTimeSection = getCurrentTimeSection()
        getTimeSectionFoodRecipes(currentTimeSection)

        val defaultDishType = FoodRecipesCategories.Coffee.name.lowercase()
        getRecommendFoodRecipes(defaultDishType, currentSelectedFoodRecipesTab)
    }

    fun getTimeSectionFoodRecipes(currentTimeSection: TimeUtil.TimeSection) {
        val dishQueryParameter = when (currentTimeSection) {
            Day -> "breakfast"
            Noon -> "lunch"
            Afternoon -> "teatime"
            Night -> "dinner"
            MidNight -> "snack"
            Unknown -> "recommend"
        }
        getFoodRecipesData(
            dataFlow = _foodRecipesTimeSectionDataStateFlow,
            query = dishQueryParameter,
            offset = 0
        )
    }

    fun getRecommendFoodRecipes(
        queryType: String,
        offset: Int
    ) {
        getFoodRecipesData(
            dataFlow = _foodRecipesDataStateFlow,
            query = queryType,
            offset = offset
        )
    }

    fun getSelectedFoodRecipesTab(): Int {
        return currentSelectedFoodRecipesTab
    }

    fun updateSelectedFoodRecipesTab(newIndex: Int) {
        currentSelectedFoodRecipesTab = newIndex
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
        dataFlow: MutableStateFlow<FoodRecipesDataState>,
        query: String,
        offset: Int,
        addRecipeInformation: Boolean = true,
        addRecipeNutrition: Boolean = !BuildConfig.DEBUG,
    ): StateFlow<NetworkResult<FoodRecipesComplexSearchDTO>> {
        return foodRecipesUseCase
            .getComplexRecipesData(
                query = query,
                addRecipeInformation = addRecipeInformation,
                addRecipeNutrition = addRecipeNutrition,
                offset = offset
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5 * 1000L),
                initialValue = NetworkResult.Loading()
            )
            .also { stateFlow ->
                viewModelScope.launch {
                    stateFlow.collect { result ->
                        handleRemoteFoodRecipesData(
                            dataFlow = dataFlow,
                            result = result
                        )
                    }
                }
            }
    }

    private fun handleRemoteFoodRecipesData(
        dataFlow: MutableStateFlow<FoodRecipesDataState>,
        result: NetworkResult<FoodRecipesComplexSearchDTO>
    ) {
        when (result) {
            is NetworkResult.Loading -> {
                LogUtil.d(TAG, "[Handle Food Recipes Data] Loading...")
                setState(dataFlow, FoodRecipesDataState.Fetching)
            }

            is NetworkResult.Success -> {
                result.data?.also {
                    LogUtil.d(TAG, "[Handle Food Recipes Data] Succeed, data: $it")
                    operationInViewModelScope { foodRecipesUseCase.cacheComplexSearchData.invoke(it.toComplexSearchEntity()) }
                    setState(dataFlow, FoodRecipesDataState.FetchSucceed(it.toComplexSearchModelList()))
                } ?: {
                    LogUtil.d(TAG, "[Handle Food Recipes Data] Succeed without data")
                    setState(dataFlow, FoodRecipesDataState.NoFoodRecipesData)
                }
            }

            is NetworkResult.Failed -> {
                val errorCode = result.code
                val errorMessage = result.message
                LogUtil.e(TAG, "[Handle Food Recipes Data] Failed, ($errorCode, $errorMessage)")
                setState(dataFlow, FoodRecipesDataState.FetchFailed(errorCode, errorMessage))
            }
        }
    }
}