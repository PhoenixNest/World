package io.dev.relic.core.data.network.mappers

import io.dev.relic.core.data.database.entity.WeatherEntity
import io.dev.relic.core.data.network.api.dto.weather.WeatherForecastDTO
import io.dev.relic.core.data.network.api.dto.weather.WeatherHourlyDTO
import io.dev.relic.domain.model.weather.WeatherDataModel
import io.dev.relic.domain.model.weather.WeatherInfoModel
import io.dev.relic.global.utils.TimeUtil.getCurrentTime
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
        return WeatherEntity(weatherDatasource = this)
    }

    fun WeatherForecastDTO.toWeatherInfoModel(): WeatherInfoModel {
        val weatherDataMap: Map<Int, List<WeatherDataModel>> = weatherHourlyDTO.toWeatherDataMap()
        val currentTime: LocalDateTime = getCurrentTime()
        val currentWeatherData: WeatherDataModel? = weatherDataMap[0]?.find {
            val hour: Int = if (currentTime.minute < 30) {
                currentTime.hour
            } else {
                currentTime.hour + 1
            }
            it.time.hour == hour
        }

        return WeatherInfoModel(
            weatherDataPerDay = weatherDataMap,
            currentWeatherData = currentWeatherData
        )
    }

    private fun WeatherHourlyDTO.toWeatherDataMap(): Map<Int, List<WeatherDataModel>> {
        return times.mapIndexed { index: Int, time: String ->
            val temperature: Double = temperatures[index]
            val humidity: Int = humidity[index]
            val weatherCode: Int = weatherCode[index]
            val pressure: Double = pressures[index]
            val windSpeed: Double = winSpeeds[index]
            val isDayOrNight: Boolean = (isDay[index] == 1)
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
        }.groupBy { groupIndexWeatherData: IndexedWeatherData ->
            // Divide with 24 will spill every weather data to one day (24 hours).
            groupIndexWeatherData.index / 24
        }.mapValues { mapEntry: Map.Entry<Int, List<IndexedWeatherData>> ->
            // Load up data into the next level.
            mapEntry.value.map { indexedWeatherData: IndexedWeatherData ->
                indexedWeatherData.data
            }
        }
    }

}