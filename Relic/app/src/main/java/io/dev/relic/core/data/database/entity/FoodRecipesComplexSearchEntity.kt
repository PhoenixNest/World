package io.dev.relic.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.dev.relic.core.data.network.api.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.dev.relic.global.utils.TimeUtil

@Entity(tableName = "table_food_recipes")
class FoodRecipesComplexSearchEntity(
    val foodRecipesComplexSearchDTO: FoodRecipesComplexSearchDTO,
    val lastUpdateTime: Long = TimeUtil.getCurrentTimeInMillis()
) {
    @PrimaryKey
    var id: Int = 0
}