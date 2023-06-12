package io.dev.relic.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.dev.relic.core.data.network.api.dto.weather.WeatherForecastDTO
import io.dev.relic.global.utils.TimeUtil

@Entity(tableName = "table_weather")
class WeatherEntity(
    val weatherDatasource: WeatherForecastDTO,
    val lastUpdateTime: Long = TimeUtil.getCurrentTimeInMillis()
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}