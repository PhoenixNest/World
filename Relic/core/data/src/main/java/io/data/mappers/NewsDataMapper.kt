package io.data.mappers

import io.data.dto.news.NewsArticleDTO
import io.data.dto.news.everything.NewsEverythingDTO
import io.data.dto.news.top_headlines.NewsTopHeadlinesDTO
import io.data.entity.NewsEverythingArticleEntity
import io.data.entity.NewsEverythingEntity
import io.data.entity.NewsTopHeadlineArticleEntity
import io.data.entity.NewsTopHeadlinesEntity

object NewsDataMapper {

    fun NewsEverythingDTO.toNewsEverythingEntity(): NewsEverythingEntity {
        return NewsEverythingEntity(datasource = this)
    }

    fun NewsTopHeadlinesDTO.toNewsTopHeadlineEntity(): NewsTopHeadlinesEntity {
        return NewsTopHeadlinesEntity(datasource = this)
    }

    fun NewsArticleDTO?.toNewsEverythingArticleEntity(): NewsEverythingArticleEntity? {
        if (this == null) {
            return null
        }

        return NewsEverythingArticleEntity(
            id = this.source?.id ?: "article id",
            source = this.source?.name ?: "Unknown source",
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content
        )
    }

    fun NewsArticleDTO?.toNewsTopHeadlineArticleEntity(): NewsTopHeadlineArticleEntity? {
        if (this == null) {
            return null
        }

        return NewsTopHeadlineArticleEntity(
            id = this.source?.id ?: "article id",
            source = this.source?.name ?: "Unknown source",
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content
        )
    }

}