package io.data.mappers

import io.data.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.data.entity.FoodRecipesComplexSearchEntity
import io.data.model.food_recipes.FoodRecipesComplexSearchInfoModel

object FoodRecipesDataMapper {

    fun FoodRecipesComplexSearchDTO.toComplexSearchEntity(): FoodRecipesComplexSearchEntity {
        return FoodRecipesComplexSearchEntity(datasource = this)
    }

    fun FoodRecipesComplexSearchDTO.toComplexSearchModelList(): List<FoodRecipesComplexSearchInfoModel?> {
        val tempList: MutableList<FoodRecipesComplexSearchInfoModel?> = mutableListOf()
        this.results?.forEach {
            tempList.add(
                FoodRecipesComplexSearchInfoModel(
                    id = it?.id,
                    title = it?.title,
                    image = it?.image,
                    imageType = it?.imageType
                )
            )
        }
        return tempList
    }

}