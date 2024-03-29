package io.data.dto.news.everything


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.data.dto.news.NewsArticleDTO

@JsonClass(generateAdapter = true)
data class NewsEverythingDTO(
    @Json(name = "status")
    val status: String?,
    @Json(name = "totalResults")
    val totalResults: Int?,
    @Json(name = "articles")
    val articles: List<NewsArticleDTO?>?
)