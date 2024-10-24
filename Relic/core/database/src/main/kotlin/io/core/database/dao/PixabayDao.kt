package io.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.data.entity.pixabay.PixabayImagesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PixabayDao {

    /* ======================== Images ======================== */

    @Query("SELECT * FROM table_pixabay_images")
    fun queryAllImagesData(): Flow<List<PixabayImagesEntity>>

    @Insert(entity = PixabayImagesEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImagesData(pixabayImagesEntity: PixabayImagesEntity)

}