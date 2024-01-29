package io.data.model.news

data class NewsArticleModel(
    val title: String?,
    val subtitle: String?,
    val author: String?,
    val thumbnailImageUrl: String?,
    val publishDate: String?,
    val contentUrl: String?,
    val source: String?
)