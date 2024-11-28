package io.dev.relic.feature.pages.detail.food_recipe.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.ext.ViewModelExt.setState
import io.common.util.LogUtil
import io.core.datastore.RelicDatastoreCenter.readAsyncData
import io.core.datastore.RelicDatastoreCenter.writeAsyncData
import io.data.dto.food_recipes.get_recipes_information_by_id.FoodRecipesInformationDTO
import io.data.mappers.FoodRecipesDataMapper.toModel
import io.data.model.NetworkResult
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.domain.use_case.food_receipes.FoodRecipesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodRecipesDetailViewModel @Inject constructor(
    application: Application,
    private val foodRecipesUseCase: FoodRecipesUseCase
) : AndroidViewModel(application) {

    /**
     * The food recipe information flow.
     * */
    private val informationDataStateFlow = MutableStateFlow<FoodRecipesDataState>(FoodRecipesDataState.Init)

    companion object {
        private const val TAG = "FoodRecipesDetailViewModel"
        private const val KEY_FOOD_RECIPE_ID = "key_food_recipe_id"
    }

    fun getInformationDataStateFlow(): StateFlow<FoodRecipesDataState> {
        return informationDataStateFlow
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
        viewModelScope.launch {
            writeAsyncData("${KEY_FOOD_RECIPE_ID}_$recipeId", isLike)
        }
    }

    fun getRecipeInformationById(
        recipeId: Int,
        includeNutrition: Boolean = true
    ) {
        viewModelScope.launch {
            foodRecipesUseCase.getRecipeInformationById.invoke(
                recipeId = recipeId,
                includeNutrition = includeNutrition
            ).stateIn(
                scope = this,
                started = SharingStarted.WhileSubscribed(5 * 1000L),
                initialValue = NetworkResult.Loading()
            ).collect { result ->
                handleInformationData(result)
            }
        }
    }

    /**
     * Handle the remote-data of food recipe information.
     *
     * @param result
     * */
    private fun handleInformationData(result: NetworkResult<FoodRecipesInformationDTO>) {
        when (result) {
            is NetworkResult.Loading -> {
                LogUtil.d(TAG, "[Handle Food Recipe Information Data] Loading...")
                setState(informationDataStateFlow, FoodRecipesDataState.Fetching)
            }

            is NetworkResult.Success -> {
                result.data?.also { dto ->
                    LogUtil.d(TAG, "[Handle Food Recipe Information Data] Succeed, data: $dto")
                    val model = dto.toModel()
                    setState(informationDataStateFlow, FoodRecipesDataState.FetchSucceed(model))
                } ?: {
                    LogUtil.d(TAG, "[Handle Food Recipe Information Data] Succeed without data")
                    setState(informationDataStateFlow, FoodRecipesDataState.NoFoodRecipesData)
                }
            }

            is NetworkResult.Failed -> {
                val errorCode = result.code
                val errorMessage = result.message
                LogUtil.e(TAG, "[Handle Food Recipe Information Data] Failed, ($errorCode, $errorMessage)")
                setState(informationDataStateFlow, FoodRecipesDataState.FetchFailed(errorCode, errorMessage))
            }
        }
    }
}