package io.dev.relic.core.module.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.dev.relic.core.module.data.network.api.model.weather.WeatherDataDTO

@Entity(tableName = "table_weather")
data class WeatherEntity(

    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,

    val weatherDataDTO: WeatherDataDTO

)