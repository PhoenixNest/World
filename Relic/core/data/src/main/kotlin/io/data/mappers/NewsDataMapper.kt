package io.data.mappers

import io.data.dto.news.NewsArticleDTO
import io.data.dto.news.top_headlines.TopHeadlinesNewsDTO
import io.data.dto.news.trending.TrendingNewsDTO
import io.data.entity.news.TopHeadlineNewsArticleEntity
import io.data.entity.news.TopHeadlinesNewsEntity
import io.data.entity.news.TrendingNewsArticleEntity
import io.data.entity.news.TrendingNewsEntity
import io.data.model.news.NewsArticleModel

object NewsDataMapper {

    fun TrendingNewsDTO.toTrendingNewsEntity(): TrendingNewsEntity {
        return TrendingNewsEntity(this)
    }

    fun TopHeadlinesNewsDTO.toTopHeadlineNewsEntity(): TopHeadlinesNewsEntity {
        return TopHeadlinesNewsEntity(this)
    }

    fun NewsArticleDTO?.toTrendingNewsArticleEntity(): TrendingNewsArticleEntity? {
        if (this == null) {
            return null
        }

        return TrendingNewsArticleEntity(
            id = source?.id ?: "article id",
            source = source?.name ?: "Unknown source",
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content
        )
    }

    fun NewsArticleDTO?.toTopHeadlineNewsArticleEntity(): TopHeadlineNewsArticleEntity? {
        if (this == null) {
            return null
        }

        return TopHeadlineNewsArticleEntity(
            id = source?.id ?: "article id",
            source = source?.name ?: "Unknown source",
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content
        )
    }

    fun List<NewsArticleDTO?>.toModelList(): List<NewsArticleModel?> {
        val tempList = mutableListOf<NewsArticleModel?>()
        this.forEach {
            tempList.add(
                NewsArticleModel(
                    title = it?.title,
                    subtitle = it?.description,
                    author = it?.author,
                    thumbnailImageUrl = it?.urlToImage,
                    publishDate = it?.publishedAt,
                    contentUrl = it?.url,
                    source = it?.source?.name
                )
            )
        }

        return tempList.toList()
    }
}