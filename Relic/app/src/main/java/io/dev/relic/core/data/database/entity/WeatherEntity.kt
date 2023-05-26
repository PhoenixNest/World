package io.dev.relic.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.dev.relic.core.data.network.api.dto.weather.WeatherApiDTO

@Entity(tableName = "table_weather")
class WeatherEntity(val weatherDatasource: WeatherApiDTO) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}