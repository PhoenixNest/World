package io.core.network.api

import io.data.dto.weather.WeatherForecastDTO
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * [Open-Meteo Api](https://open-meteo.com/)
 * */
interface IWeatherApi {

    @GET("forecast")
    suspend fun getWeatherData(
        @Query("hourly") hourly: String = "temperature_2m,relativehumidity_2m,weathercode,surface_pressure,windspeed_10m,is_day",
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): WeatherForecastDTO

}