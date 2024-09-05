package io.domain.use_case.news.action.top_headline

import io.core.database.repository.RelicDatabaseRepository
import io.data.entity.news.TopHeadlinesNewsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QueryAllTopHeadlineNewsData @Inject constructor(
    private val databaseRepository: RelicDatabaseRepository
) {
    operator fun invoke(): Flow<List<TopHeadlinesNewsEntity>> {
        return databaseRepository.queryAllTopHeadlineNewsData()
    }
}