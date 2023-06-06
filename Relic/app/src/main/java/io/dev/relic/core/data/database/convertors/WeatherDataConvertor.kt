package io.dev.relic.core.data.database.convertors

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.dev.relic.core.data.network.api.dto.weather.WeatherForecastDTO

class WeatherDataConvertor {

    @TypeConverter
    fun dataToJson(sourceData: WeatherForecastDTO): String {
        val jsonAdapter: JsonAdapter<WeatherForecastDTO> = Moshi.Builder()
            .build()
            .adapter(WeatherForecastDTO::class.java)
        return jsonAdapter.toJson(sourceData)
    }

    @TypeConverter
    fun jsonToData(json: String): WeatherForecastDTO? {
        val jsonAdapter: JsonAdapter<WeatherForecastDTO> = Moshi.Builder()
            .build()
            .adapter(WeatherForecastDTO::class.java)
        return jsonAdapter.fromJson(json)
    }

}