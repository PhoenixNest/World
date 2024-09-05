package io.domain.use_case.food_receipes.action.complex_search

import io.core.database.repository.RelicDatabaseRepository
import javax.inject.Inject

class RemoveComplexSearchData @Inject constructor(
    private val databaseRepository: RelicDatabaseRepository
) {
    suspend operator fun invoke() {
        databaseRepository.deleteAllComplexSearchData()
    }
}