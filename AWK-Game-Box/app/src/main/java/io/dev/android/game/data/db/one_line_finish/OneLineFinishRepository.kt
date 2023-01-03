package io.dev.android.game.data.db.one_line_finish

import androidx.lifecycle.LiveData
import io.dev.android.game.data.db.one_line_finish.entity.OneLineFinishEntity

class OneLineFinishRepository(
    private val dao: OneLineFinishDao
) {

    fun getAllData(): LiveData<List<OneLineFinishEntity>> {
        return dao.getAllData()
    }

    suspend fun insertData(entity: OneLineFinishEntity) {
        dao.insertData(entity)
    }

    suspend fun updateData(entity: OneLineFinishEntity) {
        dao.updateData(entity)
    }

    suspend fun deleteItem(entity: OneLineFinishEntity) {
        dao.deleteItem(entity)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}