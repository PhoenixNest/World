package io.dev.relic.core.data.database.convertors

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.dev.relic.core.data.network.api.dto.weather.WeatherApiDTO

class WeatherDataConvertor {

    @TypeConverter
    fun dataToJson(sourceData: WeatherApiDTO): String {
        val jsonAdapter: JsonAdapter<WeatherApiDTO> = Moshi.Builder()
            .build()
            .adapter(WeatherApiDTO::class.java)
        return jsonAdapter.toJson(sourceData)
    }

    @TypeConverter
    fun jsonToData(json: String): WeatherApiDTO? {
        val jsonAdapter: JsonAdapter<WeatherApiDTO> = Moshi.Builder()
            .build()
            .adapter(WeatherApiDTO::class.java)
        return jsonAdapter.fromJson(json)
    }

}