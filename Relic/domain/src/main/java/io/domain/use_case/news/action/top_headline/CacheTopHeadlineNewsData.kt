package io.domain.use_case.news.action.top_headline

import io.core.database.repository.RelicDatabaseRepository
import io.data.dto.news.NewsArticleDTO
import io.data.dto.news.top_headlines.TopHeadlinesNewsDTO
import io.data.mappers.NewsDataMapper.toTopHeadlineNewsArticleEntity
import io.data.mappers.NewsDataMapper.toTopHeadlineNewsEntity
import javax.inject.Inject

class CacheTopHeadlineNewsData @Inject constructor(
    private val databaseRepository: RelicDatabaseRepository
) {

    suspend operator fun invoke(data: TopHeadlinesNewsDTO) {
        insertTopHeadlineNewsData(data)
    }

    private suspend fun insertTopHeadlineNewsData(data: TopHeadlinesNewsDTO) {
        databaseRepository.insertNewsTopHeadlineData(data.toTopHeadlineNewsEntity())
        insertTopHeadlineNewsArticles(data.articles)
    }

    private suspend fun insertTopHeadlineNewsArticles(articles: List<NewsArticleDTO?>?) {
        articles?.forEach { articleItem ->
            articleItem.toTopHeadlineNewsArticleEntity()?.let {
                databaseRepository.insertTopHeadlineNewsArticle(it)
            }
        }
    }

}