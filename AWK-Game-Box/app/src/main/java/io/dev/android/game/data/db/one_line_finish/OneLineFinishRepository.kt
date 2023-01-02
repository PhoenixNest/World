package io.dev.android.game.data.db.one_line_finish

import androidx.lifecycle.LiveData
import io.dev.android.game.data.db.one_line_finish.model.OneLineFinishRoadModel

class OneLineFinishRepository(
    private val dao: OneLineFinishDao
) {

    fun getAllData(): LiveData<List<OneLineFinishRoadModel>> {
        return dao.getAllData()
    }

    suspend fun insertData(roadModel: OneLineFinishRoadModel) {
        dao.insertData(roadModel)
    }

    suspend fun updateData(roadModel: OneLineFinishRoadModel) {
        dao.updateData(roadModel)
    }

    suspend fun deleteItem(roadModel: OneLineFinishRoadModel) {
        dao.deleteItem(roadModel)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}