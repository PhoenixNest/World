package io.dev.relic.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.dev.relic.core.data.database.entity.FoodRecipesComplexSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodRecipesDao {

    @Query("SELECT * FROM table_food_recipes")
    fun readCacheComplexSearchData(): Flow<List<FoodRecipesComplexSearchEntity>>

    @Insert
    suspend fun insertComplexSearchData(complexSearchEntity: FoodRecipesComplexSearchEntity)

}