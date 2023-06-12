package io.dev.relic.domain.use_case.food_receipes.action.complex_search

import io.dev.relic.core.data.database.entity.FoodRecipesComplexSearchEntity
import io.dev.relic.core.data.database.repository.RelicDatabaseRepository
import javax.inject.Inject

class CacheComplexSearchData @Inject constructor(
    private val databaseRepository: RelicDatabaseRepository
) {

    suspend operator fun invoke(complexSearchEntity: FoodRecipesComplexSearchEntity) {
        databaseRepository.insertComplexSearchRecipesData(complexSearchEntity)
    }

}