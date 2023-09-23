package io.dev.relic.core.data.network.mappers

import io.dev.relic.core.data.database.entity.FoodRecipesComplexSearchEntity
import io.dev.relic.core.data.network.api.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.dev.relic.domain.model.food_recipes.FoodRecipesComplexSearchInfoModel

object FoodRecipesDataMapper {

    fun FoodRecipesComplexSearchDTO.toComplexSearchEntity(): FoodRecipesComplexSearchEntity {
        return FoodRecipesComplexSearchEntity(foodRecipesComplexSearchDTO = this)
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