package io.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.data.dto.weather.WeatherForecastDTO
import io.module.common.util.TimeUtil

@Entity(tableName = "table_weather")
class WeatherEntity(
    val weatherDatasource: WeatherForecastDTO,
    val lastUpdateTime: Long = TimeUtil.getCurrentTimeInMillis()
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}