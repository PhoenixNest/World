package io.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.data.entity.wallpaper.WallpaperImagesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WallpaperDao {

    /* ======================== Images ======================== */

    @Query("SELECT * FROM table_wallpaper_images")
    fun queryAllImagesData(): Flow<List<WallpaperImagesEntity>>

    @Insert(entity = WallpaperImagesEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImagesData(wallpaperImagesEntity: WallpaperImagesEntity)

}