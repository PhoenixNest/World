package io.dev.android.game.test.room.data

import io.dev.android.game.test.room.data.model.ItemEntity
import kotlinx.coroutines.flow.Flow

class ItemsRepositoryImpl(
    private val itemDao: ItemDao
) : ItemsRepository {

    override fun getAllItemsStream(): Flow<List<ItemEntity>> {
        return itemDao.getAllItems()
    }

    override fun getItemStreamById(id: Int): Flow<ItemEntity?> {
        return itemDao.getItemById(id)
    }

    override suspend fun insertItem(itemEntity: ItemEntity) {
        itemDao.insert(itemEntity)
    }

    override suspend fun updateItem(itemEntity: ItemEntity) {
        itemDao.update(itemEntity)
    }

    override suspend fun deleteItem(itemEntity: ItemEntity) {
        itemDao.delete(itemEntity)
    }

}