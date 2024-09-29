package io.data.convertors

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import io.data.dto.weather.WeatherForecastDTO

class WeatherDataConvertor {

    @TypeConverter
    fun dataToJson(sourceData: WeatherForecastDTO): String {
        return Moshi.Builder()
            .build()
            .adapter(WeatherForecastDTO::class.java)
            .toJson(sourceData)
    }

    @TypeConverter
    fun jsonToData(json: String): WeatherForecastDTO? {
        return Moshi.Builder()
            .build()
            .adapter(WeatherForecastDTO::class.java)
            .fromJson(json)
    }

}