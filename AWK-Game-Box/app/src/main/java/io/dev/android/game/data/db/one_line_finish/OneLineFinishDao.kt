package io.dev.android.game.data.db.one_line_finish

import androidx.lifecycle.LiveData
import androidx.room.*
import io.dev.android.game.data.db.one_line_finish.entity.OneLineFinishEntity

@Dao
interface OneLineFinishDao {
    @Query("select * from table_one_line_finish order by id asc")
    fun getAllData(): LiveData<List<OneLineFinishEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(entity: OneLineFinishEntity)

    @Update
    suspend fun updateData(entity: OneLineFinishEntity)

    @Delete
    suspend fun deleteItem(entity: OneLineFinishEntity)

    @Query("delete from table_one_line_finish")
    suspend fun deleteAll()
}