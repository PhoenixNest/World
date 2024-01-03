package io.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.data.entity.food_recipes.FoodRecipesComplexSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodRecipesDao {

    /* ======================== Complex search ======================== */

    @Query("SELECT * FROM table_food_recipes")
    fun readCacheComplexSearchData(): Flow<List<FoodRecipesComplexSearchEntity>>

    @Insert(entity = FoodRecipesComplexSearchEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComplexSearchData(complexSearchEntity: FoodRecipesComplexSearchEntity)

}