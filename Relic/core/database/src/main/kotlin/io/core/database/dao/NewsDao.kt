package io.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.data.entity.news.TopHeadlineNewsArticleEntity
import io.data.entity.news.TopHeadlinesNewsEntity
import io.data.entity.news.TrendingNewsArticleEntity
import io.data.entity.news.TrendingNewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    /* ======================== Trending ======================== */

    @Query("SELECT * FROM table_trending_news")
    fun queryAllTrendingNewsData(): Flow<List<TrendingNewsEntity>>

    @Query("SELECT * FROM table_trending_news_articles")
    fun queryAllTrendingNewsArticlesData(): Flow<List<TrendingNewsArticleEntity>>

    @Insert(entity = TrendingNewsEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingNewsData(trendingNewsEntity: TrendingNewsEntity)

    @Insert(entity = TrendingNewsArticleEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingNewsArticle(articleEntity: TrendingNewsArticleEntity)

    /* ======================== Top-Headline ======================== */

    @Query("SELECT * FROM table_top_headline_news")
    fun queryAllTopHeadlineNewsData(): Flow<List<TopHeadlinesNewsEntity>>

    @Query("SELECT * FROM table_top_headline_news_articles")
    fun queryAllTopHeadlineNewsArticlesData(): Flow<List<TopHeadlineNewsArticleEntity>>

    @Insert(entity = TopHeadlinesNewsEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopHeadlineNewsData(topHeadlinesEntity: TopHeadlinesNewsEntity)

    @Insert(entity = TopHeadlineNewsArticleEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopHeadlineNewsArticle(articleEntity: TopHeadlineNewsArticleEntity)

}