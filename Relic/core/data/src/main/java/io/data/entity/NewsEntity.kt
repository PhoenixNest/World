package io.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.common.util.TimeUtil
import io.data.dto.news.everything.NewsEverythingDTO
import io.data.dto.news.top_headlines.NewsTopHeadlinesDTO

@Entity(tableName = "table_news_everything")
data class NewsEverythingEntity(
    val newsDatasource: NewsEverythingDTO,
    val lastUpdateTime: Long = TimeUtil.getCurrentTimeInMillis()
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}

@Entity(tableName = "table_news_head_lines")
data class NewsTopHeadlinesEntity(
    val newsDatasource: NewsTopHeadlinesDTO,
    val lastUpdateTime: Long = TimeUtil.getCurrentTimeInMillis()
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
