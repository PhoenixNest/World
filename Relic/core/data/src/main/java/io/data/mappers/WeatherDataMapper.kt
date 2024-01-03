package io.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import io.common.util.TimeUtil.getCurrentTime
import io.data.dto.weather.WeatherForecastDTO
import io.data.dto.weather.WeatherHourlyDTO
import io.data.entity.weather.WeatherEntity
import io.data.model.weather.WeatherDataModel
import io.data.model.weather.WeatherInfoModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Convert weather data DTO to data model.
 *
 * @see WeatherForecastDTO
 * @see WeatherHourlyDTO
 * @see WeatherDataModel
 * */
object WeatherDataMapper {

    /**
     * Format the original weather data to model by time.
     *
     * @param index     Current index of each time.
     * @param data      Data model.
     * */
    private data class IndexedWeatherData(
        val index: Int,
        val data: WeatherDataModel
    )

    fun WeatherForecastDTO.toWeatherEntity(): WeatherEntity {
        return WeatherEntity(datasource = this)
    }

    fun WeatherForecastDTO.toWeatherInfoModel(): WeatherInfoModel {
        val weatherDataMap: Map<Int, List<WeatherDataModel?>?>? =
            weatherHourlyDTO?.toWeatherDataMap()
        val currentTime: LocalDateTime = getCurrentTime()
        val currentWeatherData: WeatherDataModel? = weatherDataMap?.get(0)?.find {
            val hour: Int = if (currentTime.minute < 30) {
                currentTime.hour
            } else {
                currentTime.hour + 1
            }
            it?.time?.hour == hour
        }

        return WeatherInfoModel(
            weatherDataPerDay = weatherDataMap,
            currentWeatherData = currentWeatherData
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun WeatherHourlyDTO.toWeatherDataMap(): Map<Int, List<WeatherDataModel?>?>? {
        return times?.mapIndexed { index: Int, time: String? ->
            val temperature: Double? = temperatures?.get(index)
            val humidity: Int? = humidity?.get(index)
            val weatherCode: Int? = weatherCode?.get(index)
            val pressure: Double? = pressures?.get(index)
            val windSpeed: Double? = winSpeeds?.get(index)
            val isDayOrNight: Boolean = (isDay?.get(index) == 1)
            IndexedWeatherData(
                index = index,
                data = WeatherDataModel(
                    time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                    temperature = temperature,
                    humidity = humidity,
                    weatherCode = weatherCode,
                    pressure = pressure,
                    windSpeed = windSpeed,
                    isDay = isDayOrNight
                )
            )
        }?.groupBy { groupIndexWeatherData: IndexedWeatherData ->
            // Divide with 24 will spill every weather data to one day (24 hours).
            groupIndexWeatherData.index / 24
        }?.mapValues { mapEntry: Map.Entry<Int, List<IndexedWeatherData>> ->
            // Load up data into the next level.
            mapEntry.value.map { indexedWeatherData: IndexedWeatherData ->
                indexedWeatherData.data
            }
        }
    }

}