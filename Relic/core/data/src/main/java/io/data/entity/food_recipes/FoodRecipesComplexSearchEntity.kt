package io.data.entity.food_recipes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.common.util.TimeUtil
import io.data.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO

@Entity(tableName = "table_food_recipes")
class FoodRecipesComplexSearchEntity(
    @ColumnInfo(name = "datasource")
    val datasource: FoodRecipesComplexSearchDTO,
    @ColumnInfo(name = "last_update_time")
    val lastUpdateTime: Long = TimeUtil.getCurrentTimeInMillis()
) {
    @ColumnInfo(name = "uid", index = true)
    @PrimaryKey(autoGenerate = false)
    var uid: Int = 0
}