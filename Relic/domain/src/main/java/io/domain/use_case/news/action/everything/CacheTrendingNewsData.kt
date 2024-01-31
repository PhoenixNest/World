package io.domain.use_case.news.action.everything

import io.core.database.repository.RelicDatabaseRepository
import io.data.dto.news.NewsArticleDTO
import io.data.dto.news.everything.TrendingNewsDTO
import io.data.mappers.NewsDataMapper.toTrendingNewsArticleEntity
import io.data.mappers.NewsDataMapper.toTrendingNewsEntity
import javax.inject.Inject

class CacheTrendingNewsData @Inject constructor(
    private val databaseRepository: RelicDatabaseRepository
) {

    suspend operator fun invoke(data: TrendingNewsDTO) {
        insertTrendingNewsData(data)
    }

    private suspend fun insertTrendingNewsData(data: TrendingNewsDTO) {
        databaseRepository.insertTrendingNewsData(data.toTrendingNewsEntity())
        insertTrendingNewsArticles(data.articles)
    }

    private suspend fun insertTrendingNewsArticles(articles: List<NewsArticleDTO?>?) {
        articles?.forEach { articleItem ->
            articleItem.toTrendingNewsArticleEntity()?.let {
                databaseRepository.insertNewsEverythingArticle(it)
            }
        }
    }

}