package io.data.entity.news

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.common.util.TimeUtil
import io.data.dto.news.top_headlines.NewsTopHeadlinesDTO

@Entity(tableName = "table_news_headlines")
data class NewsTopHeadlinesEntity(
    @ColumnInfo(name = "datasource")
    val datasource: NewsTopHeadlinesDTO,
    @ColumnInfo(name = "last_update_time")
    val lastUpdateTime: Long = TimeUtil.getCurrentTimeInMillis()
) {
    @ColumnInfo(name = "uid", index = true)
    @PrimaryKey(autoGenerate = false)
    var uid: Int = 0
}

@Entity(tableName = "table_news_top_headline_articles")
data class NewsTopHeadlineArticleEntity(
    @ColumnInfo(name = "article_id")
    val id: String?,
    @ColumnInfo(name = "source")
    val source: String?,
    @ColumnInfo(name = "author")
    val author: String?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "url")
    val url: String?,
    @ColumnInfo(name = "image_url")
    val urlToImage: String?,
    @ColumnInfo(name = "publish_time")
    val publishedAt: String?,
    @ColumnInfo(name = "content")
    val content: String?
) {
    @ColumnInfo(name = "uid", index = true)
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}