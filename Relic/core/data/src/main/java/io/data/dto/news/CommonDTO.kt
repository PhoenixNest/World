package io.data.dto.news

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsArticleDTO(
    @Json(name = "source")
    val source: NewsSourceDTO?,
    @Json(name = "author")
    val author: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "urlToImage")
    val urlToImage: String?,
    @Json(name = "publishedAt")
    val publishedAt: String?,
    @Json(name = "content")
    val content: String?
)

@JsonClass(generateAdapter = true)
data class NewsSourceDTO(
    @Json(name = "id")
    val id: String?,
    @Json(name = "name")
    val name: String?
)