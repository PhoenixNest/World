package io.domain.use_case.news.action.everything

import io.core.database.repository.RelicDatabaseRepository
import io.data.dto.news.NewsArticleDTO
import io.data.entity.news.TrendingNewsEntity
import io.data.mappers.NewsDataMapper.toTrendingNewsArticleEntity
import javax.inject.Inject

class CacheTrendingNewsData @Inject constructor(
    private val databaseRepository: RelicDatabaseRepository
) {

    suspend operator fun invoke(data: TrendingNewsEntity) {
        insertTrendingNewsData(data)
    }

    private suspend fun insertTrendingNewsData(data: TrendingNewsEntity) {
        databaseRepository.insertTrendingNewsData(data)
        insertTrendingNewsArticles(data.datasource.articles)
    }

    private suspend fun insertTrendingNewsArticles(articles: List<NewsArticleDTO?>?) {
        articles?.forEach { articleItem ->
            articleItem.toTrendingNewsArticleEntity()?.let {
                databaseRepository.insertNewsEverythingArticle(it)
            }
        }
    }

}