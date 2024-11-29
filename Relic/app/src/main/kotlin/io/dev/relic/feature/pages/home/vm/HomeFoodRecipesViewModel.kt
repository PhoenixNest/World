package io.dev.relic.feature.pages.home.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.ext.ViewModelExt.setState
import io.common.util.LogUtil
import io.common.util.TimeUtil
import io.data.dto.food_recipes.random_search.FoodRecipesRandomDTO
import io.data.mappers.FoodRecipesDataMapper.toModelList
import io.data.model.NetworkResult
import io.data.model.food_recipes.FoodRecipesRandomModel
import io.dev.relic.R
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesAutoConvertor.convertTimeSectionToDishType
import io.domain.use_case.food_receipes.FoodRecipesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFoodRecipesViewModel @Inject constructor(
    private val application: Application,
    private val foodRecipesUseCase: FoodRecipesUseCase
) : AndroidViewModel(application) {

    /**
     * The food recipes data flow of daily recommend.
     * */
    private val recommendDataStateFlow = MutableStateFlow<FoodRecipesDataState>(FoodRecipesDataState.Init)

    /**
     * Memory cache list of recommend data.
     * */
    private val recommendDataList = mutableListOf<FoodRecipesRandomModel>()

    private var currentDishQueryParameter = ""

    companion object {
        private const val TAG = "HomeFoodRecipesViewModel"
    }

    init {
        val currentTimeSection = TimeUtil.getCurrentTimeSection()
        getRecommendFoodRecipes(currentTimeSection)
    }

    fun getRecommendDataStateFlow(): StateFlow<FoodRecipesDataState> {
        return recommendDataStateFlow
    }

    fun refreshRecommendFoodRecipes() {
        val currentTimeSection = TimeUtil.getCurrentTimeSection()
        getRecommendFoodRecipes(currentTimeSection)
    }

    private fun getRecommendFoodRecipes(currentTimeSection: TimeUtil.TimeSection) {
        val dishType = convertTimeSectionToDishType(currentTimeSection)
        var dishQueryParameter = application.getString(dishType.labelResId).lowercase().trim()

        // Special treatment for the "Afternoon Tea" type
        if (dishQueryParameter == application.getString(R.string.food_recipes_label_teatime).lowercase().trim()) {
            dishQueryParameter = "lunch"
        }

        currentDishQueryParameter = dishQueryParameter
        getRandomRecipes(includeTags = currentDishQueryParameter)
    }

    /**
     * [Get Random Recipes](https://spoonacular.com/food-api/docs#Get-Random-Recipes)
     *
     * Find random (popular) recipes. `If you need to filter recipes by diet, nutrition etc.`
     * you might want to consider using the complex recipe search endpoint
     * and set the `sort` request parameter to `random`.
     *
     * @param includeNutrition  Whether to include nutritional information to returned recipes.
     * @param includeTags       The tags (can be diets, meal types, cuisines, or intolerances) that the recipe must have.
     * @param excludeTags       The tags (can be diets, meal types, cuisines, or intolerances) that the recipe must NOT have.
     * @param number            The number of random recipes to be returned (between 1 and 100).
     *
     * @see FoodRecipesRandomDTO
     * */
    private fun getRandomRecipes(
        includeNutrition: Boolean = false,
        includeTags: String,
        excludeTags: String = "",
        number: Int = 10
    ) {
        viewModelScope.launch {
            foodRecipesUseCase.getRandomRecipesData.invoke(
                includeNutrition = includeNutrition,
                includeTags = includeTags,
                excludeTags = excludeTags,
                number = number
            ).stateIn(
                scope = this,
                started = SharingStarted.WhileSubscribed(5 * 1000L),
                initialValue = NetworkResult.Loading()
            ).collect { result ->
                handleRandomData(result = result)
            }
        }
    }

    /**
     * Handle the remote-data of recommend food recipe.
     *
     * @param result
     * */
    private fun handleRandomData(result: NetworkResult<FoodRecipesRandomDTO>) {
        when (result) {
            is NetworkResult.Loading -> {
                LogUtil.d(TAG, "[Handle Random Data] Loading...")
                setState(recommendDataStateFlow, FoodRecipesDataState.Fetching)
            }

            is NetworkResult.Success -> {
                result.data?.also { dto ->
                    LogUtil.d(TAG, "[Handle Random Data] Succeed, data: $dto")
                    val modelList = dto.toModelList()
                    val filteredModelList = modelList.filterNotNull()
                    recommendDataList.clear()
                    recommendDataList.addAll(filteredModelList)
                    setState(recommendDataStateFlow, FoodRecipesDataState.FetchSucceed(recommendDataList))
                } ?: {
                    LogUtil.d(TAG, "[Handle Random Data] Succeed without data")
                    setState(recommendDataStateFlow, FoodRecipesDataState.NoFoodRecipesData)
                }
            }

            is NetworkResult.Failed -> {
                val errorCode = result.code
                val errorMessage = result.message
                LogUtil.e(TAG, "[Handle Random Data] Failed, ($errorCode, $errorMessage)")
                val newState = FoodRecipesDataState.FetchFailed(errorCode, errorMessage, recommendDataList)
                setState(recommendDataStateFlow, newState)
            }
        }
    }
}