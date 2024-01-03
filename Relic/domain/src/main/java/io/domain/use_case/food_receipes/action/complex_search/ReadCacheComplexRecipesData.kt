package io.domain.use_case.food_receipes.action.complex_search

import io.core.database.repository.RelicDatabaseRepository
import io.data.entity.food_recipes.FoodRecipesComplexSearchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadCacheComplexRecipesData @Inject constructor(
    private val databaseRepository: RelicDatabaseRepository
) {
    operator fun invoke(): Flow<List<FoodRecipesComplexSearchEntity>> {
        return databaseRepository.readComplexSearchRecipesCache()
    }
}