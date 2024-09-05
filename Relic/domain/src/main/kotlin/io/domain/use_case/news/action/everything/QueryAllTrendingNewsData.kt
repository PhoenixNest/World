package io.domain.use_case.news.action.everything

import io.core.database.repository.RelicDatabaseRepository
import io.data.entity.news.TrendingNewsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QueryAllTrendingNewsData @Inject constructor(
    private val databaseRepository: RelicDatabaseRepository
) {
    operator fun invoke(): Flow<List<TrendingNewsEntity>> {
        return databaseRepository.queryAllTrendingNewsData()
    }
}