package io.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.data.entity.news.NewsEverythingArticleEntity
import io.data.entity.news.NewsEverythingEntity
import io.data.entity.news.NewsTopHeadlineArticleEntity
import io.data.entity.news.NewsTopHeadlinesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    /* ======================== Everything ======================== */

    @Query("SELECT * FROM table_news_everything")
    fun readAllEverythingData(): Flow<List<NewsEverythingEntity>>

    @Insert(entity = NewsEverythingEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEverythingData(newsEverythingEntity: NewsEverythingEntity)

    @Insert(entity = NewsEverythingArticleEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEverythingArticle(articleEntity: NewsEverythingArticleEntity)

    /* ======================== Top-Headline ======================== */

    @Query("SELECT * FROM table_news_headlines")
    fun readAllTopHeadlineData(): Flow<List<NewsTopHeadlinesEntity>>

    @Insert(entity = NewsTopHeadlinesEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopHeadlineData(topHeadlinesEntity: NewsTopHeadlinesEntity)

    @Insert(entity = NewsTopHeadlineArticleEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopHeadlineArticle(articleEntity: NewsTopHeadlineArticleEntity)

}