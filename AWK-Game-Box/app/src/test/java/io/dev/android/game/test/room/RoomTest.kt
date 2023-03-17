package io.dev.android.game.test.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.dev.android.game.test.room.data.InventoryDatabase
import io.dev.android.game.test.room.data.ItemDao
import io.dev.android.game.test.room.data.model.ItemEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var itemDao: ItemDao
    private lateinit var inventoryDatabase: InventoryDatabase

    private var item1 = ItemEntity(1, "Apples", 10.0, 20)
    private var item2 = ItemEntity(2, "Bananas", 15.0, 97)

    @Before
    fun createDatabase() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        inventoryDatabase = Room.inMemoryDatabaseBuilder(
            context,
            InventoryDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        itemDao = inventoryDatabase.itemDao()
    }

    private suspend fun addOneItemRoDatabase() {
        itemDao.insert(item1)
    }

    private suspend fun addTwoItemsToDatabase() {
        itemDao.insert(item1)
        itemDao.insert(item2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsItemIntoDB() = runBlocking {
        addOneItemRoDatabase()
        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], item1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDB() = runBlocking {
        addTwoItemsToDatabase()
        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], item1)
        assertEquals(allItems[1], item2)
    }


    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        inventoryDatabase.close()
    }

}