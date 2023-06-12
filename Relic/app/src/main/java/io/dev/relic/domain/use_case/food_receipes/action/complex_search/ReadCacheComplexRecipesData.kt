package io.dev.relic.domain.use_case.food_receipes.action.complex_search

import io.dev.relic.core.data.database.entity.FoodRecipesComplexSearchEntity
import io.dev.relic.core.data.database.repository.RelicDatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadCacheComplexRecipesData @Inject constructor(
    private val databaseRepository: RelicDatabaseRepository
) {

    operator fun invoke(): Flow<List<FoodRecipesComplexSearchEntity>> {
        return databaseRepository.readComplexSearchRecipesCache()
    }

}