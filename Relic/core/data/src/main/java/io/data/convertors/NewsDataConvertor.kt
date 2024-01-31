package io.data.convertors

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import io.data.dto.news.everything.TrendingNewsDTO
import io.data.dto.news.top_headlines.TopHeadlinesNewsDTO

class NewsDataConvertor {

    /* ======================== Everything ======================== */

    @TypeConverter
    fun dataToNewsEverythingJson(sourceData: TrendingNewsDTO): String {
        return Moshi.Builder()
            .build()
            .adapter(TrendingNewsDTO::class.java)
            .toJson(sourceData)
    }

    @TypeConverter
    fun jsonToNewsEverythingData(json: String): TrendingNewsDTO? {
        return Moshi.Builder()
            .build()
            .adapter(TrendingNewsDTO::class.java)
            .fromJson(json)
    }

    /* ======================== Top-Headline ======================== */

    @TypeConverter
    fun dataToNewsTopHeadlineJson(sourceData: TopHeadlinesNewsDTO): String {
        return Moshi.Builder()
            .build()
            .adapter(TopHeadlinesNewsDTO::class.java)
            .toJson(sourceData)
    }

    @TypeConverter
    fun jsonToNewsTopHeadlineData(json: String): TopHeadlinesNewsDTO? {
        return Moshi.Builder()
            .build()
            .adapter(TopHeadlinesNewsDTO::class.java)
            .fromJson(json)
    }

}