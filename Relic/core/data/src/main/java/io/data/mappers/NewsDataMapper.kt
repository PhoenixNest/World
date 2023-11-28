package io.data.mappers

import io.data.dto.news.NewsArticleDTO
import io.data.dto.news.everything.NewsEverythingDTO
import io.data.dto.news.top_headlines.NewsTopHeadlinesDTO
import io.data.entity.NewsEverythingArticleEntity
import io.data.entity.NewsEverythingEntity
import io.data.entity.NewsTopHeadlineArticleEntity
import io.data.entity.NewsTopHeadlinesEntity
import io.data.model.news.NewsArticleModel

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

    fun NewsArticleDTO?.toNewsTopHeadlineArticleEntity(): NewsTopHeadlineArticleEntity? {
        if (this == null) {
            return null
        }

        return NewsTopHeadlineArticleEntity(
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

    fun List<NewsArticleDTO?>.toNewsArticleModelList(): List<NewsArticleModel?> {
        val tempList: MutableList<NewsArticleModel?> = mutableListOf()
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