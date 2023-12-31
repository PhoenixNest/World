package io.data.dto.news.top_headlines


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.data.dto.news.NewsArticleDTO

@JsonClass(generateAdapter = true)
data class NewsTopHeadlinesDTO(
    @Json(name = "status")
    val status: String?,
    @Json(name = "totalResults")
    val totalResults: Int?,
    @Json(name = "articles")
    val articles: List<NewsArticleDTO?>?
)