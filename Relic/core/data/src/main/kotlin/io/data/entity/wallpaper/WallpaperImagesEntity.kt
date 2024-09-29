package io.data.entity.wallpaper

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.common.util.TimeUtil
import io.data.dto.wallpaper.WallpaperImagesDTO

@Entity(tableName = "table_wallpaper_images")
class WallpaperImagesEntity(
    @ColumnInfo("datasource")
    val datasource: WallpaperImagesDTO,
    @ColumnInfo(name = "last_update_time")
    val lastUpdateTime: Long = TimeUtil.getCurrentTimeInMillis()
) {
    @ColumnInfo(name = "uid", index = true)
    @PrimaryKey(autoGenerate = false)
    var uid: Int = 0
}