package io.domain.use_case.news.action.top_headline

import io.core.database.repository.RelicDatabaseRepository
import io.data.dto.news.NewsArticleDTO
import io.data.entity.news.TopHeadlinesNewsEntity
import io.data.mappers.NewsDataMapper.toTopHeadlineNewsArticleEntity
import javax.inject.Inject

class CacheTopHeadlineNewsData @Inject constructor(
    private val databaseRepository: RelicDatabaseRepository
) {

    suspend operator fun invoke(data: TopHeadlinesNewsEntity) {
        insertTopHeadlineNewsData(data)
    }

    private suspend fun insertTopHeadlineNewsData(data: TopHeadlinesNewsEntity) {
        databaseRepository.insertNewsTopHeadlineData(data)
        insertTopHeadlineNewsArticles(data.datasource.articles)
    }

    private suspend fun insertTopHeadlineNewsArticles(articles: List<NewsArticleDTO?>?) {
        articles?.forEach { articleItem ->
            articleItem.toTopHeadlineNewsArticleEntity()?.let {
                databaseRepository.insertTopHeadlineNewsArticle(it)
            }
        }
    }

}