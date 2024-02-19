package io.data.mappers

import io.data.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.data.dto.food_recipes.get_recipes_information_by_id.FoodRecipesInformationDTO
import io.data.entity.food_recipes.FoodRecipesComplexSearchEntity
import io.data.model.food_recipes.FoodRecipeInformationModel
import io.data.model.food_recipes.FoodRecipesComplexSearchModel

object FoodRecipesDataMapper {

    fun FoodRecipesComplexSearchDTO.toComplexSearchEntity(): FoodRecipesComplexSearchEntity {
        return FoodRecipesComplexSearchEntity(datasource = this)
    }

    fun FoodRecipesComplexSearchDTO.toComplexSearchModelList(): List<FoodRecipesComplexSearchModel?> {
        val tempList = mutableListOf<FoodRecipesComplexSearchModel?>()
        this.results?.forEach {
            tempList.add(
                FoodRecipesComplexSearchModel(
                    id = it?.id,
                    title = it?.title,
                    author = it?.creditsText,
                    image = it?.image,
                    isVegan = it?.vegan,
                    healthScore = it?.healthScore,
                    cookTime = it?.readyInMinutes
                )
            )
        }
        return tempList
    }

    fun FoodRecipesInformationDTO.toFoodRecipeInformationModel(): FoodRecipeInformationModel {
        return FoodRecipeInformationModel(
            id = id,
            title = title,
            vegetarian = vegetarian,
            vegan = vegan,
            glutenFree = glutenFree,
            dairyFree = dairyFree,
            veryHealthy = veryHealthy,
            cheap = cheap,
            veryPopular = veryPopular,
            sustainable = sustainable,
            lowFodmap = lowFodmap,
            weightWatcherSmartPoints = weightWatcherSmartPoints,
            gaps = gaps,
            preparationMinutes = preparationMinutes,
            cookingMinutes = cookingMinutes,
            aggregateLikes = aggregateLikes,
            healthScore = healthScore,
            creditsText = creditsText,
            license = license,
            sourceName = sourceName,
            pricePerServing = pricePerServing,
            extendedIngredients = extendedIngredients,
            readyInMinutes = readyInMinutes,
            servings = servings,
            sourceUrl = sourceUrl,
            image = image,
            imageType = imageType,
            nutrition = nutrition,
            summary = summary,
            cuisines = cuisines,
            dishTypes = dishTypes,
            diets = diets,
            occasions = occasions,
            winePairing = winePairing,
            instructions = instructions,
            analyzedInstructions = analyzedInstructions
        )
    }

}