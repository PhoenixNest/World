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
    fun readAllNewsEverythingData(): Flow<List<NewsEverythingEntity>>

    @Insert(entity = NewsEverythingEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsEverythingData(newsEverythingEntity: NewsEverythingEntity)

    @Insert(entity = NewsEverythingArticleEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsEverythingArticle(articleEntity: NewsEverythingArticleEntity)

    /* ======================== Top-Headline ======================== */

    @Query("SELECT * FROM table_news_headlines")
    fun readAllNewsTopHeadlineData(): Flow<List<NewsTopHeadlinesEntity>>

    @Insert(entity = NewsTopHeadlinesEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsTopHeadlineData(topHeadlinesEntity: NewsTopHeadlinesEntity)

    @Insert(entity = NewsTopHeadlineArticleEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsTopHeadlineArticle(articleEntity: NewsTopHeadlineArticleEntity)

}