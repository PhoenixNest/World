package io.dev.android.game.test.room.data

import androidx.room.*
import io.dev.android.game.test.room.data.model.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("select * from table_items order by name asc")
    fun getAllItems(): Flow<List<ItemEntity>>

    @Query("select * from table_items where id = :id")
    fun getItemById(id: Int): Flow<ItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemEntity: ItemEntity)

    @Update(entity = ItemEntity::class)
    suspend fun update(itemEntity: ItemEntity)

    @Delete(entity = ItemEntity::class)
    suspend fun delete(itemEntity: ItemEntity)

}