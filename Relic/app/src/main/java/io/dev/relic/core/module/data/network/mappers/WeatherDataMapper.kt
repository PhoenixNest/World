package io.dev.relic.core.module.data.network.mappers

import io.dev.relic.core.module.data.network.api.model.weather.WeatherDTO
import io.dev.relic.core.module.data.network.api.model.weather.WeatherDataDTO
import io.dev.relic.domain.model.weather.WeatherDataModel
import io.dev.relic.domain.model.weather.WeatherInfoModel
import io.dev.relic.global.utils.TimeUtil.getCurrentTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Convert DTO to model.
 *
 * @see io.dev.relic.core.module.data.network.api.model.weather.WeatherDTO
 * @see io.dev.relic.core.module.data.network.api.model.weather.WeatherUnitDataDTO
 * @see io.dev.relic.core.module.data.network.api.model.weather.WeatherDataDTO
 * @see io.dev.relic.domain.model.weather.WeatherDataModel
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

    fun WeatherDataDTO.toWeatherDataMap(): Map<Int, List<WeatherDataModel>> {
        return times.mapIndexed { index: Int, time: String ->
            val temperature: Double = temperatures[index]
            val weatherCode: Int = weatherCode[index]
            val humidity: Int = humidity[index]
            val windSpeed: Double = winSpeeds[index]
            val pressure: Double = pressures[index]
            IndexedWeatherData(
                index = index,
                data = WeatherDataModel(
                    time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                    temperature = temperature,
                    weatherCode = weatherCode,
                    humidity = humidity,
                    windSpeed = windSpeed,
                    pressure = pressure
                )
            )
        }.groupBy { groupIndexWeatherData: IndexedWeatherData ->
            // Every weather data will be spilt to one day (24 hours) by divide with 24.
            groupIndexWeatherData.index / 24
        }.mapValues { mapEntry: Map.Entry<Int, List<IndexedWeatherData>> ->
            // Load up data into next level.
            mapEntry.value.map { indexedWeatherData: IndexedWeatherData ->
                indexedWeatherData.data
            }
        }
    }

    fun WeatherDTO.toWeatherInfoModel(): WeatherInfoModel {
        val weatherDataMap: Map<Int, List<WeatherDataModel>> = weatherData.toWeatherDataMap()
        val currentTime: LocalDateTime = getCurrentTime()
        val currentWeatherData = weatherDataMap[0]?.find {
            val hour = if (currentTime.minute < 30) {
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

}