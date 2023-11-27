package io.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.data.dto.news.top_headlines.NewsTopHeadlinesDTO
import io.data.entity.NewsEverythingEntity
import io.data.entity.NewsTopHeadlinesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM table_news_everything")
    fun readAllNewsEverythingData(): Flow<List<NewsEverythingEntity>>

    @Insert(entity = NewsEverythingEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsEverythingData(newsEverythingEntity: NewsEverythingEntity)

    @Query("SELECT * FROM table_news_head_lines")
    fun readAllNewsTopHeadlineData(): Flow<List<NewsTopHeadlinesEntity>>

    @Insert(entity = NewsTopHeadlinesDTO::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsTopHeadlineData(topHeadlinesEntity: NewsTopHeadlinesEntity)

}