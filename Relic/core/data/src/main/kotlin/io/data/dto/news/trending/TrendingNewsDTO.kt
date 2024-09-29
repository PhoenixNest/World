package io.data.dto.news.trending

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.data.dto.news.NewsArticleDTO

@JsonClass(generateAdapter = true)
data class TrendingNewsDTO(
    @Json(name = "status")
    val status: String?,
    @Json(name = "totalResults")
    val totalResults: Int?,
    @Json(name = "articles")
    val articles: List<NewsArticleDTO?>?
)