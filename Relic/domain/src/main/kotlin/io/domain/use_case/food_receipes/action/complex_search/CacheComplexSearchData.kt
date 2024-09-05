package io.domain.use_case.food_receipes.action.complex_search

import io.core.database.repository.RelicDatabaseRepository
import io.data.entity.food_recipes.FoodRecipesComplexSearchEntity
import javax.inject.Inject

class CacheComplexSearchData @Inject constructor(
    private val databaseRepository: RelicDatabaseRepository
) {
    suspend operator fun invoke(complexSearchEntity: FoodRecipesComplexSearchEntity) {
        databaseRepository.insertComplexSearchRecipesData(complexSearchEntity)
    }
}