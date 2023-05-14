package io.dev.relic.core.module.data.network.api

import io.dev.relic.core.module.data.network.api.model.weather.WeatherDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherApi {

    @GET("v1/forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl")
    suspend fun getWeatherData(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): WeatherDTO

}