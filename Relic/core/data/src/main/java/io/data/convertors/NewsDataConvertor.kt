package io.data.convertors

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import io.data.dto.news.everything.NewsEverythingDTO
import io.data.dto.news.top_headlines.NewsTopHeadlinesDTO

class NewsDataConvertor {

    /* ======================== Everything ======================== */

    @TypeConverter
    fun dataToNewsEverythingJson(sourceData: NewsEverythingDTO): String {
        return Moshi.Builder()
            .build()
            .adapter(NewsEverythingDTO::class.java)
            .toJson(sourceData)
    }

    @TypeConverter
    fun jsonToNewsEverythingData(json: String): NewsEverythingDTO? {
        return Moshi.Builder()
            .build()
            .adapter(NewsEverythingDTO::class.java)
            .fromJson(json)
    }

    /* ======================== Top-Headline ======================== */

    @TypeConverter
    fun dataToNewsTopHeadlineJson(sourceData: NewsTopHeadlinesDTO): String {
        return Moshi.Builder()
            .build()
            .adapter(NewsTopHeadlinesDTO::class.java)
            .toJson(sourceData)
    }

    @TypeConverter
    fun jsonToNewsTopHeadlineData(json: String): NewsTopHeadlinesDTO? {
        return Moshi.Builder()
            .build()
            .adapter(NewsTopHeadlinesDTO::class.java)
            .fromJson(json)
    }

}