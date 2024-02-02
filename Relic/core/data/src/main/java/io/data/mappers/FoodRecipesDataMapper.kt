package io.data.mappers

import io.data.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.data.entity.food_recipes.FoodRecipesComplexSearchEntity
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

}