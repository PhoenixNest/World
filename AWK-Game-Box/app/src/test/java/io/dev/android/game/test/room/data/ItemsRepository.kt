package io.dev.android.game.test.room.data

import io.dev.android.game.test.room.data.model.ItemEntity
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {

    fun getAllItemsStream(): Flow<List<ItemEntity>>

    fun getItemStreamById(id: Int): Flow<ItemEntity?>

    suspend fun insertItem(itemEntity: ItemEntity)

    suspend fun deleteItem(itemEntity: ItemEntity)

    suspend fun updateItem(itemEntity: ItemEntity)

}