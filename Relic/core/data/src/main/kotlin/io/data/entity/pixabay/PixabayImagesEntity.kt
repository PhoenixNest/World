package io.data.entity.pixabay

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.common.util.TimeUtil
import io.data.dto.pixabay.PixabayImagesDTO

@Entity(tableName = "table_pixabay_images")
class PixabayImagesEntity(
    @ColumnInfo("datasource")
    val datasource: PixabayImagesDTO,
    @ColumnInfo(name = "last_update_time")
    val lastUpdateTime: Long = TimeUtil.getCurrentTimeInMillis()
) {
    @ColumnInfo(name = "uid", index = true)
    @PrimaryKey(autoGenerate = false)
    var uid: Int = 0
}