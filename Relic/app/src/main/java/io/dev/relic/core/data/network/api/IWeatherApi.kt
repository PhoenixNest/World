package io.dev.relic.core.data.network.api

import io.dev.relic.core.data.network.api.dto.weather.WeatherApiDTO
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * [Open-Meteo Api](https://open-meteo.com/)
 * */
interface IWeatherApi {

    @GET("v1/forecast?hourly=temperature_2m,relativehumidity_2m,weathercode,surface_pressure,windspeed_10m,is_day")
    suspend fun getWeatherData(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): WeatherApiDTO

}