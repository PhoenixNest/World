package io.data.mappers

import io.data.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.data.entity.food_recipes.FoodRecipesComplexSearchEntity
import io.data.model.food_recipes.FoodRecipesComplexSearchInfoModel

object FoodRecipesDataMapper {

    fun FoodRecipesComplexSearchDTO.toComplexSearchEntity(): FoodRecipesComplexSearchEntity {
        return FoodRecipesComplexSearchEntity(datasource = this)
    }

    fun FoodRecipesComplexSearchDTO.toComplexSearchModelList(): List<FoodRecipesComplexSearchInfoModel?> {
        val tempList = mutableListOf<FoodRecipesComplexSearchInfoModel?>()
        this.results?.forEach {
            tempList.add(
                FoodRecipesComplexSearchInfoModel(
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