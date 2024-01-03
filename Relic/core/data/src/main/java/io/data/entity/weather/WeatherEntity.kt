package io.data.entity.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.common.util.TimeUtil
import io.data.dto.weather.WeatherForecastDTO

@Entity(tableName = "table_weather")
data class WeatherEntity(
    @ColumnInfo(name = "datasource")
    val datasource: WeatherForecastDTO,
    @ColumnInfo(name = "last_update_time")
    val lastUpdateTime: Long = TimeUtil.getCurrentTimeInMillis()
) {
    @ColumnInfo(name = "uid", index = true)
    @PrimaryKey(autoGenerate = false)
    var uid: Int = 0
}