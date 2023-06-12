package io.dev.relic.core.data.database.dao

import androidx.room.Dao
import io.dev.relic.core.data.database.entity.FoodRecipesComplexSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodRecipesDao {

    fun readCacheComplexSearchData(): Flow<List<FoodRecipesComplexSearchEntity>>

    suspend fun insertComplexSearchData(complexSearchEntity: FoodRecipesComplexSearchEntity)

}