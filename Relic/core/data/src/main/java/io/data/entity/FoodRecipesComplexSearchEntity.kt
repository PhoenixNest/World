package io.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.data.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.module.common.util.TimeUtil

@Entity(tableName = "table_food_recipes")
class FoodRecipesComplexSearchEntity(
    val foodRecipesComplexSearchDTO: FoodRecipesComplexSearchDTO,
    val lastUpdateTime: Long = TimeUtil.getCurrentTimeInMillis()
) {
    @PrimaryKey
    var id: Int = 0
}