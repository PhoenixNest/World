package io.dev.relic.core.module.data.database.convertors

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.dev.relic.core.module.data.network.api.model.weather.WeatherDataDTO

class WeatherDataConvertor {

    @TypeConverter
    fun dataToJson(weatherDataDTO: WeatherDataDTO): String? {
        val jsonAdapter: JsonAdapter<WeatherDataDTO> = Moshi.Builder()
            .build()
            .adapter(WeatherDataDTO::class.java)
        return jsonAdapter.toJson(weatherDataDTO)
    }

    @TypeConverter
    fun jsonToData(json: String): WeatherDataDTO? {
        val jsonAdapter: JsonAdapter<WeatherDataDTO> = Moshi.Builder()
            .build()
            .adapter(WeatherDataDTO::class.java)
        return jsonAdapter.fromJson(json)
    }

}