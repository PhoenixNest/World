package io.dev.android.game.data.db.one_line_finish

import androidx.lifecycle.LiveData
import androidx.room.*
import io.dev.android.game.data.db.one_line_finish.model.OneLineFinishRoadModel

@Dao
interface OneLineFinishDao {
    @Query("select * from table_one_line_finish order by id asc")
    fun getAllData(): LiveData<List<OneLineFinishRoadModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(roadModel: OneLineFinishRoadModel)

    @Update
    suspend fun updateData(roadModel: OneLineFinishRoadModel)

    @Delete
    suspend fun deleteItem(roadModel: OneLineFinishRoadModel)

    @Query("delete from table_one_line_finish")
    suspend fun deleteAll()
}